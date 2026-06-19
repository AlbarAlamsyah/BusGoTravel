<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Transaksi"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%
    String namaLengkap = (String) session.getAttribute("namaLengkap");
    String role = (String) session.getAttribute("role");

    Integer totalTujuan = (Integer) request.getAttribute("totalTujuan");
    Integer totalArmada = (Integer) request.getAttribute("totalArmada");
    Integer totalPenumpang = (Integer) request.getAttribute("totalPenumpang");
    Integer totalUser = (Integer) request.getAttribute("totalUser");
    Integer totalTransaksi = (Integer) request.getAttribute("totalTransaksi");

    String totalPendapatan = (String) request.getAttribute("totalPendapatan");
    String pendapatanHariIni = (String) request.getAttribute("pendapatanHariIni");
    List<Transaksi> transaksiTerbaru = (List<Transaksi>) request.getAttribute("transaksiTerbaru");
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - BusGo Travel</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/css/style.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg app-navbar sticky-top">
    <div class="container-fluid px-4">
        <a class="navbar-brand fw-bold" href="dashboard">BusGo Travel</a>
        <div class="d-flex align-items-center">
            <div class="text-end me-3 d-none d-md-block">
                <div class="fw-bold small"><%= namaLengkap %></div>
                <div class="text-muted small"><%= role %></div>
            </div>
            <a href="logout" class="btn btn-outline-danger btn-sm">Logout</a>
        </div>
    </div>
</nav>

<div class="container-fluid app-shell">
    <div class="row">
        <div class="col-lg-2 sidebar p-0">
            <div class="sidebar-inner">
                <div class="menu-title">Menu Utama</div>
                <a href="dashboard" class="menu-link active">Dashboard</a>

                <div class="menu-title">Master Data</div>
                <a href="tujuan" class="menu-link">Data Tujuan</a>
                <a href="armada" class="menu-link">Data Armada</a>
                <a href="penumpang" class="menu-link">Data Penumpang</a>
                <% if ("Admin".equals(role)) { %>
                    <a href="user" class="menu-link">Data User</a>
                <% } %>

                <div class="menu-title">Transaksi</div>
                <a href="transaksi" class="menu-link">Transaksi Tiket</a>

                <div class="menu-title">Laporan</div>
                <a href="laporan" class="menu-link">Cetak Laporan</a>

                <div class="menu-title">Sistem</div>
                <a href="logout" class="menu-link text-danger">Logout</a>
                <a href="#" class="menu-link text-danger" onclick="keluarAplikasi()">Exit</a>
            </div>
        </div>

        <div class="col-lg-10 content-area">
            <div class="page-header">
                <h2>Dashboard</h2>
                <p>Selamat datang, <strong><%= namaLengkap %></strong>. Pantau operasional BusGo Travel dari satu halaman.</p>
            </div>

            <% if (request.getParameter("error") != null) { %>
                <div class="alert alert-danger"><%= request.getParameter("error") %></div>
            <% } %>

            <div class="row g-4 mb-4">
                <div class="col-md-6 col-xl-3"><div class="stat-card"><h6>Total Tujuan</h6><h2><%= totalTujuan != null ? totalTujuan : 0 %></h2><p>Rute perjalanan tersedia</p></div></div>
                <div class="col-md-6 col-xl-3"><div class="stat-card"><h6>Total Armada</h6><h2><%= totalArmada != null ? totalArmada : 0 %></h2><p>Unit bus terdaftar</p></div></div>
                <div class="col-md-6 col-xl-3"><div class="stat-card"><h6>Total Penumpang</h6><h2><%= totalPenumpang != null ? totalPenumpang : 0 %></h2><p>Data pelanggan travel</p></div></div>
                <div class="col-md-6 col-xl-3"><div class="stat-card"><h6>Total User</h6><h2><%= totalUser != null ? totalUser : 0 %></h2><p>Akun pengguna sistem</p></div></div>
            </div>

            <div class="row g-4 mb-4">
                <div class="col-lg-4"><div class="stat-card"><h6>Total Transaksi</h6><h2><%= totalTransaksi != null ? totalTransaksi : 0 %></h2><p>Jumlah pemesanan tiket</p></div></div>
                <div class="col-lg-4"><div class="stat-card"><h6>Total Pendapatan</h6><h2 style="font-size: 28px;"><%= totalPendapatan != null ? totalPendapatan : "Rp0" %></h2><p>Akumulasi seluruh transaksi</p></div></div>
                <div class="col-lg-4"><div class="stat-card"><h6>Pendapatan Hari Ini</h6><h2 style="font-size: 28px;"><%= pendapatanHariIni != null ? pendapatanHariIni : "Rp0" %></h2><p>Transaksi tanggal hari ini</p></div></div>
            </div>

            <div class="row g-4">
                <div class="col-xl-8">
                    <div class="card card-modern">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0 fw-bold">Transaksi Terbaru</h5>
                            <a href="transaksi" class="btn btn-primary btn-sm">Lihat Semua</a>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead><tr><th>Kode</th><th>Tanggal</th><th>Penumpang</th><th>Rute</th><th>Total</th></tr></thead>
                                    <tbody>
                                    <% if (transaksiTerbaru != null && !transaksiTerbaru.isEmpty()) {
                                        for (Transaksi t : transaksiTerbaru) { %>
                                        <tr>
                                            <td><strong><%= t.getKodeTransaksi() %></strong><br><small class="text-muted"><%= t.getUsername() %></small></td>
                                            <td><%= t.getTanggalTransaksi() %></td>
                                            <td><div class="fw-semibold"><%= t.getNamaPenumpang() %></div><small class="text-muted"><%= t.getKelasTiket() %> - <%= t.getJumlahTiket() %> tiket</small></td>
                                            <td><%= t.getKotaAsal() %> → <%= t.getKotaTujuan() %></td>
                                            <td><strong><%= rupiah.format(t.getTotalBayar()) %></strong></td>
                                        </tr>
                                    <% }} else { %>
                                        <tr><td colspan="5" class="text-center text-muted py-4">Belum ada transaksi terbaru.</td></tr>
                                    <% } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4">
                    <div class="card card-modern mb-4">
                        <div class="card-header"><h5 class="mb-0 fw-bold">Ringkasan Operasional</h5></div>
                        <div class="card-body">
                            <div class="mb-3"><div class="fw-semibold">Status Layanan</div><small class="text-muted">Sistem aktif dan siap digunakan untuk melayani pemesanan tiket.</small></div>
                            <div class="mb-3"><div class="fw-semibold">Manajemen Data</div><small class="text-muted">Data tujuan, armada, penumpang, dan transaksi dikelola secara terpusat.</small></div>
                            <div><div class="fw-semibold">Laporan Operasional</div><small class="text-muted">Laporan dapat dicetak untuk membantu pemantauan dan evaluasi pendapatan.</small></div>
                        </div>
                    </div>

                    <div class="card card-modern">
                        <div class="card-header"><h5 class="mb-0 fw-bold">Akses Cepat</h5></div>
                        <div class="card-body d-grid gap-2">
                            <a href="transaksi" class="btn btn-primary">Buat Transaksi Baru</a>
                            <a href="tujuan" class="btn btn-outline-primary">Kelola Tujuan</a>
                            <a href="armada" class="btn btn-outline-primary">Kelola Armada</a>
                            <a href="penumpang" class="btn btn-outline-primary">Kelola Penumpang</a>
                            <% if ("Admin".equals(role)) { %><a href="user" class="btn btn-outline-primary">Kelola User</a><% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

                        
<script>
function keluarAplikasi() {
    const konfirmasi = confirm("Apakah Anda yakin ingin keluar dari aplikasi?");
    if (konfirmasi) {
        window.location.href = "logout";
    }
}
</script>
</body>
</html>
