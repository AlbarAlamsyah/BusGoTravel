<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Penumpang"%>

<%
    String namaLengkap = (String) session.getAttribute("namaLengkap");
    String role = (String) session.getAttribute("role");

    List<Penumpang> listPenumpang = (List<Penumpang>) request.getAttribute("listPenumpang");
    Penumpang penumpangEdit = (Penumpang) request.getAttribute("penumpangEdit");

    String keyword = (String) request.getAttribute("keyword");
    String sort = (String) request.getAttribute("sort");

    String actionForm = penumpangEdit == null ? "simpan" : "update";
%>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Data Penumpang - BusGo Travel</title>

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
                <a href="penumpang" class="menu-link active">Data Penumpang</a>

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
                <h2>Data Penumpang</h2>
                <p>Kelola data identitas penumpang yang digunakan pada transaksi pemesanan tiket travel bus.</p>
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
                                <%= penumpangEdit == null ? "Tambah Penumpang" : "Edit Penumpang" %>
                            </h5>
                        </div>

                        <div class="card-body">
                            <form action="penumpang" method="post">
                                <input type="hidden" name="action" value="<%= actionForm %>">

                                <% if (penumpangEdit != null) { %>
                                    <input type="hidden" name="idPenumpang" value="<%= penumpangEdit.getIdPenumpang() %>">
                                <% } %>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">NIK</label>
                                    <input type="text"
                                           name="nik"
                                           class="form-control"
                                           placeholder="Contoh: 3175010101010001"
                                           value="<%= penumpangEdit != null ? penumpangEdit.getNik() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Nama Penumpang</label>
                                    <input type="text"
                                           name="namaPenumpang"
                                           class="form-control"
                                           placeholder="Contoh: Muhamad Albar"
                                           value="<%= penumpangEdit != null ? penumpangEdit.getNamaPenumpang() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Nomor HP</label>
                                    <input type="text"
                                           name="noHp"
                                           class="form-control"
                                           placeholder="Contoh: 081234567890"
                                           value="<%= penumpangEdit != null ? penumpangEdit.getNoHp() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Alamat</label>
                                    <textarea name="alamat"
                                              class="form-control"
                                              rows="3"
                                              placeholder="Masukkan alamat penumpang"><%= penumpangEdit != null ? penumpangEdit.getAlamat() : "" %></textarea>
                                </div>

                                <div class="mb-4">
                                    <label class="form-label fw-semibold">Jenis Kelamin</label>
                                    <select name="jenisKelamin" class="form-select">
                                        <option value="">-- Pilih Jenis Kelamin --</option>
                                        <option value="Laki-laki" <%= penumpangEdit != null && "Laki-laki".equals(penumpangEdit.getJenisKelamin()) ? "selected" : "" %>>Laki-laki</option>
                                        <option value="Perempuan" <%= penumpangEdit != null && "Perempuan".equals(penumpangEdit.getJenisKelamin()) ? "selected" : "" %>>Perempuan</option>
                                    </select>
                                </div>

                                <div class="d-grid gap-2">
                                    <button type="submit" class="btn btn-primary">
                                        <%= penumpangEdit == null ? "Simpan Data" : "Update Data" %>
                                    </button>

                                    <% if (penumpangEdit != null) { %>
                                        <a href="penumpang" class="btn btn-outline-secondary">Batal Edit</a>
                                    <% } %>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-xl-8">
                    <div class="search-box mb-3">
                        <form action="penumpang" method="get" class="row g-3 align-items-end">
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Cari Data</label>
                                <input type="text"
                                       name="keyword"
                                       class="form-control"
                                       placeholder="Cari NIK, nama, nomor HP, alamat..."
                                       value="<%= keyword != null ? keyword : "" %>">
                            </div>

                            <div class="col-md-4">
                                <label class="form-label fw-semibold">Urutkan</label>
                                <select name="sort" class="form-select">
                                    <option value="" <%= "".equals(sort) ? "selected" : "" %>>Terbaru</option>
                                    <option value="nik_asc" <%= "nik_asc".equals(sort) ? "selected" : "" %>>NIK A-Z</option>
                                    <option value="nik_desc" <%= "nik_desc".equals(sort) ? "selected" : "" %>>NIK Z-A</option>
                                    <option value="nama_asc" <%= "nama_asc".equals(sort) ? "selected" : "" %>>Nama A-Z</option>
                                    <option value="nama_desc" <%= "nama_desc".equals(sort) ? "selected" : "" %>>Nama Z-A</option>
                                    <option value="jk_asc" <%= "jk_asc".equals(sort) ? "selected" : "" %>>Jenis Kelamin A-Z</option>
                                    <option value="jk_desc" <%= "jk_desc".equals(sort) ? "selected" : "" %>>Jenis Kelamin Z-A</option>
                                </select>
                            </div>

                            <div class="col-md-2 d-grid">
                                <button type="submit" class="btn btn-primary">Tampil</button>
                            </div>
                        </form>
                    </div>

                    <div class="card card-modern">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0 fw-bold">Daftar Penumpang</h5>
                            <span class="badge badge-soft-primary">
                                <%= listPenumpang == null ? 0 : listPenumpang.size() %> Data
                            </span>
                        </div>

                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>NIK</th>
                                            <th>Nama</th>
                                            <th>No HP</th>
                                            <th>Alamat</th>
                                            <th>Jenis Kelamin</th>
                                            <th width="150">Aksi</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        if (listPenumpang != null && !listPenumpang.isEmpty()) {
                                            int no = 1;
                                            for (Penumpang p : listPenumpang) {
                                    %>
                                        <tr>
                                            <td><%= no++ %></td>
                                            <td>
                                                <strong><%= p.getNik() %></strong>
                                            </td>
                                            <td>
                                                <div class="fw-semibold"><%= p.getNamaPenumpang() %></div>
                                                <small class="text-muted">Data customer travel</small>
                                            </td>
                                            <td><%= p.getNoHp() %></td>
                                            <td>
                                                <small><%= p.getAlamat() %></small>
                                            </td>
                                            <td>
                                                <% if ("Laki-laki".equals(p.getJenisKelamin())) { %>
                                                    <span class="badge badge-soft-primary">Laki-laki</span>
                                                <% } else { %>
                                                    <span class="badge badge-soft-warning">Perempuan</span>
                                                <% } %>
                                            </td>
                                            <td>
                                                <div class="action-buttons">
                                                    <a href="penumpang?action=edit&id=<%= p.getIdPenumpang() %>"
                                                       class="btn btn-warning btn-sm">
                                                        Edit
                                                    </a>

                                                    <a href="penumpang?action=hapus&id=<%= p.getIdPenumpang() %>"
                                                       class="btn btn-danger btn-sm"
                                                       onclick="return confirm('Yakin ingin menghapus data penumpang ini?')">
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
                                            <td colspan="7" class="text-center text-muted py-4">
                                                Data penumpang belum tersedia.
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