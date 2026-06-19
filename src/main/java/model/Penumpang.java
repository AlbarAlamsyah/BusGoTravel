package model;

public class Penumpang {

    private int idPenumpang;
    private String nik;
    private String namaPenumpang;
    private String noHp;
    private String alamat;
    private String jenisKelamin;

    public Penumpang() {
    }

    public Penumpang(int idPenumpang, String nik, String namaPenumpang, String noHp, String alamat, String jenisKelamin) {
        this.idPenumpang = idPenumpang;
        this.nik = nik;
        this.namaPenumpang = namaPenumpang;
        this.noHp = noHp;
        this.alamat = alamat;
        this.jenisKelamin = jenisKelamin;
    }

    public int getIdPenumpang() {
        return idPenumpang;
    }

    public void setIdPenumpang(int idPenumpang) {
        this.idPenumpang = idPenumpang;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNamaPenumpang() {
        return namaPenumpang;
    }

    public void setNamaPenumpang(String namaPenumpang) {
        this.namaPenumpang = namaPenumpang;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}