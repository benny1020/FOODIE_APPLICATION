package com.example.myapplication;

import android.graphics.drawable.Drawable;

public class Listviewitem {
    private String Att_file_no_mk;
    private String Rcp_nm;
    private String Info_eng;
    private String Info_pro;
    private String Info_car;
    private String Info_fat;
    private String Manual1;
    private String Manual2;
    private String Manual3;
    private String Manual4;
    private String Manual5;


    public Listviewitem(String att_file_no_mk, String rcp_nm, String info_eng, String info_pro, String info_car, String info_fat, String manual1, String manual2, String manual3, String manual4, String manual5) {
        Att_file_no_mk = att_file_no_mk;
        Rcp_nm = rcp_nm;
        Info_eng = info_eng;
        Info_pro = info_pro;
        Info_car = info_car;
        Info_fat = info_fat;
        Manual1 = manual1;
        Manual2 = manual2;
        Manual3 = manual3;
        Manual4 = manual4;
        Manual5 = manual5;
    }

    public String getAtt_file_no_mk() {
        return Att_file_no_mk;
    }

    public void setAtt_file_no_mk(String att_file_no_mk) {
        Att_file_no_mk = att_file_no_mk;
    }

    public String getRcp_nm() {
        return Rcp_nm;
    }

    public void setRcp_nm(String rcp_nm) {
        Rcp_nm = rcp_nm;
    }

    public String getInfo_eng() {
        return Info_eng;
    }

    public void setInfo_eng(String info_eng) {
        Info_eng = info_eng;
    }

    public String getInfo_pro() {
        return Info_pro;
    }

    public void setInfo_pro(String info_pro) {
        Info_pro = info_pro;
    }

    public String getInfo_car() {
        return Info_car;
    }

    public void setInfo_car(String info_car) {
        Info_car = info_car;
    }

    public String getInfo_fat() {
        return Info_fat;
    }

    public void setInfo_fat(String info_fat) {
        Info_fat = info_fat;
    }

    public String getManual1() {
        return Manual1;
    }

    public void setManual1(String manual1) {
        Manual1 = manual1;
    }

    public String getManual2() {
        return Manual2;
    }

    public void setManual2(String manual2) {
        Manual2 = manual2;
    }

    public String getManual3() {
        return Manual3;
    }

    public void setManual3(String manual3) {
        Manual3 = manual3;
    }

    public String getManual4() {
        return Manual4;
    }

    public void setManual4(String manual4) {
        Manual4 = manual4;
    }

    public String getManual5() {
        return Manual5;
    }

    public void setManual5(String manual5) {
        Manual5 = manual5;
    }
}