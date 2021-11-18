package com.example.tripchoice.DongHyun;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {
    private ArrayList<attraction> list = new ArrayList<attraction>();

    public ArrayList<attraction> getList() {
        return list;
    }

    public void setList(ArrayList<attraction> list){
        this.list = list;
    }
}
