package controller;

import config.Koneksi;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@WebServlet(name = "LaporanServlet", urlPatterns = {"/laporan"})
public class LaporanServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            request.getRequestDispatcher("laporan.jsp").forward(request, response);
            return;
        }

        prosesCetak(request, response, action);
    }

    private void prosesCetak(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {

        String reportFile;
        String namaPdf;
        Map<String, Object> params = new HashMap<>();

        try {
            switch (action) {
                case "tujuan":
                    reportFile = "laporan_tujuan.jrxml";
                    namaPdf = "laporan_data_tujuan.pdf";
                    break;

                case "armada":
                    reportFile = "laporan_armada.jrxml";
                    namaPdf = "laporan_data_armada.pdf";
                    break;

                case "penumpang":
                    reportFile = "laporan_penumpang.jrxml";
                    namaPdf = "laporan_data_penumpang.pdf";
                    break;

                case "user":
                    reportFile = "laporan_user.jrxml";
                    namaPdf = "laporan_data_user.pdf";
                    break;

                case "transaksi":
                    reportFile = "laporan_transaksi.jrxml";
                    namaPdf = "laporan_transaksi.pdf";
                    params.put("tanggal_awal", Date.valueOf("1900-01-01"));
                    params.put("tanggal_akhir", Date.valueOf("2100-12-31"));
                    break;

                case "transaksi_tanggal":
                    reportFile = "laporan_transaksi.jrxml";
                    namaPdf = "laporan_transaksi_periode.pdf";

                    String tanggalAwal = request.getParameter("tanggal_awal");
                    String tanggalAkhir = request.getParameter("tanggal_akhir");

                    if (isEmpty(tanggalAwal) || isEmpty(tanggalAkhir)) {
                        request.setAttribute("error", "Tanggal awal dan tanggal akhir wajib diisi.");
                        request.getRequestDispatcher("laporan.jsp").forward(request, response);
                        return;
                    }

                    params.put("tanggal_awal", Date.valueOf(tanggalAwal));
                    params.put("tanggal_akhir", Date.valueOf(tanggalAkhir));
                    break;

                case "pendapatan":
                    reportFile = "laporan_pendapatan.jrxml";
                    namaPdf = "laporan_pendapatan.pdf";

                    String awal = request.getParameter("tanggal_awal");
                    String akhir = request.getParameter("tanggal_akhir");

                    if (isEmpty(awal)) {
                        awal = "1900-01-01";
                    }

                    if (isEmpty(akhir)) {
                        akhir = "2100-12-31";
                    }

                    params.put("tanggal_awal", Date.valueOf(awal));
                    params.put("tanggal_akhir", Date.valueOf(akhir));
                    break;

                default:
                    request.setAttribute("error", "Jenis laporan tidak valid.");
                    request.getRequestDispatcher("laporan.jsp").forward(request, response);
                    return;
            }

            String reportPath = getServletContext().getRealPath("/reports/" + reportFile);

            if (reportPath == null) {
                request.setAttribute("error", "Path report tidak ditemukan.");
                request.getRequestDispatcher("laporan.jsp").forward(request, response);
                return;
            }

            File fileReport = new File(reportPath);

            if (!fileReport.exists()) {
                request.setAttribute("error", "File report tidak ditemukan di path: " + reportPath);
                request.getRequestDispatcher("laporan.jsp").forward(request, response);
                return;
            }

            byte[] pdfBytes = buatPdf(reportPath, params);

            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + namaPdf + "\"");
            response.setContentLength(pdfBytes.length);

            try (OutputStream out = response.getOutputStream()) {
                out.write(pdfBytes);
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Gagal mencetak laporan: " + e.getClass().getName() + " - " + e.getMessage());
            request.getRequestDispatcher("laporan.jsp").forward(request, response);
        }
    }

    private byte[] buatPdf(String reportPath, Map<String, Object> params) throws Exception {
        try (Connection conn = Koneksi.getConnection();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

            return baos.toByteArray();
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
