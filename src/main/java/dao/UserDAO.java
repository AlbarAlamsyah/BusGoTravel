package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.User;
import util.PasswordUtil;

public class UserDAO {

    public User login(String username, String password) {
        User user = null;

        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND status = 'Aktif'";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String usernameInput = username.trim();
            String passwordInput = password.trim();
            String hashedPassword = PasswordUtil.hashPassword(passwordInput);

            ps.setString(1, usernameInput);
            ps.setString(2, hashedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();

                user.setIdUser(rs.getInt("id_user"));
                user.setNamaLengkap(rs.getString("nama_lengkap"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {
            System.out.println("ERROR LOGIN: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getAllUsers(String keyword, String sort) {
        List<User> list = new ArrayList<>();

        String orderBy = "id_user DESC";

        if ("nama_asc".equals(sort)) {
            orderBy = "nama_lengkap ASC";
        } else if ("nama_desc".equals(sort)) {
            orderBy = "nama_lengkap DESC";
        } else if ("username_asc".equals(sort)) {
            orderBy = "username ASC";
        } else if ("username_desc".equals(sort)) {
            orderBy = "username DESC";
        } else if ("role_asc".equals(sort)) {
            orderBy = "role ASC";
        } else if ("role_desc".equals(sort)) {
            orderBy = "role DESC";
        } else if ("status_asc".equals(sort)) {
            orderBy = "status ASC";
        } else if ("status_desc".equals(sort)) {
            orderBy = "status DESC";
        }

        String sql = "SELECT * FROM users "
                + "WHERE nama_lengkap LIKE ? "
                + "OR username LIKE ? "
                + "OR role LIKE ? "
                + "OR status LIKE ? "
                + "ORDER BY " + orderBy;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String key = "%" + (keyword == null ? "" : keyword.trim()) + "%";

            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();

                user.setIdUser(rs.getInt("id_user"));
                user.setNamaLengkap(rs.getString("nama_lengkap"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));

                list.add(user);
            }

        } catch (Exception e) {
            System.out.println("ERROR GET ALL USERS: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public User getUserById(int idUser) {
        User user = null;

        String sql = "SELECT * FROM users WHERE id_user = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();

                user.setIdUser(rs.getInt("id_user"));
                user.setNamaLengkap(rs.getString("nama_lengkap"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {
            System.out.println("ERROR GET USER BY ID: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    public boolean tambahUser(User user) {
        String sql = "INSERT INTO users "
                + "(nama_lengkap, username, password, role, status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String hashedPassword = PasswordUtil.hashPassword(user.getPassword().trim());

            ps.setString(1, user.getNamaLengkap());
            ps.setString(2, user.getUsername());
            ps.setString(3, hashedPassword);
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("ERROR TAMBAH USER: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET "
                + "nama_lengkap = ?, "
                + "username = ?, "
                + "role = ?, "
                + "status = ? "
                + "WHERE id_user = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getNamaLengkap());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getStatus());
            ps.setInt(5, user.getIdUser());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("ERROR UPDATE USER: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserDenganPassword(User user) {
        String sql = "UPDATE users SET "
                + "nama_lengkap = ?, "
                + "username = ?, "
                + "password = ?, "
                + "role = ?, "
                + "status = ? "
                + "WHERE id_user = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String hashedPassword = PasswordUtil.hashPassword(user.getPassword().trim());

            ps.setString(1, user.getNamaLengkap());
            ps.setString(2, user.getUsername());
            ps.setString(3, hashedPassword);
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());
            ps.setInt(6, user.getIdUser());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("ERROR UPDATE USER DENGAN PASSWORD: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusUser(int idUser) {
        String sql = "DELETE FROM users WHERE id_user = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("ERROR HAPUS USER: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cekUsername(String username) {
        String sql = "SELECT username FROM users WHERE username = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username.trim());

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("ERROR CEK USERNAME: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cekUsernameSaatUpdate(String username, int idUser) {
        String sql = "SELECT username FROM users WHERE username = ? AND id_user != ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            ps.setInt(2, idUser);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("ERROR CEK USERNAME SAAT UPDATE: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int hitungTotalUser() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM users";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            System.out.println("ERROR HITUNG TOTAL USER: " + e.getMessage());
            e.printStackTrace();
        }

        return total;
    }
}