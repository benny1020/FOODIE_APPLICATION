package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.net.URLEncoder;

public class InfoActivity extends Activity {


    DBHelper helper;
    SQLiteDatabase db;
    ContentValues values=new ContentValues();

    EditText edit1;
    EditText edit2;
    EditText edit3;

    ImageButton intent_btn1;
    ImageButton intent_btn2;
    ImageButton intent_btn3;
    ImageButton intent_btn4;
    ImageButton intent_btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();
        System.out.println("============");

        intent_btn1=findViewById(R.id.tab_info);
        intent_btn2=findViewById(R.id.tab_food);
        intent_btn3=findViewById(R.id.tab_ingrediants);
        intent_btn4=findViewById(R.id.tab_camera);
        intent_btn5=findViewById(R.id.tab_exercise);

        helper =new DBHelper(InfoActivity.this,"info.db",null,1);
        db=helper.getWritableDatabase();
        helper.onCreate(db);

/*
        intent_btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(InfoActivity.this, InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
*/
        intent_btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        intent_btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(InfoActivity.this, MainActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        intent_btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, search_with_image.class);
                startActivity(intent);
                finish();
            }
        });
        intent_btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, recommand.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.save).setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        show();
                    }
                }
        );

        findViewById(R.id.delete).setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        db.execSQL("delete from info");
                    }
                }
        );


    }


    void show(){
        System.out.println("============");
        edit1 = (EditText) findViewById(R.id.name);
        edit2 = (EditText) findViewById(R.id.height);
        edit3 = (EditText) findViewById(R.id.weight);
        String str1= edit1.getText().toString();//EditText에 작성된 Text얻어오기
        String str2= edit2.getText().toString();//EditText에 작성된 Text얻어오기
        String str3= edit3.getText().toString();//EditText에 작성된 Text얻어오기
        String t1 = URLEncoder.encode(str1);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String t2 = URLEncoder.encode(str2);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String t3 = URLEncoder.encode(str3);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);


        String a1;
        String a2;
        String a3;
        try {
            values.put("name",t1);
            values.put("height",t2);
            values.put("weight",t3);


            db.insert("info",null,values);


            String sql = "select name,height,weight from info;";
            Cursor c = db.rawQuery(sql, null);
            while(c.moveToNext()){
                a1=c.getString(0);
                a2=c.getString(1);
                a3=c.getString(2);
                System.out.println("test");
                System.out.println(a1);
                System.out.println(a2);
                System.out.println(a3);

            }
            c.close();
        } catch (Exception e) {

        }


        //




    }


}