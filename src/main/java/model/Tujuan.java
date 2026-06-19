package model;

public class Tujuan {

    private int idTujuan;
    private String kodeTujuan;
    private String kotaAsal;
    private String kotaTujuan;
    private double hargaEkonomi;
    private double hargaBisnis;
    private double hargaEksekutif;
    private String status;

    public Tujuan() {
    }

    public Tujuan(int idTujuan, String kodeTujuan, String kotaAsal, String kotaTujuan,
                  double hargaEkonomi, double hargaBisnis, double hargaEksekutif, String status) {
        this.idTujuan = idTujuan;
        this.kodeTujuan = kodeTujuan;
        this.kotaAsal = kotaAsal;
        this.kotaTujuan = kotaTujuan;
        this.hargaEkonomi = hargaEkonomi;
        this.hargaBisnis = hargaBisnis;
        this.hargaEksekutif = hargaEksekutif;
        this.status = status;
    }

    public int getIdTujuan() {
        return idTujuan;
    }

    public void setIdTujuan(int idTujuan) {
        this.idTujuan = idTujuan;
    }

    public String getKodeTujuan() {
        return kodeTujuan;
    }

    public void setKodeTujuan(String kodeTujuan) {
        this.kodeTujuan = kodeTujuan;
    }

    public String getKotaAsal() {
        return kotaAsal;
    }

    public void setKotaAsal(String kotaAsal) {
        this.kotaAsal = kotaAsal;
    }

    public String getKotaTujuan() {
        return kotaTujuan;
    }

    public void setKotaTujuan(String kotaTujuan) {
        this.kotaTujuan = kotaTujuan;
    }

    public double getHargaEkonomi() {
        return hargaEkonomi;
    }

    public void setHargaEkonomi(double hargaEkonomi) {
        this.hargaEkonomi = hargaEkonomi;
    }

    public double getHargaBisnis() {
        return hargaBisnis;
    }

    public void setHargaBisnis(double hargaBisnis) {
        this.hargaBisnis = hargaBisnis;
    }

    public double getHargaEksekutif() {
        return hargaEksekutif;
    }

    public void setHargaEksekutif(double hargaEksekutif) {
        this.hargaEksekutif = hargaEksekutif;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}