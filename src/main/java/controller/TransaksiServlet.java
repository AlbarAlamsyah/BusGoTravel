package controller;

import dao.TransaksiDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Transaksi;
import model.Tujuan;

@WebServlet(name = "TransaksiServlet", urlPatterns = {"/transaksi"})
public class TransaksiServlet extends HttpServlet {

    private final TransaksiDAO transaksiDAO = new TransaksiDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("hapus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));

            boolean berhasil = transaksiDAO.hapusTransaksi(id);

            if (berhasil) {
                redirectWithMessage(response, "Data transaksi berhasil dihapus.", "success");
            } else {
                redirectWithMessage(response, "Data transaksi gagal dihapus.", "error");
            }
            return;
        }

        String keyword = request.getParameter("keyword");
        String sort = request.getParameter("sort");

        request.setAttribute("listTransaksi", transaksiDAO.getAllTransaksi(keyword, sort));
        request.setAttribute("listTujuan", transaksiDAO.getTujuanAktif());
        request.setAttribute("listArmada", transaksiDAO.getArmadaAktif());
        request.setAttribute("listPenumpang", transaksiDAO.getAllPenumpang());
        request.setAttribute("kodeTransaksi", transaksiDAO.generateKodeTransaksi());
        request.setAttribute("tanggalHariIni", LocalDate.now().toString());
        request.setAttribute("keyword", keyword == null ? "" : keyword);
        request.setAttribute("sort", sort == null ? "" : sort);

        request.getRequestDispatcher("transaksi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String kodeTransaksi = request.getParameter("kodeTransaksi");
        String tanggalTransaksi = request.getParameter("tanggalTransaksi");
        String idTujuanStr = request.getParameter("idTujuan");
        String idArmadaStr = request.getParameter("idArmada");
        String idPenumpangStr = request.getParameter("idPenumpang");
        String kelasTiket = request.getParameter("kelasTiket");
        String jumlahTiketStr = request.getParameter("jumlahTiket");

        if (isEmpty(kodeTransaksi) || isEmpty(tanggalTransaksi)
                || isEmpty(idTujuanStr) || isEmpty(idArmadaStr)
                || isEmpty(idPenumpangStr) || isEmpty(kelasTiket)
                || isEmpty(jumlahTiketStr)) {

            redirectWithMessage(response, "Semua field transaksi wajib diisi.", "error");
            return;
        }

        int idTujuan;
        int idArmada;
        int idPenumpang;
        int jumlahTiket;

        try {
            idTujuan = Integer.parseInt(idTujuanStr);
            idArmada = Integer.parseInt(idArmadaStr);
            idPenumpang = Integer.parseInt(idPenumpangStr);
            jumlahTiket = Integer.parseInt(jumlahTiketStr);

            if (idTujuan <= 0 || idArmada <= 0 || idPenumpang <= 0) {
                redirectWithMessage(response, "Data tujuan, armada, dan penumpang harus dipilih.", "error");
                return;
            }

            if (jumlahTiket <= 0) {
                redirectWithMessage(response, "Jumlah tiket harus lebih dari 0.", "error");
                return;
            }

        } catch (NumberFormatException e) {
            redirectWithMessage(response, "Input angka tidak valid.", "error");
            return;
        }

        Tujuan tujuan = transaksiDAO.getTujuanById(idTujuan);

        if (tujuan == null) {
            redirectWithMessage(response, "Data tujuan tidak tersedia atau tidak aktif.", "error");
            return;
        }

        double hargaTiket;

        if ("Ekonomi".equals(kelasTiket)) {
            hargaTiket = tujuan.getHargaEkonomi();
        } else if ("Bisnis".equals(kelasTiket)) {
            hargaTiket = tujuan.getHargaBisnis();
        } else if ("Eksekutif".equals(kelasTiket)) {
            hargaTiket = tujuan.getHargaEksekutif();
        } else {
            redirectWithMessage(response, "Kelas tiket tidak valid.", "error");
            return;
        }

        double subtotal = hargaTiket * jumlahTiket;
        double diskon = 0;

        if (jumlahTiket > 3) {
            diskon = subtotal * 0.10;
        }

        double totalBayar = subtotal - diskon;

        Integer idUser = (Integer) request.getSession().getAttribute("idUser");

        if (idUser == null) {
            response.sendRedirect("login");
            return;
        }

        Transaksi transaksi = new Transaksi();

        transaksi.setKodeTransaksi(kodeTransaksi.trim().toUpperCase());
        transaksi.setTanggalTransaksi(tanggalTransaksi);
        transaksi.setIdTujuan(idTujuan);
        transaksi.setIdArmada(idArmada);
        transaksi.setIdPenumpang(idPenumpang);
        transaksi.setKelasTiket(kelasTiket);
        transaksi.setHargaTiket(hargaTiket);
        transaksi.setJumlahTiket(jumlahTiket);
        transaksi.setSubtotal(subtotal);
        transaksi.setDiskon(diskon);
        transaksi.setTotalBayar(totalBayar);
        transaksi.setIdUser(idUser);

        boolean berhasil = transaksiDAO.tambahTransaksi(transaksi);

        if (berhasil) {
            redirectWithMessage(response, "Transaksi tiket berhasil disimpan.", "success");
        } else {
            redirectWithMessage(response, "Transaksi tiket gagal disimpan.", "error");
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void redirectWithMessage(HttpServletResponse response, String message, String type)
            throws IOException {
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect("transaksi?" + type + "=" + encodedMessage);
    }
}