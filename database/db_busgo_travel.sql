-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 19, 2026 at 06:23 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_busgo_travel`
--

-- --------------------------------------------------------

--
-- Table structure for table `armada`
--

CREATE TABLE `armada` (
  `id_armada` int(11) NOT NULL,
  `kode_armada` varchar(20) NOT NULL,
  `nama_bus` varchar(100) NOT NULL,
  `plat_nomor` varchar(20) NOT NULL,
  `kapasitas` int(11) NOT NULL,
  `fasilitas` text DEFAULT NULL,
  `status` enum('Aktif','Maintenance','Nonaktif') DEFAULT 'Aktif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `armada`
--

INSERT INTO `armada` (`id_armada`, `kode_armada`, `nama_bus`, `plat_nomor`, `kapasitas`, `fasilitas`, `status`) VALUES
(1, 'BUS001', 'BusGo Executive 01', 'B 7001 BG', 40, 'AC, Reclining Seat, USB Charger, WiFi', 'Aktif'),
(2, 'BUS002', 'BusGo Premium 02', 'B 7002 BG', 45, 'AC, Reclining Seat, TV', 'Aktif'),
(3, 'BUS003', 'BusGo Economy 03', 'B 7003 BG', 50, 'AC, Standard Seat', 'Aktif');

-- --------------------------------------------------------

--
-- Table structure for table `log_aktivitas`
--

CREATE TABLE `log_aktivitas` (
  `id_log` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `aktivitas` varchar(255) DEFAULT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `penumpang`
--

CREATE TABLE `penumpang` (
  `id_penumpang` int(11) NOT NULL,
  `nik` varchar(20) NOT NULL,
  `nama_penumpang` varchar(100) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `alamat` text DEFAULT NULL,
  `jenis_kelamin` enum('Laki-laki','Perempuan') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `penumpang`
--

INSERT INTO `penumpang` (`id_penumpang`, `nik`, `nama_penumpang`, `no_hp`, `alamat`, `jenis_kelamin`) VALUES
(1, '3175010101010001', 'Muhamad Albar', '081234567890', 'Jakarta', 'Laki-laki'),
(2, '3175010101010002', 'Aisyah Putri', '081298765432', 'Serang', 'Perempuan'),
(3, '3173402873640001', 'Fredy Ferari', '089786754321', 'Cengkareng', 'Laki-laki');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `kode_transaksi` varchar(30) NOT NULL,
  `tanggal_transaksi` date NOT NULL,
  `id_tujuan` int(11) NOT NULL,
  `id_armada` int(11) NOT NULL,
  `id_penumpang` int(11) NOT NULL,
  `kelas_tiket` enum('Ekonomi','Bisnis','Eksekutif') NOT NULL,
  `harga_tiket` decimal(12,2) NOT NULL,
  `jumlah_tiket` int(11) NOT NULL,
  `subtotal` decimal(12,2) NOT NULL,
  `diskon` decimal(12,2) NOT NULL,
  `total_bayar` decimal(12,2) NOT NULL,
  `id_user` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `kode_transaksi`, `tanggal_transaksi`, `id_tujuan`, `id_armada`, `id_penumpang`, `kelas_tiket`, `harga_tiket`, `jumlah_tiket`, `subtotal`, `diskon`, `total_bayar`, `id_user`, `created_at`) VALUES
(1, 'TRX001', '2026-06-17', 2, 1, 1, 'Eksekutif', 350000.00, 1, 350000.00, 0.00, 350000.00, 1, '2026-06-16 20:32:34');

-- --------------------------------------------------------

--
-- Table structure for table `tujuan`
--

CREATE TABLE `tujuan` (
  `id_tujuan` int(11) NOT NULL,
  `kode_tujuan` varchar(20) NOT NULL,
  `kota_asal` varchar(100) NOT NULL,
  `kota_tujuan` varchar(100) NOT NULL,
  `harga_ekonomi` decimal(12,2) NOT NULL,
  `harga_bisnis` decimal(12,2) NOT NULL,
  `harga_eksekutif` decimal(12,2) NOT NULL,
  `status` enum('Aktif','Nonaktif') DEFAULT 'Aktif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tujuan`
--

INSERT INTO `tujuan` (`id_tujuan`, `kode_tujuan`, `kota_asal`, `kota_tujuan`, `harga_ekonomi`, `harga_bisnis`, `harga_eksekutif`, `status`) VALUES
(1, 'TJN001', 'Jakarta', 'Bandung', 90000.00, 130000.00, 180000.00, 'Aktif'),
(2, 'TJN002', 'Jakarta', 'Yogyakarta', 180000.00, 250000.00, 350000.00, 'Aktif'),
(3, 'TJN003', 'Jakarta', 'Surabaya', 250000.00, 350000.00, 500000.00, 'Aktif');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Admin','Operator') NOT NULL,
  `status` enum('Aktif','Nonaktif') DEFAULT 'Aktif',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `nama_lengkap`, `username`, `password`, `role`, `status`, `created_at`) VALUES
(1, 'Administrator BusGo', 'admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Admin', 'Aktif', '2026-06-13 02:13:16'),
(2, 'Operator BusGo', 'operator', 'ec6e1c25258002eb1c67d15c7f45da7945fa4c58778fd7d88faa5e53e3b4698d', 'Operator', 'Aktif', '2026-06-13 02:13:16'),
(3, 'Muhamad Albar Alamsyah', 'albar', 'f3e5ae75994ada5688693d05257237f2501f01393f44def2d6f43a54cf1118fb', 'Operator', 'Aktif', '2026-06-13 08:01:33');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `armada`
--
ALTER TABLE `armada`
  ADD PRIMARY KEY (`id_armada`),
  ADD UNIQUE KEY `kode_armada` (`kode_armada`),
  ADD UNIQUE KEY `plat_nomor` (`plat_nomor`);

--
-- Indexes for table `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  ADD PRIMARY KEY (`id_log`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `penumpang`
--
ALTER TABLE `penumpang`
  ADD PRIMARY KEY (`id_penumpang`),
  ADD UNIQUE KEY `nik` (`nik`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD UNIQUE KEY `kode_transaksi` (`kode_transaksi`),
  ADD KEY `id_tujuan` (`id_tujuan`),
  ADD KEY `id_armada` (`id_armada`),
  ADD KEY `id_penumpang` (`id_penumpang`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `tujuan`
--
ALTER TABLE `tujuan`
  ADD PRIMARY KEY (`id_tujuan`),
  ADD UNIQUE KEY `kode_tujuan` (`kode_tujuan`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `armada`
--
ALTER TABLE `armada`
  MODIFY `id_armada` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  MODIFY `id_log` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `penumpang`
--
ALTER TABLE `penumpang`
  MODIFY `id_penumpang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tujuan`
--
ALTER TABLE `tujuan`
  MODIFY `id_tujuan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  ADD CONSTRAINT `log_aktivitas_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`id_tujuan`) REFERENCES `tujuan` (`id_tujuan`),
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`id_armada`) REFERENCES `armada` (`id_armada`),
  ADD CONSTRAINT `transaksi_ibfk_3` FOREIGN KEY (`id_penumpang`) REFERENCES `penumpang` (`id_penumpang`),
  ADD CONSTRAINT `transaksi_ibfk_4` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
