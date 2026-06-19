package model;

public class User {

    private int idUser;
    private String namaLengkap;
    private String username;
    private String password;
    private String role;
    private String status;

    public User() {
    }

    public User(int idUser, String namaLengkap, String username, String password, String role, String status) {
        this.idUser = idUser;
        this.namaLengkap = namaLengkap;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    } 

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}