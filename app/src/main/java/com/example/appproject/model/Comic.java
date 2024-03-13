package com.example.appproject.model;

import java.io.Serializable;

public class Comic implements Serializable {
    private int id;
    private String name;
    private String author;
    private String description;
    private String status;
    private String dateUpdate;
    private byte[] avatar;

    public Comic() {
    }

    public Comic(int id, String name, String description, String author, String status, String dateUpdate, byte[] avatar) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.status = status;
        this.dateUpdate = dateUpdate;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }


}
