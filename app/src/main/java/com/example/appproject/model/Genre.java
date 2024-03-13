package com.example.appproject.model;

import java.io.Serializable;

public class Genre implements Serializable {
    private  int idGenre;
    private  String nameGenre;


    public Genre() {
    }

    public Genre(int idGenre, String nameGenre) {
        this.idGenre = idGenre;
        this.nameGenre = nameGenre;
    }

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }
}
