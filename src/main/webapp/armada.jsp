<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Armada"%>

<%
    String namaLengkap = (String) session.getAttribute("namaLengkap");
    String role = (String) session.getAttribute("role");

    List<Armada> listArmada = (List<Armada>) request.getAttribute("listArmada");
    Armada armadaEdit = (Armada) request.getAttribute("armadaEdit");

    String keyword = (String) request.getAttribute("keyword");
    String sort = (String) request.getAttribute("sort");

    String actionForm = armadaEdit == null ? "simpan" : "update";
%>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Data Armada - BusGo Travel</title>

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
                <a href="armada" class="menu-link active">Data Armada</a>
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
                <h2>Data Armada</h2>
                <p>Kelola informasi bus, plat nomor, kapasitas, fasilitas, dan status operasional armada.</p>
            </div>

            <% if (request.getParameter("success") != null) { %>
                <div class="alert alert-success">
                    <%= request.getParameter("success") %>
                </div>
            <% } %>

            <% if (request.getParameter("error") != null) { %>
                <div class="alert alert-danger">
                    <%= request.getParameter("error") %>
                </div>
            <% } %>

            <div class="row g-4">

                <div class="col-xl-4">
                    <div class="card card-modern">
                        <div class="card-header">
                            <h5 class="mb-0 fw-bold">
                                <%= armadaEdit == null ? "Tambah Armada" : "Edit Armada" %>
                            </h5>
                        </div>

                        <div class="card-body">
                            <form action="armada" method="post">
                                <input type="hidden" name="action" value="<%= actionForm %>">

                                <% if (armadaEdit != null) { %>
                                    <input type="hidden" name="idArmada" value="<%= armadaEdit.getIdArmada() %>">
                                <% } %>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Kode Armada</label>
                                    <input type="text"
                                           name="kodeArmada"
                                           class="form-control"
                                           placeholder="Contoh: BUS004"
                                           value="<%= armadaEdit != null ? armadaEdit.getKodeArmada() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Nama Bus</label>
                                    <input type="text"
                                           name="namaBus"
                                           class="form-control"
                                           placeholder="Contoh: BusGo Executive 04"
                                           value="<%= armadaEdit != null ? armadaEdit.getNamaBus() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Plat Nomor</label>
                                    <input type="text"
                                           name="platNomor"
                                           class="form-control"
                                           placeholder="Contoh: B 7004 BG"
                                           value="<%= armadaEdit != null ? armadaEdit.getPlatNomor() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Kapasitas</label>
                                    <input type="number"
                                           name="kapasitas"
                                           class="form-control"
                                           placeholder="Contoh: 45"
                                           value="<%= armadaEdit != null ? armadaEdit.getKapasitas() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Fasilitas</label>
                                    <textarea name="fasilitas"
                                              class="form-control"
                                              rows="3"
                                              placeholder="Contoh: AC, Reclining Seat, USB Charger, WiFi"><%= armadaEdit != null ? armadaEdit.getFasilitas() : "" %></textarea>
                                </div>

                                <div class="mb-4">
                                    <label class="form-label fw-semibold">Status</label>
                                    <select name="status" class="form-select">
                                        <option value="Aktif" <%= armadaEdit != null && "Aktif".equals(armadaEdit.getStatus()) ? "selected" : "" %>>Aktif</option>
                                        <option value="Maintenance" <%= armadaEdit != null && "Maintenance".equals(armadaEdit.getStatus()) ? "selected" : "" %>>Maintenance</option>
                                        <option value="Nonaktif" <%= armadaEdit != null && "Nonaktif".equals(armadaEdit.getStatus()) ? "selected" : "" %>>Nonaktif</option>
                                    </select>
                                </div>

                                <div class="d-grid gap-2">
                                    <button type="submit" class="btn btn-primary">
                                        <%= armadaEdit == null ? "Simpan Data" : "Update Data" %>
                                    </button>

                                    <% if (armadaEdit != null) { %>
                                        <a href="armada" class="btn btn-outline-secondary">Batal Edit</a>
                                    <% } %>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-xl-8">
                    <div class="search-box mb-3">
                        <form action="armada" method="get" class="row g-3 align-items-end">
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Cari Data</label>
                                <input type="text"
                                       name="keyword"
                                       class="form-control"
                                       placeholder="Cari kode, nama bus, plat, fasilitas, status..."
                                       value="<%= keyword != null ? keyword : "" %>">
                            </div>

                            <div class="col-md-4">
                                <label class="form-label fw-semibold">Urutkan</label>
                                <select name="sort" class="form-select">
                                    <option value="" <%= "".equals(sort) ? "selected" : "" %>>Terbaru</option>
                                    <option value="kode_asc" <%= "kode_asc".equals(sort) ? "selected" : "" %>>Kode A-Z</option>
                                    <option value="kode_desc" <%= "kode_desc".equals(sort) ? "selected" : "" %>>Kode Z-A</option>
                                    <option value="nama_asc" <%= "nama_asc".equals(sort) ? "selected" : "" %>>Nama A-Z</option>
                                    <option value="nama_desc" <%= "nama_desc".equals(sort) ? "selected" : "" %>>Nama Z-A</option>
                                    <option value="plat_asc" <%= "plat_asc".equals(sort) ? "selected" : "" %>>Plat A-Z</option>
                                    <option value="plat_desc" <%= "plat_desc".equals(sort) ? "selected" : "" %>>Plat Z-A</option>
                                    <option value="kapasitas_asc" <%= "kapasitas_asc".equals(sort) ? "selected" : "" %>>Kapasitas Terkecil</option>
                                    <option value="kapasitas_desc" <%= "kapasitas_desc".equals(sort) ? "selected" : "" %>>Kapasitas Terbesar</option>
                                </select>
                            </div>

                            <div class="col-md-2 d-grid">
                                <button type="submit" class="btn btn-primary">Tampil</button>
                            </div>
                        </form>
                    </div>

                    <div class="card card-modern">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0 fw-bold">Daftar Armada</h5>
                            <span class="badge badge-soft-primary">
                                <%= listArmada == null ? 0 : listArmada.size() %> Data
                            </span>
                        </div>

                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Kode</th>
                                            <th>Bus</th>
                                            <th>Plat</th>
                                            <th>Kapasitas</th>
                                            <th>Fasilitas</th>
                                            <th>Status</th>
                                            <th width="150">Aksi</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        if (listArmada != null && !listArmada.isEmpty()) {
                                            int no = 1;
                                            for (Armada a : listArmada) {
                                    %>
                                        <tr>
                                            <td><%= no++ %></td>
                                            <td>
                                                <strong><%= a.getKodeArmada() %></strong>
                                            </td>
                                            <td>
                                                <div class="fw-semibold"><%= a.getNamaBus() %></div>
                                                <small class="text-muted">Unit armada travel</small>
                                            </td>
                                            <td><%= a.getPlatNomor() %></td>
                                            <td><%= a.getKapasitas() %> kursi</td>
                                            <td>
                                                <small><%= a.getFasilitas() %></small>
                                            </td>
                                            <td>
                                                <% if ("Aktif".equals(a.getStatus())) { %>
                                                    <span class="badge badge-soft-success">Aktif</span>
                                                <% } else if ("Maintenance".equals(a.getStatus())) { %>
                                                    <span class="badge badge-soft-warning">Maintenance</span>
                                                <% } else { %>
                                                    <span class="badge badge-soft-danger">Nonaktif</span>
                                                <% } %>
                                            </td>
                                            <td>
                                                <div class="action-buttons">
                                                    <a href="armada?action=edit&id=<%= a.getIdArmada() %>"
                                                       class="btn btn-warning btn-sm">
                                                        Edit
                                                    </a>

                                                    <a href="armada?action=hapus&id=<%= a.getIdArmada() %>"
                                                       class="btn btn-danger btn-sm"
                                                       onclick="return confirm('Yakin ingin menghapus data armada ini?')">
                                                        Hapus
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                        <tr>
                                            <td colspan="8" class="text-center text-muted py-4">
                                                Data armada belum tersedia.
                                            </td>
                                        </tr>
                                    <%
                                        }
                                    %>
                                    </tbody>
                                </table>
                            </div>
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