<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - BusGo Travel</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/css/style.css" rel="stylesheet">
</head>
<body class="login-body">

<div class="container">
    <div class="row min-vh-100 align-items-center justify-content-center">
        <div class="col-lg-9 col-xl-8">
            <div class="card login-card shadow-lg border-0">
                <div class="row g-0">
                    <div class="col-md-6 text-white p-5 d-flex flex-column justify-content-between login-left-panel">
                        <div>
                            <div class="brand-circle mb-4">BG</div>
                            <h1 class="fw-bold mb-3">BusGo Travel</h1>
                            <p class="fs-5 mb-4">
                                Solusi operasional travel bus untuk pemesanan tiket, pengelolaan armada, data pelanggan, dan laporan pendapatan.
                            </p>
                            <div class="feature-list">
                                <div>✓ Pemesanan tiket lebih cepat</div>
                                <div>✓ Data operasional terpusat</div>
                                <div>✓ Laporan siap cetak</div>
                            </div>
                        </div>
                        <div class="mt-4">
                            <span class="badge bg-light text-dark me-2">Booking</span>
                            <span class="badge bg-light text-dark me-2">Travel</span>
                            <span class="badge bg-light text-dark">Report</span>
                        </div>
                    </div>

                    <div class="col-md-6 p-5 bg-white">
                        <div class="mb-4">
                            <h3 class="fw-bold mb-1">Masuk Akun</h3>
                            <p class="text-muted mb-0">Kelola layanan perjalanan dengan aman.</p>
                        </div>

                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
                        <% } %>

                        <% if (request.getAttribute("success") != null) { %>
                            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
                        <% } %>

                        <form action="<%= request.getContextPath() %>/login" method="post">
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Username</label>
                                <input type="text" name="username" class="form-control form-control-lg" placeholder="Masukkan username" autocomplete="off">
                            </div>
                            <div class="mb-4">
                                <label class="form-label fw-semibold">Password</label>
                                <input type="password" name="password" class="form-control form-control-lg" placeholder="Masukkan password">
                            </div>
                            <button type="submit" class="btn btn-primary btn-lg w-100 login-btn">Login</button>
                        </form>

                        <div class="mt-4 login-info">
                            <div><strong>Admin:</strong> admin / admin123</div>
                            <div><strong>Operator:</strong> operator / operator123</div>
                        </div>
                    </div>
                </div>
            </div>
            <p class="text-center text-muted mt-4 small">© 2026 BusGo Travel. All rights reserved.</p>
        </div>
    </div>
</div>

</body>
</html>
