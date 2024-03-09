package com.example.appproject.model;

import com.example.appproject.db.ComicDataActivity;

import java.util.List;

public class DanhMuc {
    private String nameCategory;
    List<Comic> comics;

    public DanhMuc(String nameCategory, List<Comic> comics) {
        this.nameCategory = nameCategory;
        this.comics = comics;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public  List<Comic> comics() {
        return comics;
    }

    public void setComics(  List<Comic> comics) {
        this.comics = comics;
    }
}
