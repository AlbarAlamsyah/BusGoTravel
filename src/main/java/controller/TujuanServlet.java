package controller;

import dao.TujuanDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Tujuan;

@WebServlet(name = "TujuanServlet", urlPatterns = {"/tujuan"})
public class TujuanServlet extends HttpServlet {

    private final TujuanDAO tujuanDAO = new TujuanDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Tujuan tujuan = tujuanDAO.getTujuanById(id);

            request.setAttribute("tujuanEdit", tujuan);
        }

        if ("hapus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));

            boolean berhasil = tujuanDAO.hapusTujuan(id);

            if (berhasil) {
                redirectWithMessage(response, "Data tujuan berhasil dihapus.", "success");
            } else {
                redirectWithMessage(response, "Data tujuan gagal dihapus. Kemungkinan data sudah dipakai transaksi.", "error");
            }
            return;
        }

        String keyword = request.getParameter("keyword");
        String sort = request.getParameter("sort");

        request.setAttribute("listTujuan", tujuanDAO.getAllTujuan(keyword, sort));
        request.setAttribute("keyword", keyword == null ? "" : keyword);
        request.setAttribute("sort", sort == null ? "" : sort);

        request.getRequestDispatcher("tujuan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String idTujuanStr = request.getParameter("idTujuan");
        String kodeTujuan = request.getParameter("kodeTujuan");
        String kotaAsal = request.getParameter("kotaAsal");
        String kotaTujuan = request.getParameter("kotaTujuan");
        String hargaEkonomiStr = request.getParameter("hargaEkonomi");
        String hargaBisnisStr = request.getParameter("hargaBisnis");
        String hargaEksekutifStr = request.getParameter("hargaEksekutif");
        String status = request.getParameter("status");

        if (isEmpty(kodeTujuan) || isEmpty(kotaAsal) || isEmpty(kotaTujuan)
                || isEmpty(hargaEkonomiStr) || isEmpty(hargaBisnisStr)
                || isEmpty(hargaEksekutifStr) || isEmpty(status)) {

            redirectWithMessage(response, "Semua field wajib diisi.", "error");
            return;
        }

        double hargaEkonomi;
        double hargaBisnis;
        double hargaEksekutif;

        try {
            hargaEkonomi = Double.parseDouble(hargaEkonomiStr);
            hargaBisnis = Double.parseDouble(hargaBisnisStr);
            hargaEksekutif = Double.parseDouble(hargaEksekutifStr);

            if (hargaEkonomi <= 0 || hargaBisnis <= 0 || hargaEksekutif <= 0) {
                redirectWithMessage(response, "Harga tiket harus lebih dari 0.", "error");
                return;
            }

            if (hargaBisnis < hargaEkonomi || hargaEksekutif < hargaBisnis) {
                redirectWithMessage(response, "Harga harus berurutan: Ekonomi <= Bisnis <= Eksekutif.", "error");
                return;
            }

        } catch (NumberFormatException e) {
            redirectWithMessage(response, "Harga tiket harus berupa angka valid.", "error");
            return;
        }

        Tujuan tujuan = new Tujuan();
        tujuan.setKodeTujuan(kodeTujuan.trim().toUpperCase());
        tujuan.setKotaAsal(kotaAsal.trim());
        tujuan.setKotaTujuan(kotaTujuan.trim());
        tujuan.setHargaEkonomi(hargaEkonomi);
        tujuan.setHargaBisnis(hargaBisnis);
        tujuan.setHargaEksekutif(hargaEksekutif);
        tujuan.setStatus(status);

        boolean berhasil;

        if ("update".equals(action)) {
            int idTujuan = Integer.parseInt(idTujuanStr);

            if (tujuanDAO.cekKodeTujuanSaatUpdate(tujuan.getKodeTujuan(), idTujuan)) {
                redirectWithMessage(response, "Kode tujuan sudah digunakan data lain.", "error");
                return;
            }

            tujuan.setIdTujuan(idTujuan);
            berhasil = tujuanDAO.updateTujuan(tujuan);

            if (berhasil) {
                redirectWithMessage(response, "Data tujuan berhasil diperbarui.", "success");
            } else {
                redirectWithMessage(response, "Data tujuan gagal diperbarui.", "error");
            }

        } else {
            if (tujuanDAO.cekKodeTujuan(tujuan.getKodeTujuan())) {
                redirectWithMessage(response, "Kode tujuan sudah terdaftar.", "error");
                return;
            }

            berhasil = tujuanDAO.tambahTujuan(tujuan);

            if (berhasil) {
                redirectWithMessage(response, "Data tujuan berhasil ditambahkan.", "success");
            } else {
                redirectWithMessage(response, "Data tujuan gagal ditambahkan.", "error");
            }
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void redirectWithMessage(HttpServletResponse response, String message, String type)
            throws IOException {
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect("tujuan?" + type + "=" + encodedMessage);
    }
}