# BusGo Travel

BusGo Travel adalah aplikasi web untuk mengelola operasional pemesanan tiket travel bus. Sistem ini menyediakan fitur pengelolaan data tujuan, armada, penumpang, user, transaksi tiket, perhitungan pembayaran otomatis, diskon, serta pencetakan laporan dalam format PDF.

Aplikasi ini dirancang untuk membantu proses administrasi dan transaksi travel bus agar lebih cepat, rapi, terpusat, dan mudah digunakan oleh Admin maupun Operator.

## Fitur Utama

* Login dengan hak akses Admin dan Operator
* Session login dan logout
* Password tersimpan dalam bentuk hash
* Dashboard dinamis
* CRUD data tujuan
* CRUD data armada
* CRUD data penumpang
* CRUD data user
* Transaksi pemesanan tiket
* Perhitungan harga tiket berdasarkan tujuan dan kelas tiket
* Diskon otomatis jika pembelian tiket lebih dari 3
* Perhitungan subtotal dan total bayar otomatis
* Search dan sort data
* Validasi input kosong
* Validasi angka
* Pencegahan data duplikat
* Laporan PDF menggunakan JasperReport
* Laporan data tujuan
* Laporan data armada
* Laporan data penumpang
* Laporan data user
* Laporan transaksi
* Laporan transaksi berdasarkan tanggal
* Laporan total pendapatan

## Hak Akses

### Admin

Admin dapat mengakses seluruh fitur sistem, termasuk:

* Dashboard
* Data tujuan
* Data armada
* Data penumpang
* Data user
* Transaksi tiket
* Laporan
* Logout dan Exit

### Operator

Operator dapat mengakses fitur operasional, yaitu:

* Dashboard
* Data tujuan
* Data armada
* Data penumpang
* Transaksi tiket
* Laporan transaksi dan pendapatan
* Logout dan Exit

Operator tidak memiliki akses ke data user.

## Teknologi yang Digunakan

* Java
* Java Servlet
* JSP
* JDBC
* MySQL
* JasperReport
* Maven
* Apache Tomcat
* Bootstrap
* CSS

## Struktur Database

Database yang digunakan adalah `db_busgo_travel`.

Tabel utama:

* `users`
* `tujuan`
* `armada`
* `penumpang`
* `transaksi`
* `log_aktivitas`

## Perhitungan Transaksi

Sistem menghitung transaksi tiket dengan rumus:

```text
Subtotal = Harga Tiket x Jumlah Tiket
Diskon = 10% dari subtotal jika jumlah tiket lebih dari 3
Total Bayar = Subtotal - Diskon
```

Harga tiket ditentukan berdasarkan tujuan dan kelas tiket:

* Ekonomi
* Bisnis
* Eksekutif

## Cara Menjalankan Aplikasi

1. Clone repository ini.
2. Import project ke Apache NetBeans.
3. Pastikan Apache Tomcat sudah terpasang.
4. Import database `db_busgo_travel.sql` ke MySQL/phpMyAdmin.
5. Sesuaikan konfigurasi koneksi database pada file `Koneksi.java`.
6. Jalankan project menggunakan Tomcat.
7. Buka aplikasi melalui browser.

Contoh URL:

```text
http://localhost:8080/BusGoTravel
```

## Akun Login Default

### Admin

```text
Username: admin
Password: admin123
```

### Operator

```text
Username: operator
Password: operator123
```

## Laporan

Sistem menyediakan laporan dalam format PDF, yaitu:

* Laporan data tujuan
* Laporan data armada
* Laporan data penumpang
* Laporan data user
* Laporan transaksi
* Laporan transaksi berdasarkan tanggal
* Laporan total pendapatan

## Status Project

Project ini sudah mencakup fitur utama sistem operasional travel bus, mulai dari pengelolaan data, transaksi, perhitungan pembayaran, hak akses pengguna, sampai laporan PDF.
