package com.example.quanlydiemsinhvien.data_models;

public class AccountSinhVien {
    public String id;
    public String password;

    public AccountSinhVien() {
    }

    public AccountSinhVien(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
