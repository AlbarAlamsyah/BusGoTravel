package controller;

import dao.ArmadaDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Armada;

@WebServlet(name = "ArmadaServlet", urlPatterns = {"/armada"})
public class ArmadaServlet extends HttpServlet {

    private final ArmadaDAO armadaDAO = new ArmadaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Armada armada = armadaDAO.getArmadaById(id);

            request.setAttribute("armadaEdit", armada);
        }

        if ("hapus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));

            boolean berhasil = armadaDAO.hapusArmada(id);

            if (berhasil) {
                redirectWithMessage(response, "Data armada berhasil dihapus.", "success");
            } else {
                redirectWithMessage(response, "Data armada gagal dihapus. Kemungkinan data sudah dipakai transaksi.", "error");
            }
            return;
        }

        String keyword = request.getParameter("keyword");
        String sort = request.getParameter("sort");

        request.setAttribute("listArmada", armadaDAO.getAllArmada(keyword, sort));
        request.setAttribute("keyword", keyword == null ? "" : keyword);
        request.setAttribute("sort", sort == null ? "" : sort);

        request.getRequestDispatcher("armada.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String idArmadaStr = request.getParameter("idArmada");
        String kodeArmada = request.getParameter("kodeArmada");
        String namaBus = request.getParameter("namaBus");
        String platNomor = request.getParameter("platNomor");
        String kapasitasStr = request.getParameter("kapasitas");
        String fasilitas = request.getParameter("fasilitas");
        String status = request.getParameter("status");

        if (isEmpty(kodeArmada) || isEmpty(namaBus) || isEmpty(platNomor)
                || isEmpty(kapasitasStr) || isEmpty(fasilitas) || isEmpty(status)) {

            redirectWithMessage(response, "Semua field wajib diisi.", "error");
            return;
        }

        int kapasitas;

        try {
            kapasitas = Integer.parseInt(kapasitasStr);

            if (kapasitas <= 0) {
                redirectWithMessage(response, "Kapasitas harus lebih dari 0.", "error");
                return;
            }

            if (kapasitas < 10) {
                redirectWithMessage(response, "Kapasitas bus minimal 10 kursi.", "error");
                return;
            }

        } catch (NumberFormatException e) {
            redirectWithMessage(response, "Kapasitas harus berupa angka valid.", "error");
            return;
        }

        Armada armada = new Armada();
        armada.setKodeArmada(kodeArmada.trim().toUpperCase());
        armada.setNamaBus(namaBus.trim());
        armada.setPlatNomor(platNomor.trim().toUpperCase());
        armada.setKapasitas(kapasitas);
        armada.setFasilitas(fasilitas.trim());
        armada.setStatus(status);

        boolean berhasil;

        if ("update".equals(action)) {
            int idArmada = Integer.parseInt(idArmadaStr);

            if (armadaDAO.cekKodeArmadaSaatUpdate(armada.getKodeArmada(), idArmada)) {
                redirectWithMessage(response, "Kode armada sudah digunakan data lain.", "error");
                return;
            }

            if (armadaDAO.cekPlatNomorSaatUpdate(armada.getPlatNomor(), idArmada)) {
                redirectWithMessage(response, "Plat nomor sudah digunakan armada lain.", "error");
                return;
            }

            armada.setIdArmada(idArmada);
            berhasil = armadaDAO.updateArmada(armada);

            if (berhasil) {
                redirectWithMessage(response, "Data armada berhasil diperbarui.", "success");
            } else {
                redirectWithMessage(response, "Data armada gagal diperbarui.", "error");
            }

        } else {
            if (armadaDAO.cekKodeArmada(armada.getKodeArmada())) {
                redirectWithMessage(response, "Kode armada sudah terdaftar.", "error");
                return;
            }

            if (armadaDAO.cekPlatNomor(armada.getPlatNomor())) {
                redirectWithMessage(response, "Plat nomor sudah terdaftar.", "error");
                return;
            }

            berhasil = armadaDAO.tambahArmada(armada);

            if (berhasil) {
                redirectWithMessage(response, "Data armada berhasil ditambahkan.", "success");
            } else {
                redirectWithMessage(response, "Data armada gagal ditambahkan.", "error");
            }
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void redirectWithMessage(HttpServletResponse response, String message, String type)
            throws IOException {
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect("armada?" + type + "=" + encodedMessage);
    }
}