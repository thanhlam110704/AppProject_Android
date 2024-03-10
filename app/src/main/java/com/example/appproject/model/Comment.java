package com.example.appproject.model;

public class Comment {
    private String idComment;
    private String id_account_name;
    private String id_comic_name;

    public Comment() {
    }
    public Comment(String idComment,String id_account_name,String id_comic_name){
        this.idComment=idComment;
        this.id_account_name=id_account_name;
        this.id_comic_name=id_comic_name;
    }

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    public String getId_account_name() {
        return id_account_name;
    }

    public void setId_account_name(String id_account_name) {
        this.id_account_name = id_account_name;
    }

    public String getId_comic_name() {
        return id_comic_name;
    }

    public void setId_comic_name(String id_comic_name) {
        this.id_comic_name = id_comic_name;
    }
}
