package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Tujuan;

public class TujuanDAO {

    public List<Tujuan> getAllTujuan(String keyword, String sort) {
        List<Tujuan> list = new ArrayList<>();

        String orderBy = "id_tujuan DESC";

        if ("kode_asc".equals(sort)) {
            orderBy = "kode_tujuan ASC";
        } else if ("kode_desc".equals(sort)) {
            orderBy = "kode_tujuan DESC";
        } else if ("asal_asc".equals(sort)) {
            orderBy = "kota_asal ASC";
        } else if ("asal_desc".equals(sort)) {
            orderBy = "kota_asal DESC";
        } else if ("tujuan_asc".equals(sort)) {
            orderBy = "kota_tujuan ASC";
        } else if ("tujuan_desc".equals(sort)) {
            orderBy = "kota_tujuan DESC";
        } else if ("harga_asc".equals(sort)) {
            orderBy = "harga_ekonomi ASC";
        } else if ("harga_desc".equals(sort)) {
            orderBy = "harga_ekonomi DESC";
        }

        String sql = "SELECT * FROM tujuan "
                   + "WHERE kode_tujuan LIKE ? "
                   + "OR kota_asal LIKE ? "
                   + "OR kota_tujuan LIKE ? "
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
                Tujuan tujuan = new Tujuan();
                tujuan.setIdTujuan(rs.getInt("id_tujuan"));
                tujuan.setKodeTujuan(rs.getString("kode_tujuan"));
                tujuan.setKotaAsal(rs.getString("kota_asal"));
                tujuan.setKotaTujuan(rs.getString("kota_tujuan"));
                tujuan.setHargaEkonomi(rs.getDouble("harga_ekonomi"));
                tujuan.setHargaBisnis(rs.getDouble("harga_bisnis"));
                tujuan.setHargaEksekutif(rs.getDouble("harga_eksekutif"));
                tujuan.setStatus(rs.getString("status"));

                list.add(tujuan);
            }

        } catch (Exception e) {
            System.out.println("Error getAllTujuan: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Tujuan getTujuanById(int idTujuan) {
        Tujuan tujuan = null;

        String sql = "SELECT * FROM tujuan WHERE id_tujuan = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTujuan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tujuan = new Tujuan();
                tujuan.setIdTujuan(rs.getInt("id_tujuan"));
                tujuan.setKodeTujuan(rs.getString("kode_tujuan"));
                tujuan.setKotaAsal(rs.getString("kota_asal"));
                tujuan.setKotaTujuan(rs.getString("kota_tujuan"));
                tujuan.setHargaEkonomi(rs.getDouble("harga_ekonomi"));
                tujuan.setHargaBisnis(rs.getDouble("harga_bisnis"));
                tujuan.setHargaEksekutif(rs.getDouble("harga_eksekutif"));
                tujuan.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {
            System.out.println("Error getTujuanById: " + e.getMessage());
            e.printStackTrace();
        }

        return tujuan;
    }

    public boolean tambahTujuan(Tujuan tujuan) {
        String sql = "INSERT INTO tujuan "
                   + "(kode_tujuan, kota_asal, kota_tujuan, harga_ekonomi, harga_bisnis, harga_eksekutif, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tujuan.getKodeTujuan());
            ps.setString(2, tujuan.getKotaAsal());
            ps.setString(3, tujuan.getKotaTujuan());
            ps.setDouble(4, tujuan.getHargaEkonomi());
            ps.setDouble(5, tujuan.getHargaBisnis());
            ps.setDouble(6, tujuan.getHargaEksekutif());
            ps.setString(7, tujuan.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error tambahTujuan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTujuan(Tujuan tujuan) {
        String sql = "UPDATE tujuan SET "
                   + "kode_tujuan = ?, "
                   + "kota_asal = ?, "
                   + "kota_tujuan = ?, "
                   + "harga_ekonomi = ?, "
                   + "harga_bisnis = ?, "
                   + "harga_eksekutif = ?, "
                   + "status = ? "
                   + "WHERE id_tujuan = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tujuan.getKodeTujuan());
            ps.setString(2, tujuan.getKotaAsal());
            ps.setString(3, tujuan.getKotaTujuan());
            ps.setDouble(4, tujuan.getHargaEkonomi());
            ps.setDouble(5, tujuan.getHargaBisnis());
            ps.setDouble(6, tujuan.getHargaEksekutif());
            ps.setString(7, tujuan.getStatus());
            ps.setInt(8, tujuan.getIdTujuan());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updateTujuan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusTujuan(int idTujuan) {
        String sql = "DELETE FROM tujuan WHERE id_tujuan = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTujuan);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error hapusTujuan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cekKodeTujuan(String kodeTujuan) {
        String sql = "SELECT kode_tujuan FROM tujuan WHERE kode_tujuan = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kodeTujuan.trim());
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error cekKodeTujuan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cekKodeTujuanSaatUpdate(String kodeTujuan, int idTujuan) {
        String sql = "SELECT kode_tujuan FROM tujuan WHERE kode_tujuan = ? AND id_tujuan != ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kodeTujuan.trim());
            ps.setInt(2, idTujuan);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error cekKodeTujuanSaatUpdate: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int hitungTotalTujuan() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM tujuan";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            System.out.println("Error hitungTotalTujuan: " + e.getMessage());
            e.printStackTrace();
        }

        return total;
    }
}