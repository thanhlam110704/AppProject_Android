package com.example.appproject.model;

public class Comment {
    private int idComment;
    private int id_account_name;
    private int id_comic_name;

    public Comment() {
    }

    public Comment(int idComment, int id_account_name, int id_comic_name) {
        this.idComment = idComment;
        this.id_account_name = id_account_name;
        this.id_comic_name = id_comic_name;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getId_account_name() {
        return id_account_name;
    }

    public void setId_account_name(int id_account_name) {
        this.id_account_name = id_account_name;
    }

    public int getId_comic_name() {
        return id_comic_name;
    }

    public void setId_comic_name(int id_comic_name) {
        this.id_comic_name = id_comic_name;
    }
}
