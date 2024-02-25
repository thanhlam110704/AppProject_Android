package com.example.appproject.object;

public class TruyenTranh {
    private String tenTruyen,tenChap,LinkAnh,TheLoai;



    public TruyenTranh(){

    }
    public TruyenTranh(String tenTruyen,String tenChap, String linkAnh)
    {
        this.tenTruyen= tenTruyen;
        this.tenChap= tenChap;
        this.LinkAnh=linkAnh;
    }
    public TruyenTranh(String tenTruyen,String tenChap, String linkAnh,String theLoai)
    {
        this.tenTruyen= tenTruyen;
        this.tenChap= tenChap;
        this.LinkAnh=linkAnh;
        this.TheLoai=theLoai;
    }
    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String theLoai) {
        TheLoai = theLoai;
    }
    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }
}
