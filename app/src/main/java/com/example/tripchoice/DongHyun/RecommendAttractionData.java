package com.example.tripchoice.DongHyun;

import java.io.Serializable;

public class RecommendAttractionData implements Serializable {
    String POIName;
    String POIAddress;
    double latitude;
    double longitude;

    RecommendAttractionData(String n, String a, double la, double lo){
        this.POIName = n;
        this.POIAddress = a;
        this.latitude = la;
        this.longitude = lo;
    }
}
