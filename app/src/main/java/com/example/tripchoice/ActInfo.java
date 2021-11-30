package com.example.tripchoice;

import android.graphics.Bitmap;


public class ActInfo {
    String name;
    String addr;
    String call;
    String rate;
    String region;
    String Desc;
    Bitmap bitmap;

    public ActInfo() {
    }

    public ActInfo(String name, String addr, String call, String rate, String region, Bitmap bitmap) {
        this.name = name;
        this.addr = addr;
        this.call = call;
        this.rate = rate;
        this.region = region;
        this.bitmap = bitmap;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Bitmap getBitmap() { return bitmap; }

    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
