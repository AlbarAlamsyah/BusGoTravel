package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Transaksi;
import model.Tujuan;
import model.Armada;
import model.Penumpang;

public class TransaksiDAO {

    public List<Transaksi> getAllTransaksi(String keyword, String sort) {
        List<Transaksi> list = new ArrayList<>();

        String orderBy = "t.id_transaksi DESC";

        if ("kode_asc".equals(sort)) {
            orderBy = "t.kode_transaksi ASC";
        } else if ("kode_desc".equals(sort)) {
            orderBy = "t.kode_transaksi DESC";
        } else if ("tanggal_asc".equals(sort)) {
            orderBy = "t.tanggal_transaksi ASC";
        } else if ("tanggal_desc".equals(sort)) {
            orderBy = "t.tanggal_transaksi DESC";
        } else if ("total_asc".equals(sort)) {
            orderBy = "t.total_bayar ASC";
        } else if ("total_desc".equals(sort)) {
            orderBy = "t.total_bayar DESC";
        }

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
                + "WHERE t.kode_transaksi LIKE ? "
                + "OR t.tanggal_transaksi LIKE ? "
                + "OR tj.kota_asal LIKE ? "
                + "OR tj.kota_tujuan LIKE ? "
                + "OR a.nama_bus LIKE ? "
                + "OR p.nama_penumpang LIKE ? "
                + "OR t.kelas_tiket LIKE ? "
                + "ORDER BY " + orderBy;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String key = "%" + (keyword == null ? "" : keyword.trim()) + "%";

            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);
            ps.setString(5, key);
            ps.setString(6, key);
            ps.setString(7, key);

            ResultSet rs = ps.executeQuery();

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
            System.out.println("Error getAllTransaksi: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public boolean tambahTransaksi(Transaksi transaksi) {
        String sql = "INSERT INTO transaksi "
                + "(kode_transaksi, tanggal_transaksi, id_tujuan, id_armada, id_penumpang, "
                + "kelas_tiket, harga_tiket, jumlah_tiket, subtotal, diskon, total_bayar, id_user) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, transaksi.getKodeTransaksi());
            ps.setString(2, transaksi.getTanggalTransaksi());
            ps.setInt(3, transaksi.getIdTujuan());
            ps.setInt(4, transaksi.getIdArmada());
            ps.setInt(5, transaksi.getIdPenumpang());
            ps.setString(6, transaksi.getKelasTiket());
            ps.setDouble(7, transaksi.getHargaTiket());
            ps.setInt(8, transaksi.getJumlahTiket());
            ps.setDouble(9, transaksi.getSubtotal());
            ps.setDouble(10, transaksi.getDiskon());
            ps.setDouble(11, transaksi.getTotalBayar());
            ps.setInt(12, transaksi.getIdUser());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error tambahTransaksi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusTransaksi(int idTransaksi) {
        String sql = "DELETE FROM transaksi WHERE id_transaksi = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTransaksi);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error hapusTransaksi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Tujuan> getTujuanAktif() {
        List<Tujuan> list = new ArrayList<>();

        String sql = "SELECT * FROM tujuan WHERE status = 'Aktif' ORDER BY kota_tujuan ASC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tujuan t = new Tujuan();

                t.setIdTujuan(rs.getInt("id_tujuan"));
                t.setKodeTujuan(rs.getString("kode_tujuan"));
                t.setKotaAsal(rs.getString("kota_asal"));
                t.setKotaTujuan(rs.getString("kota_tujuan"));
                t.setHargaEkonomi(rs.getDouble("harga_ekonomi"));
                t.setHargaBisnis(rs.getDouble("harga_bisnis"));
                t.setHargaEksekutif(rs.getDouble("harga_eksekutif"));
                t.setStatus(rs.getString("status"));

                list.add(t);
            }

        } catch (Exception e) {
            System.out.println("Error getTujuanAktif: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Armada> getArmadaAktif() {
        List<Armada> list = new ArrayList<>();

        String sql = "SELECT * FROM armada WHERE status = 'Aktif' ORDER BY nama_bus ASC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Armada a = new Armada();

                a.setIdArmada(rs.getInt("id_armada"));
                a.setKodeArmada(rs.getString("kode_armada"));
                a.setNamaBus(rs.getString("nama_bus"));
                a.setPlatNomor(rs.getString("plat_nomor"));
                a.setKapasitas(rs.getInt("kapasitas"));
                a.setFasilitas(rs.getString("fasilitas"));
                a.setStatus(rs.getString("status"));

                list.add(a);
            }

        } catch (Exception e) {
            System.out.println("Error getArmadaAktif: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Penumpang> getAllPenumpang() {
        List<Penumpang> list = new ArrayList<>();

        String sql = "SELECT * FROM penumpang ORDER BY nama_penumpang ASC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
            System.out.println("Error getAllPenumpang transaksi: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Tujuan getTujuanById(int idTujuan) {
        Tujuan tujuan = null;

        String sql = "SELECT * FROM tujuan WHERE id_tujuan = ? AND status = 'Aktif'";

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
            System.out.println("Error getTujuanById transaksi: " + e.getMessage());
            e.printStackTrace();
        }

        return tujuan;
    }

    public String generateKodeTransaksi() {
        String kode = "TRX001";

        String sql = "SELECT kode_transaksi FROM transaksi ORDER BY id_transaksi DESC LIMIT 1";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastKode = rs.getString("kode_transaksi");
                int nomor = Integer.parseInt(lastKode.substring(3));
                nomor++;

                kode = String.format("TRX%03d", nomor);
            }

        } catch (Exception e) {
            System.out.println("Error generateKodeTransaksi: " + e.getMessage());
            e.printStackTrace();
        }

        return kode;
    }

    public int hitungTotalTransaksi() {
        int total = 0;

        String sql = "SELECT COUNT(*) AS total FROM transaksi";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            System.out.println("Error hitungTotalTransaksi: " + e.getMessage());
            e.printStackTrace();
        }

        return total;
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
}