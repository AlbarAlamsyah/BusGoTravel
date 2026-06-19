<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Tujuan"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>

<%
    String namaLengkap = (String) session.getAttribute("namaLengkap");
    String role = (String) session.getAttribute("role");

    List<Tujuan> listTujuan = (List<Tujuan>) request.getAttribute("listTujuan");
    Tujuan tujuanEdit = (Tujuan) request.getAttribute("tujuanEdit");

    String keyword = (String) request.getAttribute("keyword");
    String sort = (String) request.getAttribute("sort");

    NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    String actionForm = tujuanEdit == null ? "simpan" : "update";
%>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Data Tujuan - BusGo Travel</title>

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
                <a href="tujuan" class="menu-link active">Data Tujuan</a>
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
                <h2>Data Tujuan</h2>
                <p>Kelola rute perjalanan, kota asal, kota tujuan, kelas tiket, dan harga perjalanan bus.</p>
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
                                <%= tujuanEdit == null ? "Tambah Tujuan" : "Edit Tujuan" %>
                            </h5>
                        </div>

                        <div class="card-body">
                            <form action="tujuan" method="post">
                                <input type="hidden" name="action" value="<%= actionForm %>">

                                <% if (tujuanEdit != null) { %>
                                    <input type="hidden" name="idTujuan" value="<%= tujuanEdit.getIdTujuan() %>">
                                <% } %>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Kode Tujuan</label>
                                    <input type="text"
                                           name="kodeTujuan"
                                           class="form-control"
                                           placeholder="Contoh: TJN004"
                                           value="<%= tujuanEdit != null ? tujuanEdit.getKodeTujuan() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Kota Asal</label>
                                    <input type="text"
                                           name="kotaAsal"
                                           class="form-control"
                                           placeholder="Contoh: Jakarta"
                                           value="<%= tujuanEdit != null ? tujuanEdit.getKotaAsal() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Kota Tujuan</label>
                                    <input type="text"
                                           name="kotaTujuan"
                                           class="form-control"
                                           placeholder="Contoh: Bandung"
                                           value="<%= tujuanEdit != null ? tujuanEdit.getKotaTujuan() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Harga Ekonomi</label>
                                    <input type="number"
                                           name="hargaEkonomi"
                                           class="form-control"
                                           placeholder="Contoh: 90000"
                                           value="<%= tujuanEdit != null ? tujuanEdit.getHargaEkonomi() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Harga Bisnis</label>
                                    <input type="number"
                                           name="hargaBisnis"
                                           class="form-control"
                                           placeholder="Contoh: 130000"
                                           value="<%= tujuanEdit != null ? tujuanEdit.getHargaBisnis() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Harga Eksekutif</label>
                                    <input type="number"
                                           name="hargaEksekutif"
                                           class="form-control"
                                           placeholder="Contoh: 180000"
                                           value="<%= tujuanEdit != null ? tujuanEdit.getHargaEksekutif() : "" %>">
                                </div>

                                <div class="mb-4">
                                    <label class="form-label fw-semibold">Status</label>
                                    <select name="status" class="form-select">
                                        <option value="Aktif" <%= tujuanEdit != null && "Aktif".equals(tujuanEdit.getStatus()) ? "selected" : "" %>>Aktif</option>
                                        <option value="Nonaktif" <%= tujuanEdit != null && "Nonaktif".equals(tujuanEdit.getStatus()) ? "selected" : "" %>>Nonaktif</option>
                                    </select>
                                </div>

                                <div class="d-grid gap-2">
                                    <button type="submit" class="btn btn-primary">
                                        <%= tujuanEdit == null ? "Simpan Data" : "Update Data" %>
                                    </button>

                                    <% if (tujuanEdit != null) { %>
                                        <a href="tujuan" class="btn btn-outline-secondary">Batal Edit</a>
                                    <% } %>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-xl-8">
                    <div class="search-box mb-3">
                        <form action="tujuan" method="get" class="row g-3 align-items-end">
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Cari Data</label>
                                <input type="text"
                                       name="keyword"
                                       class="form-control"
                                       placeholder="Cari kode, asal, tujuan, status..."
                                       value="<%= keyword != null ? keyword : "" %>">
                            </div>

                            <div class="col-md-4">
                                <label class="form-label fw-semibold">Urutkan</label>
                                <select name="sort" class="form-select">
                                    <option value="" <%= "".equals(sort) ? "selected" : "" %>>Terbaru</option>
                                    <option value="kode_asc" <%= "kode_asc".equals(sort) ? "selected" : "" %>>Kode A-Z</option>
                                    <option value="kode_desc" <%= "kode_desc".equals(sort) ? "selected" : "" %>>Kode Z-A</option>
                                    <option value="asal_asc" <%= "asal_asc".equals(sort) ? "selected" : "" %>>Asal A-Z</option>
                                    <option value="asal_desc" <%= "asal_desc".equals(sort) ? "selected" : "" %>>Asal Z-A</option>
                                    <option value="tujuan_asc" <%= "tujuan_asc".equals(sort) ? "selected" : "" %>>Tujuan A-Z</option>
                                    <option value="tujuan_desc" <%= "tujuan_desc".equals(sort) ? "selected" : "" %>>Tujuan Z-A</option>
                                    <option value="harga_asc" <%= "harga_asc".equals(sort) ? "selected" : "" %>>Harga Termurah</option>
                                    <option value="harga_desc" <%= "harga_desc".equals(sort) ? "selected" : "" %>>Harga Termahal</option>
                                </select>
                            </div>

                            <div class="col-md-2 d-grid">
                                <button type="submit" class="btn btn-primary">Tampil</button>
                            </div>
                        </form>
                    </div>

                    <div class="card card-modern">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0 fw-bold">Daftar Tujuan</h5>
                            <span class="badge badge-soft-primary">
                                <%= listTujuan == null ? 0 : listTujuan.size() %> Data
                            </span>
                        </div>

                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Kode</th>
                                            <th>Rute</th>
                                            <th>Ekonomi</th>
                                            <th>Bisnis</th>
                                            <th>Eksekutif</th>
                                            <th>Status</th>
                                            <th width="150">Aksi</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        if (listTujuan != null && !listTujuan.isEmpty()) {
                                            int no = 1;
                                            for (Tujuan t : listTujuan) {
                                    %>
                                        <tr>
                                            <td><%= no++ %></td>
                                            <td>
                                                <strong><%= t.getKodeTujuan() %></strong>
                                            </td>
                                            <td>
                                                <div class="fw-semibold"><%= t.getKotaAsal() %> → <%= t.getKotaTujuan() %></div>
                                                <small class="text-muted">Rute perjalanan bus</small>
                                            </td>
                                            <td><%= rupiah.format(t.getHargaEkonomi()) %></td>
                                            <td><%= rupiah.format(t.getHargaBisnis()) %></td>
                                            <td><%= rupiah.format(t.getHargaEksekutif()) %></td>
                                            <td>
                                                <% if ("Aktif".equals(t.getStatus())) { %>
                                                    <span class="badge badge-soft-success">Aktif</span>
                                                <% } else { %>
                                                    <span class="badge badge-soft-danger">Nonaktif</span>
                                                <% } %>
                                            </td>
                                            <td>
                                                <div class="action-buttons">
                                                    <a href="tujuan?action=edit&id=<%= t.getIdTujuan() %>"
                                                       class="btn btn-warning btn-sm">
                                                        Edit
                                                    </a>

                                                    <a href="tujuan?action=hapus&id=<%= t.getIdTujuan() %>"
                                                       class="btn btn-danger btn-sm"
                                                       onclick="return confirm('Yakin ingin menghapus data tujuan ini?')">
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
                                                Data tujuan belum tersedia.
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