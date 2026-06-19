<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String namaLengkap = (String) session.getAttribute("namaLengkap");
    String role = (String) session.getAttribute("role");
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pusat Laporan - BusGo Travel</title>

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
                <a href="dashboard" class="menu-link">Dashboard</a>

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
                <a href="laporan" class="menu-link active">Cetak Laporan</a>

                <div class="menu-title">Sistem</div>
                <a href="logout" class="menu-link text-danger">Logout</a>
                <a href="#" class="menu-link text-danger" onclick="keluarAplikasi()">Exit</a>
            </div>
        </div>

        <div class="col-lg-10 content-area">
            <div class="page-header">
                <h2>Pusat Laporan</h2>
                <p>Kelola dan cetak laporan operasional BusGo Travel secara cepat dan akurat.</p>
            </div>

            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
            <% } %>

            <div class="row g-4 mb-4">
                <div class="col-md-6 col-xl-3">
                    <div class="card card-modern h-100">
                        <div class="card-body d-flex flex-column">
                            <h5 class="fw-bold mb-2">Data Tujuan</h5>
                            <p class="text-muted flex-grow-1">Daftar rute perjalanan dan harga tiket berdasarkan kelas layanan.</p>
                            <a href="laporan?action=tujuan" target="_blank" class="btn btn-primary w-100">Cetak Laporan</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-xl-3">
                    <div class="card card-modern h-100">
                        <div class="card-body d-flex flex-column">
                            <h5 class="fw-bold mb-2">Data Armada</h5>
                            <p class="text-muted flex-grow-1">Informasi unit bus, kapasitas, fasilitas, plat nomor, dan status armada.</p>
                            <a href="laporan?action=armada" target="_blank" class="btn btn-primary w-100">Cetak Laporan</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-xl-3">
                    <div class="card card-modern h-100">
                        <div class="card-body d-flex flex-column">
                            <h5 class="fw-bold mb-2">Data Penumpang</h5>
                            <p class="text-muted flex-grow-1">Rekap data pelanggan yang terdaftar pada sistem pemesanan tiket.</p>
                            <a href="laporan?action=penumpang" target="_blank" class="btn btn-primary w-100">Cetak Laporan</a>
                        </div>
                    </div>
                </div>

                <% if ("Admin".equals(role)) { %>
                <div class="col-md-6 col-xl-3">
                    <div class="card card-modern h-100">
                        <div class="card-body d-flex flex-column">
                            <h5 class="fw-bold mb-2">Data User</h5>
                            <p class="text-muted flex-grow-1">Daftar akun pengguna yang memiliki akses ke sistem operasional.</p>
                            <a href="laporan?action=user" target="_blank" class="btn btn-primary w-100">Cetak Laporan</a>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>

            <div class="row g-4">
                <div class="col-xl-4">
                    <div class="card card-modern h-100">
                        <div class="card-body d-flex flex-column">
                            <h5 class="fw-bold mb-2">Semua Transaksi</h5>
                            <p class="text-muted flex-grow-1">Laporan seluruh transaksi pemesanan tiket yang sudah tersimpan.</p>
                            <a href="laporan?action=transaksi" target="_blank" class="btn btn-primary w-100">Cetak Semua Transaksi</a>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4">
                    <div class="card card-modern h-100">
                        <div class="card-body">
                            <h5 class="fw-bold mb-2">Transaksi Periode</h5>
                            <p class="text-muted">Cetak transaksi berdasarkan rentang tanggal tertentu.</p>
                            <form action="laporan" method="get" target="_blank">
                                <input type="hidden" name="action" value="transaksi_tanggal">
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Tanggal Awal</label>
                                    <input type="date" name="tanggal_awal" class="form-control" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Tanggal Akhir</label>
                                    <input type="date" name="tanggal_akhir" class="form-control" required>
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Cetak Laporan</button>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4">
                    <div class="card card-modern h-100">
                        <div class="card-body">
                            <h5 class="fw-bold mb-2">Pendapatan</h5>
                            <p class="text-muted">Ringkasan pendapatan berdasarkan periode transaksi.</p>
                            <form action="laporan" method="get" target="_blank">
                                <input type="hidden" name="action" value="pendapatan">
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Tanggal Awal</label>
                                    <input type="date" name="tanggal_awal" class="form-control">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Tanggal Akhir</label>
                                    <input type="date" name="tanggal_akhir" class="form-control">
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Cetak Laporan</button>
                            </form>
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
