package controller;

import dao.PenumpangDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Penumpang;

@WebServlet(name = "PenumpangServlet", urlPatterns = {"/penumpang"})
public class PenumpangServlet extends HttpServlet {

    private final PenumpangDAO penumpangDAO = new PenumpangDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Penumpang penumpang = penumpangDAO.getPenumpangById(id);
            request.setAttribute("penumpangEdit", penumpang);
        }

        if ("hapus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));

            boolean berhasil = penumpangDAO.hapusPenumpang(id);

            if (berhasil) {
                redirectWithMessage(response, "Data penumpang berhasil dihapus.", "success");
            } else {
                redirectWithMessage(response, "Data penumpang gagal dihapus. Kemungkinan data sudah dipakai transaksi.", "error");
            }
            return;
        }

        String keyword = request.getParameter("keyword");
        String sort = request.getParameter("sort");

        request.setAttribute("listPenumpang", penumpangDAO.getAllPenumpang(keyword, sort));
        request.setAttribute("keyword", keyword == null ? "" : keyword);
        request.setAttribute("sort", sort == null ? "" : sort);

        request.getRequestDispatcher("penumpang.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String idPenumpangStr = request.getParameter("idPenumpang");
        String nik = request.getParameter("nik");
        String namaPenumpang = request.getParameter("namaPenumpang");
        String noHp = request.getParameter("noHp");
        String alamat = request.getParameter("alamat");
        String jenisKelamin = request.getParameter("jenisKelamin");

        if (isEmpty(nik) || isEmpty(namaPenumpang) || isEmpty(noHp)
                || isEmpty(alamat) || isEmpty(jenisKelamin)) {

            redirectWithMessage(response, "Semua field wajib diisi.", "error");
            return;
        }

        if (!nik.matches("\\d+")) {
            redirectWithMessage(response, "NIK hanya boleh berisi angka.", "error");
            return;
        }

        if (nik.length() < 8 || nik.length() > 20) {
            redirectWithMessage(response, "Panjang NIK harus antara 8 sampai 20 digit.", "error");
            return;
        }

        if (!noHp.matches("\\d+")) {
            redirectWithMessage(response, "Nomor HP hanya boleh berisi angka.", "error");
            return;
        }

        if (noHp.length() < 10 || noHp.length() > 15) {
            redirectWithMessage(response, "Nomor HP harus antara 10 sampai 15 digit.", "error");
            return;
        }

        Penumpang penumpang = new Penumpang();
        penumpang.setNik(nik.trim());
        penumpang.setNamaPenumpang(namaPenumpang.trim());
        penumpang.setNoHp(noHp.trim());
        penumpang.setAlamat(alamat.trim());
        penumpang.setJenisKelamin(jenisKelamin);

        boolean berhasil;

        if ("update".equals(action)) {
            int idPenumpang = Integer.parseInt(idPenumpangStr);

            if (penumpangDAO.cekNikSaatUpdate(penumpang.getNik(), idPenumpang)) {
                redirectWithMessage(response, "NIK sudah digunakan penumpang lain.", "error");
                return;
            }

            penumpang.setIdPenumpang(idPenumpang);
            berhasil = penumpangDAO.updatePenumpang(penumpang);

            if (berhasil) {
                redirectWithMessage(response, "Data penumpang berhasil diperbarui.", "success");
            } else {
                redirectWithMessage(response, "Data penumpang gagal diperbarui.", "error");
            }

        } else {
            if (penumpangDAO.cekNik(penumpang.getNik())) {
                redirectWithMessage(response, "NIK sudah terdaftar.", "error");
                return;
            }

            berhasil = penumpangDAO.tambahPenumpang(penumpang);

            if (berhasil) {
                redirectWithMessage(response, "Data penumpang berhasil ditambahkan.", "success");
            } else {
                redirectWithMessage(response, "Data penumpang gagal ditambahkan.", "error");
            }
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void redirectWithMessage(HttpServletResponse response, String message, String type)
            throws IOException {
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect("penumpang?" + type + "=" + encodedMessage);
    }
}