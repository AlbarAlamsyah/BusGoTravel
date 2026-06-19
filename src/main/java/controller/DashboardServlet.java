package controller;

import dao.DashboardDAO;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    private final DashboardDAO dashboardDAO = new DashboardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        int totalTujuan = dashboardDAO.hitungTotalTujuan();
        int totalArmada = dashboardDAO.hitungTotalArmada();
        int totalPenumpang = dashboardDAO.hitungTotalPenumpang();
        int totalUser = dashboardDAO.hitungTotalUser();
        int totalTransaksi = dashboardDAO.hitungTotalTransaksi();

        double totalPendapatan = dashboardDAO.hitungTotalPendapatan();
        double pendapatanHariIni = dashboardDAO.hitungPendapatanHariIni();

        request.setAttribute("totalTujuan", totalTujuan);
        request.setAttribute("totalArmada", totalArmada);
        request.setAttribute("totalPenumpang", totalPenumpang);
        request.setAttribute("totalUser", totalUser);
        request.setAttribute("totalTransaksi", totalTransaksi);
        request.setAttribute("totalPendapatan", rupiah.format(totalPendapatan));
        request.setAttribute("pendapatanHariIni", rupiah.format(pendapatanHariIni));
        request.setAttribute("transaksiTerbaru", dashboardDAO.getTransaksiTerbaru());

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}