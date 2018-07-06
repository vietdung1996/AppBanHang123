package vn.edu.topica.model;

import android.graphics.Bitmap;

/**
 * Created by admin on 10/15/2017.
 */

public class Loaisp {
    private int id;
    private String Tensp;
    private String Hinhanh;

    public Loaisp() {
    }

    public Loaisp(int id, String tensp, String hinhanh) {
        this.id = id;
        Tensp = tensp;
        Hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public String getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        Hinhanh = hinhanh;
    }
}
