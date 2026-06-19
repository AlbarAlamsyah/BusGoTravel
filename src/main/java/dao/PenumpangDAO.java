package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Penumpang;

public class PenumpangDAO {

    public List<Penumpang> getAllPenumpang(String keyword, String sort) {
        List<Penumpang> list = new ArrayList<>();

        String orderBy = "id_penumpang DESC";

        if ("nik_asc".equals(sort)) {
            orderBy = "nik ASC";
        } else if ("nik_desc".equals(sort)) {
            orderBy = "nik DESC";
        } else if ("nama_asc".equals(sort)) {
            orderBy = "nama_penumpang ASC";
        } else if ("nama_desc".equals(sort)) {
            orderBy = "nama_penumpang DESC";
        } else if ("jk_asc".equals(sort)) {
            orderBy = "jenis_kelamin ASC";
        } else if ("jk_desc".equals(sort)) {
            orderBy = "jenis_kelamin DESC";
        }

        String sql = "SELECT * FROM penumpang "
                + "WHERE nik LIKE ? "
                + "OR nama_penumpang LIKE ? "
                + "OR no_hp LIKE ? "
                + "OR alamat LIKE ? "
                + "OR jenis_kelamin LIKE ? "
                + "ORDER BY " + orderBy;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String key = "%" + (keyword == null ? "" : keyword.trim()) + "%";

            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);
            ps.setString(5, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Penumpang p = new Penumpang();
                p.setIdPenumpang(rs.getInt("id_penumpang"));
                p.setNik(rs.getString("nik"));
                p.setNamaPenumpang(rs.getString("nama_penumpang"));
                p.setNoHp(rs.getString("no_hp"));
                p.setAlamat(rs.getString("alamat"));
                p.setJenisKelamin(rs.getString("jenis_kelamin"));

                list.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error getAllPenumpang: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Penumpang getPenumpangById(int idPenumpang) {
        Penumpang penumpang = null;

        String sql = "SELECT * FROM penumpang WHERE id_penumpang = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPenumpang);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                penumpang = new Penumpang();
                penumpang.setIdPenumpang(rs.getInt("id_penumpang"));
                penumpang.setNik(rs.getString("nik"));
                penumpang.setNamaPenumpang(rs.getString("nama_penumpang"));
                penumpang.setNoHp(rs.getString("no_hp"));
                penumpang.setAlamat(rs.getString("alamat"));
                penumpang.setJenisKelamin(rs.getString("jenis_kelamin"));
            }

        } catch (Exception e) {
            System.out.println("Error getPenumpangById: " + e.getMessage());
            e.printStackTrace();
        }

        return penumpang;
    }

    public boolean tambahPenumpang(Penumpang penumpang) {
        String sql = "INSERT INTO penumpang "
                + "(nik, nama_penumpang, no_hp, alamat, jenis_kelamin) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, penumpang.getNik());
            ps.setString(2, penumpang.getNamaPenumpang());
            ps.setString(3, penumpang.getNoHp());
            ps.setString(4, penumpang.getAlamat());
            ps.setString(5, penumpang.getJenisKelamin());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error tambahPenumpang: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePenumpang(Penumpang penumpang) {
        String sql = "UPDATE penumpang SET "
                + "nik = ?, "
                + "nama_penumpang = ?, "
                + "no_hp = ?, "
                + "alamat = ?, "
                + "jenis_kelamin = ? "
                + "WHERE id_penumpang = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, penumpang.getNik());
            ps.setString(2, penumpang.getNamaPenumpang());
            ps.setString(3, penumpang.getNoHp());
            ps.setString(4, penumpang.getAlamat());
            ps.setString(5, penumpang.getJenisKelamin());
            ps.setInt(6, penumpang.getIdPenumpang());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updatePenumpang: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusPenumpang(int idPenumpang) {
        String sql = "DELETE FROM penumpang WHERE id_penumpang = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPenumpang);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error hapusPenumpang: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cekNik(String nik) {
        String sql = "SELECT nik FROM penumpang WHERE nik = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nik.trim());
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error cekNik: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cekNikSaatUpdate(String nik, int idPenumpang) {
        String sql = "SELECT nik FROM penumpang WHERE nik = ? AND id_penumpang != ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nik.trim());
            ps.setInt(2, idPenumpang);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error cekNikSaatUpdate: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int hitungTotalPenumpang() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM penumpang";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            System.out.println("Error hitungTotalPenumpang: " + e.getMessage());
            e.printStackTrace();
        }

        return total;
    }
}