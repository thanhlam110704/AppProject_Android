package com.example.appproject.model;

public class Account {
    private int id;
    private String username;
    private String password;
    private String role;
    private byte[] avatar;
    private int phone;

    public Account(int id, String username, String password, String role, byte[] avatar, int phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.avatar = avatar;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
