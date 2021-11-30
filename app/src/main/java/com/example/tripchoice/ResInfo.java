package com.example.tripchoice;

import android.graphics.Bitmap;

public class ResInfo {
    private String region;
    private String name;
    private String call;
    private String addr;
    private String rate;
    private String menu;
    private Bitmap bitmap;

    public ResInfo(){

    }

    public ResInfo(String region, String name, String call, String addr, String rate, String menu, Bitmap bitmap) {
        this.region = region;
        this.name = name;
        this.call = call;
        this.addr = addr;
        this.rate = rate;
        this.menu = menu;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() { return bitmap; }

    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }

    public String getMenu() { return menu; }

    public void setMenu(String menu) { this.menu = menu; }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
