<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.User"%>

<%
    String namaLengkap = (String) session.getAttribute("namaLengkap");
    String roleLogin = (String) session.getAttribute("role");

    List<User> listUser = (List<User>) request.getAttribute("listUser");
    User userEdit = (User) request.getAttribute("userEdit");

    String keyword = (String) request.getAttribute("keyword");
    String sort = (String) request.getAttribute("sort");

    String actionForm = userEdit == null ? "simpan" : "update";
%>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Data User - BusGo Travel</title>

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
                <div class="text-muted small"><%= roleLogin %></div>
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

                <% if ("Admin".equals(roleLogin)) { %>
                    <a href="user" class="menu-link active">Data User</a>
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
                <h2>Data User</h2>
                <p>Kelola akun pengguna sistem, hak akses Admin atau Operator, serta status akun login.</p>
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
                                <%= userEdit == null ? "Tambah User" : "Edit User" %>
                            </h5>
                        </div>

                        <div class="card-body">
                            <form action="user" method="post">
                                <input type="hidden" name="action" value="<%= actionForm %>">

                                <% if (userEdit != null) { %>
                                    <input type="hidden" name="idUser" value="<%= userEdit.getIdUser() %>">
                                <% } %>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Nama Lengkap</label>
                                    <input type="text"
                                           name="namaLengkap"
                                           class="form-control"
                                           placeholder="Contoh: Operator BusGo"
                                           value="<%= userEdit != null ? userEdit.getNamaLengkap() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Username</label>
                                    <input type="text"
                                           name="username"
                                           class="form-control"
                                           placeholder="Contoh: operator2"
                                           autocomplete="off"
                                           value="<%= userEdit != null ? userEdit.getUsername() : "" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">
                                        Password
                                    </label>

                                    <% if (userEdit != null) { %>
                                        <small class="text-muted d-block mb-2">
                                            Kosongkan jika tidak ingin mengganti password.
                                        </small>
                                    <% } %>

                                    <input type="password"
                                           name="password"
                                           class="form-control"
                                           placeholder="<%= userEdit == null ? "Minimal 6 karakter" : "Password baru jika ingin diganti" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Hak Akses</label>
                                    <select name="role" class="form-select">
                                        <option value="">-- Pilih Role --</option>
                                        <option value="Admin" <%= userEdit != null && "Admin".equals(userEdit.getRole()) ? "selected" : "" %>>Admin</option>
                                        <option value="Operator" <%= userEdit != null && "Operator".equals(userEdit.getRole()) ? "selected" : "" %>>Operator</option>
                                    </select>
                                </div>

                                <div class="mb-4">
                                    <label class="form-label fw-semibold">Status</label>
                                    <select name="status" class="form-select">
                                        <option value="">-- Pilih Status --</option>
                                        <option value="Aktif" <%= userEdit != null && "Aktif".equals(userEdit.getStatus()) ? "selected" : "" %>>Aktif</option>
                                        <option value="Nonaktif" <%= userEdit != null && "Nonaktif".equals(userEdit.getStatus()) ? "selected" : "" %>>Nonaktif</option>
                                    </select>
                                </div>

                                <div class="d-grid gap-2">
                                    <button type="submit" class="btn btn-primary">
                                        <%= userEdit == null ? "Simpan Data" : "Update Data" %>
                                    </button>

                                    <% if (userEdit != null) { %>
                                        <a href="user" class="btn btn-outline-secondary">Batal Edit</a>
                                    <% } %>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-xl-8">
                    <div class="search-box mb-3">
                        <form action="user" method="get" class="row g-3 align-items-end">
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Cari Data</label>
                                <input type="text"
                                       name="keyword"
                                       class="form-control"
                                       placeholder="Cari nama, username, role, status..."
                                       value="<%= keyword != null ? keyword : "" %>">
                            </div>

                            <div class="col-md-4">
                                <label class="form-label fw-semibold">Urutkan</label>
                                <select name="sort" class="form-select">
                                    <option value="" <%= "".equals(sort) ? "selected" : "" %>>Terbaru</option>
                                    <option value="nama_asc" <%= "nama_asc".equals(sort) ? "selected" : "" %>>Nama A-Z</option>
                                    <option value="nama_desc" <%= "nama_desc".equals(sort) ? "selected" : "" %>>Nama Z-A</option>
                                    <option value="username_asc" <%= "username_asc".equals(sort) ? "selected" : "" %>>Username A-Z</option>
                                    <option value="username_desc" <%= "username_desc".equals(sort) ? "selected" : "" %>>Username Z-A</option>
                                    <option value="role_asc" <%= "role_asc".equals(sort) ? "selected" : "" %>>Role A-Z</option>
                                    <option value="role_desc" <%= "role_desc".equals(sort) ? "selected" : "" %>>Role Z-A</option>
                                    <option value="status_asc" <%= "status_asc".equals(sort) ? "selected" : "" %>>Status A-Z</option>
                                    <option value="status_desc" <%= "status_desc".equals(sort) ? "selected" : "" %>>Status Z-A</option>
                                </select>
                            </div>

                            <div class="col-md-2 d-grid">
                                <button type="submit" class="btn btn-primary">Tampil</button>
                            </div>
                        </form>
                    </div>

                    <div class="card card-modern">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0 fw-bold">Daftar User</h5>
                            <span class="badge badge-soft-primary">
                                <%= listUser == null ? 0 : listUser.size() %> Data
                            </span>
                        </div>

                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Nama Lengkap</th>
                                            <th>Username</th>
                                            <th>Role</th>
                                            <th>Status</th>
                                            <th width="150">Aksi</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        if (listUser != null && !listUser.isEmpty()) {
                                            int no = 1;
                                            for (User u : listUser) {
                                    %>
                                        <tr>
                                            <td><%= no++ %></td>
                                            <td>
                                                <div class="fw-semibold"><%= u.getNamaLengkap() %></div>
                                                <small class="text-muted">Pengguna sistem</small>
                                            </td>
                                            <td>
                                                <strong><%= u.getUsername() %></strong>
                                            </td>
                                            <td>
                                                <% if ("Admin".equals(u.getRole())) { %>
                                                    <span class="badge badge-soft-primary">Admin</span>
                                                <% } else { %>
                                                    <span class="badge badge-soft-warning">Operator</span>
                                                <% } %>
                                            </td>
                                            <td>
                                                <% if ("Aktif".equals(u.getStatus())) { %>
                                                    <span class="badge badge-soft-success">Aktif</span>
                                                <% } else { %>
                                                    <span class="badge badge-soft-danger">Nonaktif</span>
                                                <% } %>
                                            </td>
                                            <td>
                                                <div class="action-buttons">
                                                    <a href="user?action=edit&id=<%= u.getIdUser() %>"
                                                       class="btn btn-warning btn-sm">
                                                        Edit
                                                    </a>

                                                    <a href="user?action=hapus&id=<%= u.getIdUser() %>"
                                                       class="btn btn-danger btn-sm"
                                                       onclick="return confirm('Yakin ingin menghapus user ini?')">
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
                                            <td colspan="6" class="text-center text-muted py-4">
                                                Data user belum tersedia.
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