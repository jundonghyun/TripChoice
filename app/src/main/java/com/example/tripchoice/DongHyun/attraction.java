package com.example.tripchoice.DongHyun;

import android.graphics.Bitmap;

public class attraction {
    String title;
    String address;
    Bitmap bitmap;
    double longitude;//128...
    double latitude;//32,..

    public attraction(String t, String a, Bitmap b, double lo, double la) {
        this.title = t;
        this.address = a;
        this.bitmap = b;
        this.longitude = lo;
        this.latitude = la;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
