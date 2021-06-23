package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String sql1 ="create table if not exists foodname("
                + "_id integer primary key autoincrement, "
                + "Att_file_no_mk text,"
                + "Rcp_nm text,"
                + "Info_eng text,"
                + "Info_car text,"
                + "Info_pro text,"
                + "Info_fat text,"
                + "Info_na text,"
                + "Rcp_parts_dtls text,"
                + "Manual1 text,"
                + "Manual2 text,"
                + "Manual3 text,"
                + "Manual4 text,"
                + "Manual5 text,"
                + "Manual6 text,"
                + "Manual7 text,"
                + "Manual8 text,"
                + "Manual9 text,"
                + "Manual10 text,"
                + "Manual11 text,"
                + "Manual12 text,"
                + "Manual_img1 text,"
                + "Manual_img2 text,"
                + "Manual_img3 text,"
                + "Manual_img4 text,"
                + "Manual_img5 text,"
                + "Manual_img6 text,"
                + "Manual_img7 text,"
                + "Manual_img8 text,"
                + "Manual_img9 text,"
                + "Manual_img10 text,"
                + "Manual_img11 text,"
                + "Manual_img12 text);";


        db.execSQL(sql1);
        String sql2 ="create table if not exists foodingrediants("
                + "_id integer primary key autoincrement, "
                + "Att_file_no_mk text,"
                + "Rcp_nm text,"
                + "Info_eng text,"
                + "Info_car text,"
                + "Info_pro text,"
                + "Info_fat text,"
                + "Info_na text,"
                + "Rcp_parts_dtls text,"
                + "Manual1 text,"
                + "Manual2 text,"
                + "Manual3 text,"
                + "Manual4 text,"
                + "Manual5 text,"
                + "Manual6 text,"
                + "Manual7 text,"
                + "Manual8 text,"
                + "Manual9 text,"
                + "Manual10 text,"
                + "Manual11 text,"
                + "Manual12 text,"
                + "Manual_img1 text,"
                + "Manual_img2 text,"
                + "Manual_img3 text,"
                + "Manual_img4 text,"
                + "Manual_img5 text,"
                + "Manual_img6 text,"
                + "Manual_img7 text,"
                + "Manual_img8 text,"
                + "Manual_img9 text,"
                + "Manual_img10 text,"
                + "Manual_img11 text,"
                + "Manual_img12 text);";
        db.execSQL(sql2);

        String sql3 ="create table if not exists info("
                + "_id integer primary key autoincrement, "
                + "name text,"
                + "height text,"
                + "weight text);";
        db.execSQL(sql3);

        String sql4 ="create table if not exists exercise("
                + "_id integer primary key autoincrement, "
                + "Att_file_no_mk text,"
                + "Rcp_nm text,"
                + "Info_eng text,"
                + "Info_car text,"
                + "Info_pro text,"
                + "Info_fat text,"
                + "Info_na text,"
                + "Rcp_parts_dtls text,"
                + "Manual1 text,"
                + "Manual2 text,"
                + "Manual3 text,"
                + "Manual4 text,"
                + "Manual5 text,"
                + "Manual6 text,"
                + "Manual7 text,"
                + "Manual8 text,"
                + "Manual9 text,"
                + "Manual10 text,"
                + "Manual11 text,"
                + "Manual12 text,"
                + "Manual_img1 text,"
                + "Manual_img2 text,"
                + "Manual_img3 text,"
                + "Manual_img4 text,"
                + "Manual_img5 text,"
                + "Manual_img6 text,"
                + "Manual_img7 text,"
                + "Manual_img8 text,"
                + "Manual_img9 text,"
                + "Manual_img10 text,"
                + "Manual_img11 text,"
                + "Manual_img12 text);";
        db.execSQL(sql4);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        String sql1 = "drop table if exists food";
        db.execSQL(sql1);
        String sql2 = "drop table if exists foodingrediants";
        db.execSQL(sql2);
        String sql3 = "drop table if exists info";
        db.execSQL(sql3);
        String sql4 = "drop table if exists exercise";
        db.execSQL(sql4);
        onCreate(db);
    }

}