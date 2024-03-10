package com.example.appproject.model;

public class Chapter {
    private int idChapter;
    private  String nameChap;
    private  String viewer;
    private  String datePublish;
    private  String img1;
    private  String img2;
    private  String img3;
    private  String img4;
    private  String img5;
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Chapter() {
    }

    public Chapter(int idChapter,String nameChap,String viewer,String datePublish,String img1,String img2,String img3,String img4,String img5
    ,byte[]image){
        this.idChapter = idChapter;
        this.nameChap = nameChap;
        this.viewer = viewer;
        this.datePublish = datePublish;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
        this.image=image;
    }
    public int getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(int idChapter) {
        this.idChapter = idChapter;
    }

    public String getNameChap() {
        return nameChap;
    }

    public void setNameChap(String nameChap) {
        this.nameChap = nameChap;
    }

    public String getViewer() {
        return viewer;
    }

    public void setViewer(String viewer) {
        this.viewer = viewer;
    }

    public String getDatePublish() {
        return datePublish;
    }

    public void setDatePublish(String datePublish) {
        this.datePublish = datePublish;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }
}
