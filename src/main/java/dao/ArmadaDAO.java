package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Armada;

public class ArmadaDAO {

    public List<Armada> getAllArmada(String keyword, String sort) {
        List<Armada> list = new ArrayList<>();

        String orderBy = "id_armada DESC";

        if ("kode_asc".equals(sort)) {
            orderBy = "kode_armada ASC";
        } else if ("kode_desc".equals(sort)) {
            orderBy = "kode_armada DESC";
        } else if ("nama_asc".equals(sort)) {
            orderBy = "nama_bus ASC";
        } else if ("nama_desc".equals(sort)) {
            orderBy = "nama_bus DESC";
        } else if ("plat_asc".equals(sort)) {
            orderBy = "plat_nomor ASC";
        } else if ("plat_desc".equals(sort)) {
            orderBy = "plat_nomor DESC";
        } else if ("kapasitas_asc".equals(sort)) {
            orderBy = "kapasitas ASC";
        } else if ("kapasitas_desc".equals(sort)) {
            orderBy = "kapasitas DESC";
        }

        String sql = "SELECT * FROM armada "
                + "WHERE kode_armada LIKE ? "
                + "OR nama_bus LIKE ? "
                + "OR plat_nomor LIKE ? "
                + "OR fasilitas LIKE ? "
                + "OR status LIKE ? "
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
                Armada armada = new Armada();
                armada.setIdArmada(rs.getInt("id_armada"));
                armada.setKodeArmada(rs.getString("kode_armada"));
                armada.setNamaBus(rs.getString("nama_bus"));
                armada.setPlatNomor(rs.getString("plat_nomor"));
                armada.setKapasitas(rs.getInt("kapasitas"));
                armada.setFasilitas(rs.getString("fasilitas"));
                armada.setStatus(rs.getString("status"));

                list.add(armada);
            }

        } catch (Exception e) {
            System.out.println("Error getAllArmada: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Armada getArmadaById(int idArmada) {
        Armada armada = null;

        String sql = "SELECT * FROM armada WHERE id_armada = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idArmada);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                armada = new Armada();
                armada.setIdArmada(rs.getInt("id_armada"));
                armada.setKodeArmada(rs.getString("kode_armada"));
                armada.setNamaBus(rs.getString("nama_bus"));
                armada.setPlatNomor(rs.getString("plat_nomor"));
                armada.setKapasitas(rs.getInt("kapasitas"));
                armada.setFasilitas(rs.getString("fasilitas"));
                armada.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {
            System.out.println("Error getArmadaById: " + e.getMessage());
            e.printStackTrace();
        }

        return armada;
    }

    public boolean tambahArmada(Armada armada) {
        String sql = "INSERT INTO armada "
                + "(kode_armada, nama_bus, plat_nomor, kapasitas, fasilitas, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, armada.getKodeArmada());
            ps.setString(2, armada.getNamaBus());
            ps.setString(3, armada.getPlatNomor());
            ps.setInt(4, armada.getKapasitas());
            ps.setString(5, armada.getFasilitas());
            ps.setString(6, armada.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error tambahArmada: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateArmada(Armada armada) {
        String sql = "UPDATE armada SET "
                + "kode_armada = ?, "
                + "nama_bus = ?, "
                + "plat_nomor = ?, "
                + "kapasitas = ?, "
                + "fasilitas = ?, "
                + "status = ? "
                + "WHERE id_armada = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, armada.getKodeArmada());
            ps.setString(2, armada.getNamaBus());
            ps.setString(3, armada.getPlatNomor());
            ps.setInt(4, armada.getKapasitas());
            ps.setString(5, armada.getFasilitas());
            ps.setString(6, armada.getStatus());
            ps.setInt(7, armada.getIdArmada());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updateArmada: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusArmada(int idArmada) {
        String sql = "DELETE FROM armada WHERE id_armada = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idArmada);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error hapusArmada: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cekKodeArmada(String kodeArmada) {
        String sql = "SELECT kode_armada FROM armada WHERE kode_armada = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kodeArmada.trim());
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error cekKodeArmada: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cekKodeArmadaSaatUpdate(String kodeArmada, int idArmada) {
        String sql = "SELECT kode_armada FROM armada WHERE kode_armada = ? AND id_armada != ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kodeArmada.trim());
            ps.setInt(2, idArmada);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error cekKodeArmadaSaatUpdate: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cekPlatNomor(String platNomor) {
        String sql = "SELECT plat_nomor FROM armada WHERE plat_nomor = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, platNomor.trim());
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error cekPlatNomor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cekPlatNomorSaatUpdate(String platNomor, int idArmada) {
        String sql = "SELECT plat_nomor FROM armada WHERE plat_nomor = ? AND id_armada != ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, platNomor.trim());
            ps.setInt(2, idArmada);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error cekPlatNomorSaatUpdate: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int hitungTotalArmada() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM armada";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            System.out.println("Error hitungTotalArmada: " + e.getMessage());
            e.printStackTrace();
        }

        return total;
    }
}