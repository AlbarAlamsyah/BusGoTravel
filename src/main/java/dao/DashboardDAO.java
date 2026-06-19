package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Transaksi;

public class DashboardDAO {

    public int hitungTotalTujuan() {
        return hitungData("SELECT COUNT(*) AS total FROM tujuan");
    }

    public int hitungTotalArmada() {
        return hitungData("SELECT COUNT(*) AS total FROM armada");
    }

    public int hitungTotalPenumpang() {
        return hitungData("SELECT COUNT(*) AS total FROM penumpang");
    }

    public int hitungTotalUser() {
        return hitungData("SELECT COUNT(*) AS total FROM users");
    }

    public int hitungTotalTransaksi() {
        return hitungData("SELECT COUNT(*) AS total FROM transaksi");
    }

    public double hitungTotalPendapatan() {
        double total = 0;

        String sql = "SELECT COALESCE(SUM(total_bayar), 0) AS total FROM transaksi";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            System.out.println("Error hitungTotalPendapatan: " + e.getMessage());
            e.printStackTrace();
        }

        return total;
    }

    public double hitungPendapatanHariIni() {
        double total = 0;

        String sql = "SELECT COALESCE(SUM(total_bayar), 0) AS total "
                   + "FROM transaksi "
                   + "WHERE tanggal_transaksi = CURDATE()";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            System.out.println("Error hitungPendapatanHariIni: " + e.getMessage());
            e.printStackTrace();
        }

        return total;
    }

    public List<Transaksi> getTransaksiTerbaru() {
        List<Transaksi> list = new ArrayList<>();

        String sql = "SELECT t.*, "
                + "tj.kota_asal, tj.kota_tujuan, "
                + "a.nama_bus, a.plat_nomor, "
                + "p.nama_penumpang, "
                + "u.username "
                + "FROM transaksi t "
                + "JOIN tujuan tj ON t.id_tujuan = tj.id_tujuan "
                + "JOIN armada a ON t.id_armada = a.id_armada "
                + "JOIN penumpang p ON t.id_penumpang = p.id_penumpang "
                + "JOIN users u ON t.id_user = u.id_user "
                + "ORDER BY t.id_transaksi DESC "
                + "LIMIT 5";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Transaksi t = new Transaksi();

                t.setIdTransaksi(rs.getInt("id_transaksi"));
                t.setKodeTransaksi(rs.getString("kode_transaksi"));
                t.setTanggalTransaksi(rs.getString("tanggal_transaksi"));
                t.setIdTujuan(rs.getInt("id_tujuan"));
                t.setIdArmada(rs.getInt("id_armada"));
                t.setIdPenumpang(rs.getInt("id_penumpang"));
                t.setKelasTiket(rs.getString("kelas_tiket"));
                t.setHargaTiket(rs.getDouble("harga_tiket"));
                t.setJumlahTiket(rs.getInt("jumlah_tiket"));
                t.setSubtotal(rs.getDouble("subtotal"));
                t.setDiskon(rs.getDouble("diskon"));
                t.setTotalBayar(rs.getDouble("total_bayar"));
                t.setIdUser(rs.getInt("id_user"));

                t.setKotaAsal(rs.getString("kota_asal"));
                t.setKotaTujuan(rs.getString("kota_tujuan"));
                t.setNamaBus(rs.getString("nama_bus"));
                t.setPlatNomor(rs.getString("plat_nomor"));
                t.setNamaPenumpang(rs.getString("nama_penumpang"));
                t.setUsername(rs.getString("username"));

                list.add(t);
            }

        } catch (Exception e) {
            System.out.println("Error getTransaksiTerbaru: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    private int hitungData(String sql) {
        int total = 0;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            System.out.println("Error hitungData: " + e.getMessage());
            e.printStackTrace();
        }

        return total;
    }
}