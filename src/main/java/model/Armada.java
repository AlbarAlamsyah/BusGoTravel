package model;

public class Armada {

    private int idArmada;
    private String kodeArmada;
    private String namaBus;
    private String platNomor;
    private int kapasitas;
    private String fasilitas;
    private String status;

    public Armada() {
    }

    public Armada(int idArmada, String kodeArmada, String namaBus, String platNomor,
                  int kapasitas, String fasilitas, String status) {
        this.idArmada = idArmada;
        this.kodeArmada = kodeArmada;
        this.namaBus = namaBus;
        this.platNomor = platNomor;
        this.kapasitas = kapasitas;
        this.fasilitas = fasilitas;
        this.status = status;
    }

    public int getIdArmada() {
        return idArmada;
    }

    public void setIdArmada(int idArmada) {
        this.idArmada = idArmada;
    }

    public String getKodeArmada() {
        return kodeArmada;
    }

    public void setKodeArmada(String kodeArmada) {
        this.kodeArmada = kodeArmada;
    }

    public String getNamaBus() {
        return namaBus;
    }

    public void setNamaBus(String namaBus) {
        this.namaBus = namaBus;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}