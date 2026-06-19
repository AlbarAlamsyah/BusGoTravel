package controller;

import dao.UserDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (!isAdmin(request)) {
            response.sendRedirect("dashboard?error=" + URLEncoder.encode("Akses ditolak. Halaman user hanya untuk Admin.", StandardCharsets.UTF_8));
            return;
        }

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userDAO.getUserById(id);
            request.setAttribute("userEdit", user);
        }

        if ("hapus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Integer idLogin = (Integer) request.getSession().getAttribute("idUser");

            if (idLogin != null && id == idLogin) {
                redirectWithMessage(response, "User yang sedang login tidak boleh dihapus.", "error");
                return;
            }

            boolean berhasil = userDAO.hapusUser(id);

            if (berhasil) {
                redirectWithMessage(response, "Data user berhasil dihapus.", "success");
            } else {
                redirectWithMessage(response, "Data user gagal dihapus. Kemungkinan user sudah dipakai transaksi.", "error");
            }
            return;
        }

        String keyword = request.getParameter("keyword");
        String sort = request.getParameter("sort");

        request.setAttribute("listUser", userDAO.getAllUsers(keyword, sort));
        request.setAttribute("keyword", keyword == null ? "" : keyword);
        request.setAttribute("sort", sort == null ? "" : sort);

        request.getRequestDispatcher("user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (!isAdmin(request)) {
            response.sendRedirect("dashboard?error=" + URLEncoder.encode("Akses ditolak. Halaman user hanya untuk Admin.", StandardCharsets.UTF_8));
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String idUserStr = request.getParameter("idUser");
        String namaLengkap = request.getParameter("namaLengkap");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String status = request.getParameter("status");

        if (isEmpty(namaLengkap) || isEmpty(username) || isEmpty(role) || isEmpty(status)) {
            redirectWithMessage(response, "Nama lengkap, username, role, dan status wajib diisi.", "error");
            return;
        }

        if (username.trim().length() < 4) {
            redirectWithMessage(response, "Username minimal 4 karakter.", "error");
            return;
        }

        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            redirectWithMessage(response, "Username hanya boleh huruf, angka, dan underscore.", "error");
            return;
        }

        User user = new User();
        user.setNamaLengkap(namaLengkap.trim());
        user.setUsername(username.trim());
        user.setRole(role);
        user.setStatus(status);

        boolean berhasil;

        if ("update".equals(action)) {
            int idUser = Integer.parseInt(idUserStr);

            if (userDAO.cekUsernameSaatUpdate(user.getUsername(), idUser)) {
                redirectWithMessage(response, "Username sudah digunakan user lain.", "error");
                return;
            }

            user.setIdUser(idUser);

            if (!isEmpty(password)) {
                if (password.trim().length() < 6) {
                    redirectWithMessage(response, "Password minimal 6 karakter.", "error");
                    return;
                }

                user.setPassword(password.trim());
                berhasil = userDAO.updateUserDenganPassword(user);
            } else {
                berhasil = userDAO.updateUser(user);
            }

            if (berhasil) {
                redirectWithMessage(response, "Data user berhasil diperbarui.", "success");
            } else {
                redirectWithMessage(response, "Data user gagal diperbarui.", "error");
            }

        } else {
            if (isEmpty(password)) {
                redirectWithMessage(response, "Password wajib diisi untuk user baru.", "error");
                return;
            }

            if (password.trim().length() < 6) {
                redirectWithMessage(response, "Password minimal 6 karakter.", "error");
                return;
            }

            if (userDAO.cekUsername(user.getUsername())) {
                redirectWithMessage(response, "Username sudah terdaftar.", "error");
                return;
            }

            user.setPassword(password.trim());
            berhasil = userDAO.tambahUser(user);

            if (berhasil) {
                redirectWithMessage(response, "Data user berhasil ditambahkan.", "success");
            } else {
                redirectWithMessage(response, "Data user gagal ditambahkan.", "error");
            }
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return false;
        }

        Object role = session.getAttribute("role");

        return role != null && "Admin".equals(role.toString());
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void redirectWithMessage(HttpServletResponse response, String message, String type)
            throws IOException {
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect("user?" + type + "=" + encodedMessage);
    }
}