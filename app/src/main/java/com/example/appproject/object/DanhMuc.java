package com.example.appproject.object;

import java.util.List;

public class DanhMuc {
    private String nameCategory;
    private List<TruyenTranh> comics;

    public DanhMuc(String nameCategory, List<TruyenTranh> books) {
        this.nameCategory = nameCategory;
        this.comics = books;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<TruyenTranh> getBooks() {
        return comics;
    }

    public void setBooks(List<TruyenTranh> books) {
        this.comics = books;
    }
}
