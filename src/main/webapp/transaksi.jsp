<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Transaksi"%>
<%@page import="model.Tujuan"%>
<%@page import="model.Armada"%>
<%@page import="model.Penumpang"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>

<%
    String namaLengkap = (String) session.getAttribute("namaLengkap");
    String role = (String) session.getAttribute("role");

    List<Transaksi> listTransaksi = (List<Transaksi>) request.getAttribute("listTransaksi");
    List<Tujuan> listTujuan = (List<Tujuan>) request.getAttribute("listTujuan");
    List<Armada> listArmada = (List<Armada>) request.getAttribute("listArmada");
    List<Penumpang> listPenumpang = (List<Penumpang>) request.getAttribute("listPenumpang");

    String kodeTransaksi = (String) request.getAttribute("kodeTransaksi");
    String tanggalHariIni = (String) request.getAttribute("tanggalHariIni");

    String keyword = (String) request.getAttribute("keyword");
    String sort = (String) request.getAttribute("sort");

    NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    boolean masterLengkap = listTujuan != null && !listTujuan.isEmpty()
            && listArmada != null && !listArmada.isEmpty()
            && listPenumpang != null && !listPenumpang.isEmpty();
%>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Transaksi Tiket - BusGo Travel</title>

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
                <a href="transaksi" class="menu-link active">Transaksi Tiket</a>

                <div class="menu-title">Laporan</div>
                <a href="laporan" class="menu-link">Cetak Laporan</a>

                <div class="menu-title">Sistem</div>
                <a href="logout" class="menu-link text-danger">Logout</a>
                <a href="#" class="menu-link text-danger" onclick="keluarAplikasi()">Exit</a>
            </div>
        </div>

        <div class="col-lg-10 content-area">

            <div class="page-header">
                <h2>Transaksi Tiket</h2>
                <p>Kelola pemesanan tiket bus, hitung harga berdasarkan tujuan dan kelas tiket, serta diskon otomatis.</p>
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

            <% if (!masterLengkap) { %>
                <div class="alert alert-warning">
                    Transaksi belum bisa dilakukan. Pastikan data tujuan aktif, armada aktif, dan penumpang sudah tersedia.
                </div>
            <% } %>

            <div class="row g-4">

                <div class="col-xl-4">
                    <div class="card card-modern">
                        <div class="card-header">
                            <h5 class="mb-0 fw-bold">Input Transaksi</h5>
                        </div>

                        <div class="card-body">
                            <form action="transaksi" method="post">

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Kode Transaksi</label>
                                    <input type="text"
                                           name="kodeTransaksi"
                                           class="form-control"
                                           value="<%= kodeTransaksi %>"
                                           readonly>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Tanggal Transaksi</label>
                                    <input type="date"
                                           name="tanggalTransaksi"
                                           class="form-control"
                                           value="<%= tanggalHariIni %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Tujuan</label>
                                    <select name="idTujuan" id="idTujuan" class="form-select" onchange="hitungTotal()">
                                        <option value="">-- Pilih Tujuan --</option>
                                        <%
                                            if (listTujuan != null) {
                                                for (Tujuan t : listTujuan) {
                                        %>
                                            <option value="<%= t.getIdTujuan() %>"
                                                    data-ekonomi="<%= t.getHargaEkonomi() %>"
                                                    data-bisnis="<%= t.getHargaBisnis() %>"
                                                    data-eksekutif="<%= t.getHargaEksekutif() %>">
                                                <%= t.getKotaAsal() %> → <%= t.getKotaTujuan() %>
                                            </option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Armada</label>
                                    <select name="idArmada" class="form-select">
                                        <option value="">-- Pilih Armada --</option>
                                        <%
                                            if (listArmada != null) {
                                                for (Armada a : listArmada) {
                                        %>
                                            <option value="<%= a.getIdArmada() %>">
                                                <%= a.getNamaBus() %> - <%= a.getPlatNomor() %> (<%= a.getKapasitas() %> kursi)
                                            </option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Penumpang</label>
                                    <select name="idPenumpang" class="form-select">
                                        <option value="">-- Pilih Penumpang --</option>
                                        <%
                                            if (listPenumpang != null) {
                                                for (Penumpang p : listPenumpang) {
                                        %>
                                            <option value="<%= p.getIdPenumpang() %>">
                                                <%= p.getNamaPenumpang() %> - <%= p.getNoHp() %>
                                            </option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Kelas Tiket</label>
                                    <select name="kelasTiket" id="kelasTiket" class="form-select" onchange="hitungTotal()">
                                        <option value="">-- Pilih Kelas --</option>
                                        <option value="Ekonomi">Ekonomi</option>
                                        <option value="Bisnis">Bisnis</option>
                                        <option value="Eksekutif">Eksekutif</option>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Jumlah Tiket</label>
                                    <input type="number"
                                           name="jumlahTiket"
                                           id="jumlahTiket"
                                           class="form-control"
                                           placeholder="Contoh: 4"
                                           min="1"
                                           oninput="hitungTotal()">
                                </div>

                                <div class="login-info mb-3">
                                    <div class="d-flex justify-content-between mb-2">
                                        <span>Harga Tiket</span>
                                        <strong id="hargaTiketText">Rp 0</strong>
                                    </div>
                                    <div class="d-flex justify-content-between mb-2">
                                        <span>Subtotal</span>
                                        <strong id="subtotalText">Rp 0</strong>
                                    </div>
                                    <div class="d-flex justify-content-between mb-2">
                                        <span>Diskon</span>
                                        <strong id="diskonText">Rp 0</strong>
                                    </div>
                                    <hr>
                                    <div class="d-flex justify-content-between">
                                        <span>Total Bayar</span>
                                        <strong id="totalText">Rp 0</strong>
                                    </div>
                                </div>

                                <div class="d-grid">
                                    <button type="submit"
                                            class="btn btn-primary"
                                            <%= !masterLengkap ? "disabled" : "" %>>
                                        Simpan Transaksi
                                    </button>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-xl-8">
                    <div class="search-box mb-3">
                        <form action="transaksi" method="get" class="row g-3 align-items-end">
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Cari Transaksi</label>
                                <input type="text"
                                       name="keyword"
                                       class="form-control"
                                       placeholder="Cari kode, tanggal, rute, penumpang..."
                                       value="<%= keyword != null ? keyword : "" %>">
                            </div>

                            <div class="col-md-4">
                                <label class="form-label fw-semibold">Urutkan</label>
                                <select name="sort" class="form-select">
                                    <option value="" <%= "".equals(sort) ? "selected" : "" %>>Terbaru</option>
                                    <option value="kode_asc" <%= "kode_asc".equals(sort) ? "selected" : "" %>>Kode A-Z</option>
                                    <option value="kode_desc" <%= "kode_desc".equals(sort) ? "selected" : "" %>>Kode Z-A</option>
                                    <option value="tanggal_asc" <%= "tanggal_asc".equals(sort) ? "selected" : "" %>>Tanggal Terlama</option>
                                    <option value="tanggal_desc" <%= "tanggal_desc".equals(sort) ? "selected" : "" %>>Tanggal Terbaru</option>
                                    <option value="total_asc" <%= "total_asc".equals(sort) ? "selected" : "" %>>Total Terendah</option>
                                    <option value="total_desc" <%= "total_desc".equals(sort) ? "selected" : "" %>>Total Tertinggi</option>
                                </select>
                            </div>

                            <div class="col-md-2 d-grid">
                                <button type="submit" class="btn btn-primary">Tampil</button>
                            </div>
                        </form>
                    </div>

                    <div class="card card-modern">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0 fw-bold">Daftar Transaksi</h5>
                            <span class="badge badge-soft-primary">
                                <%= listTransaksi == null ? 0 : listTransaksi.size() %> Data
                            </span>
                        </div>

                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Kode</th>
                                            <th>Tanggal</th>
                                            <th>Penumpang</th>
                                            <th>Rute</th>
                                            <th>Kelas</th>
                                            <th>Qty</th>
                                            <th>Total</th>
                                            <th width="90">Aksi</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        if (listTransaksi != null && !listTransaksi.isEmpty()) {
                                            int no = 1;
                                            for (Transaksi t : listTransaksi) {
                                    %>
                                        <tr>
                                            <td><%= no++ %></td>
                                            <td>
                                                <strong><%= t.getKodeTransaksi() %></strong>
                                                <br>
                                                <small class="text-muted"><%= t.getUsername() %></small>
                                            </td>
                                            <td><%= t.getTanggalTransaksi() %></td>
                                            <td>
                                                <div class="fw-semibold"><%= t.getNamaPenumpang() %></div>
                                                <small class="text-muted"><%= t.getNamaBus() %></small>
                                            </td>
                                            <td>
                                                <%= t.getKotaAsal() %> → <%= t.getKotaTujuan() %>
                                            </td>
                                            <td>
                                                <span class="badge badge-soft-primary"><%= t.getKelasTiket() %></span>
                                            </td>
                                            <td><%= t.getJumlahTiket() %></td>
                                            <td>
                                                <strong><%= rupiah.format(t.getTotalBayar()) %></strong>
                                                <% if (t.getDiskon() > 0) { %>
                                                    <br>
                                                    <small class="text-success">
                                                        Diskon <%= rupiah.format(t.getDiskon()) %>
                                                    </small>
                                                <% } %>
                                            </td>
                                            <td>
                                                <a href="transaksi?action=hapus&id=<%= t.getIdTransaksi() %>"
                                                   class="btn btn-danger btn-sm"
                                                   onclick="return confirm('Yakin ingin menghapus transaksi ini?')">
                                                    Hapus
                                                </a>
                                            </td>
                                        </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                        <tr>
                                            <td colspan="9" class="text-center text-muted py-4">
                                                Data transaksi belum tersedia.
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
function formatRupiah(angka) {
    return new Intl.NumberFormat('id-ID', {
        style: 'currency',
        currency: 'IDR',
        minimumFractionDigits: 0
    }).format(angka);
}

function hitungTotal() {
    const tujuan = document.getElementById('idTujuan');
    const kelas = document.getElementById('kelasTiket').value;
    const jumlah = parseInt(document.getElementById('jumlahTiket').value) || 0;

    let harga = 0;

    if (tujuan.value !== '' && kelas !== '') {
        const selected = tujuan.options[tujuan.selectedIndex];

        if (kelas === 'Ekonomi') {
            harga = parseFloat(selected.getAttribute('data-ekonomi')) || 0;
        } else if (kelas === 'Bisnis') {
            harga = parseFloat(selected.getAttribute('data-bisnis')) || 0;
        } else if (kelas === 'Eksekutif') {
            harga = parseFloat(selected.getAttribute('data-eksekutif')) || 0;
        }
    }

    const subtotal = harga * jumlah;
    let diskon = 0;

    if (jumlah > 3) {
        diskon = subtotal * 0.10;
    }

    const total = subtotal - diskon;

    document.getElementById('hargaTiketText').innerText = formatRupiah(harga);
    document.getElementById('subtotalText').innerText = formatRupiah(subtotal);
    document.getElementById('diskonText').innerText = formatRupiah(diskon);
    document.getElementById('totalText').innerText = formatRupiah(total);
}
function keluarAplikasi() {
    const konfirmasi = confirm("Apakah Anda yakin ingin keluar dari aplikasi?");
    if (konfirmasi) {
        window.location.href = "logout";
    }
}
</script>

</body>
</html>