package com.example.appproject.object;



public class ItemSearch {
    private String TenTruyen;
    private String TenChap;
    private String TheLoai;
    private int LinkAnh;

    public ItemSearch(String tenTruyen, String tenChap, String theLoai, int linkAnh) {
        TenTruyen = tenTruyen;
        TenChap = tenChap;
        TheLoai = theLoai;
        LinkAnh = linkAnh;
    }

    public String getTenTruyen() {
        return TenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        TenTruyen = tenTruyen;
    }

    public String getTenChap() {
        return TenChap;
    }

    public void setTenChap(String tenChap) {
        TenChap = tenChap;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String theLoai) {
        TheLoai = theLoai;
    }

    public int getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(int linkAnh) {
        LinkAnh = linkAnh;
    }
}

