package vn.edu.topica.model;

import java.io.Serializable;

/**
 * Created by admin on 10/23/2017.
 */

public class Sanpham implements Serializable{
    public int Id;
    public  String Tensanpham;
    public Integer Giasanpham;
    public  String Motasanpham;
    public String Hinhanhsanpham;
    public int Idsanpham;

    public Sanpham(int id, String tensanpham, Integer giasanpham, String motasanpham, String hinhanhsanpham, int idsanpham) {
        Id = id;
        Tensanpham = tensanpham;
        Giasanpham = giasanpham;
        Motasanpham = motasanpham;
        Hinhanhsanpham = hinhanhsanpham;
        Idsanpham = idsanpham;
    }

    public Sanpham() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        Tensanpham = tensanpham;
    }

    public Integer getGiasanpham() {
        return Giasanpham;
    }

    public void setGiasanpham(Integer giasanpham) {
        Giasanpham = giasanpham;
    }

    public String getMotasanpham() {
        return Motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        Motasanpham = motasanpham;
    }

    public String getHinhanhsanpham() {
        return Hinhanhsanpham;
    }

    public void setHinhanhsanpam(String hinhanhsanpam) {
        Hinhanhsanpham = hinhanhsanpam;
    }

    public int getIdsanpham() {
        return Idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        Idsanpham = idsanpham;
    }
}
