

package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity1 extends Activity  {









    boolean inrow = false,  inMANUAL01 = false, inMANUAL02 = false, inMANUAL03 = false;
    boolean inINFO_ENG = false, inMANUAL04= false, inMANUAL05 = false, inATT_FILE_NO_MK=false, inMANUAL06 = false, inMANUAL07 = false;
    boolean inMANUAL08 = false, inMANUAL09 = false, inRCP_PARTS_DTLS= false, inMANUAL_IMG01=false, inRCP_NM=false,inINFO_PRO=false;
    boolean inMANUAL_IMG03=false, inMANUAL11=false, inMANUAL_IMG02=false, inINFO_CAR=false, inMANUAL12=false, inMANUAL_IMG05=false;
    boolean inMANUAL_IMG04=false, inMANUAL10=false,  inMANUAL_IMG06=false, inMANUAL_IMG07=false;
    boolean inMANUAL_IMG08=false, inMANUAL_IMG09=false, inMANUAL_IMG10=false, inMANUAL_IMG11=false, inMANUAL_IMG12=false;
    boolean inINFO_NA=false, inINFO_FAT=false;
    //INFO_ENG=열량, ATT_FILE_NO_MK=전체이미지, RCP_PARTS_DTLS=재료들, RCP_NM=음식명,INFO_PRO=단백질, INFO_CAR=탄수화물,
    // INFO_NA=나트륨, INFO_FAT=지방
    String  MANUAL01=null, MANUAL02=null, MANUAL03=null, MANUAL04=null, MANUAL05=null, MANUAL06=null;
    String MANUAL07=null, MANUAL08=null, MANUAL09=null, MANUAL10=null, MANUAL11=null, MANUAL12=null, MANUAL_IMG01=null;
    String MANUAL_IMG02=null, MANUAL_IMG03=null, MANUAL_IMG04=null, MANUAL_IMG05=null, MANUAL_IMG06=null, MANUAL_IMG07=null, MANUAL_IMG08=null;
    String MANUAL_IMG09=null, MANUAL_IMG10=null, MANUAL_IMG11=null, MANUAL_IMG12=null, INFO_ENG=null, ATT_FILE_NO_MK=null, RCP_PARTS_DTLS=null;
    String RCP_NM=null, INFO_PRO=null, INFO_CAR=null,  INFO_NA=null, INFO_FAT=null;


    String key = "2d1637715c0a40a0b0d5";


    TextView text;
    TextView text1;
    ImageView image;

    ImageButton intent_btn1;
    ImageButton intent_btn2;
    ImageButton intent_btn3;
    ImageButton intent_btn4;
    ImageButton intent_btn5;

    DBHelper helper;
    SQLiteDatabase db;

    ContentValues values=new ContentValues();

    ListView listView;
    SimpleCursorAdapter adapter1;
    MyListAdapter myListAdapter;
    ArrayList<Listviewitem> list_itemArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        intent_btn1=findViewById(R.id.tab_info);
        intent_btn2=findViewById(R.id.tab_food);
        intent_btn3=findViewById(R.id.tab_ingrediants);
        intent_btn4=findViewById(R.id.tab_camera);
        intent_btn5=findViewById(R.id.tab_exercise);

        intent_btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity1.this, InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        intent_btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity1.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
/*
        intent_btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity1.this, MainActivity1.class);
                startActivity(intent);
                finish();
            }
        });
*/
        intent_btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity1.this, search_with_image.class);
                startActivity(intent);
                finish();
            }
        });
        intent_btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity1.this, recommand.class);
                startActivity(intent);
                finish();
            }
        });


        StrictMode.enableDefaults();
        helper =new DBHelper(MainActivity1.this,"foodingrediants.db",null,1);
        db=helper.getWritableDatabase();
        helper.onCreate(db);



        //     text = (TextView) findViewById(R.id.result);

        findViewById(R.id.button1).setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        //       text.setText(null);
                        show1();
                    }
                }
        );



    }




    void show1() {
        EditText edit1;
        edit1 = (EditText) findViewById(R.id.edit1);
        String str = edit1.getText().toString();//EditText에 작성된 Text얻어오기
        String RCP_PARTS = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String queryUrl = "http://openapi.foodsafetykorea.go.kr/api/" + key + "/COOKRCP01/xml/1/5/RCP_PARTS_DTLS=" + RCP_PARTS;
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");
            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {


                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        System.out.println("---------------44444.");
                        if (parser.getName().equals("MANUAL01")) {
                            inMANUAL01 = true;
                            System.out.println("---------------5.");
                        }
                        if (parser.getName().equals("MANUAL02")) {
                            inMANUAL02 = true;
                            System.out.println("---------------6.");
                        }
                        if (parser.getName().equals("INFO_ENG")) {
                            inINFO_ENG = true;
                            System.out.println("---------------7.");
                        }
                        if (parser.getName().equals("MANUAL03")) {
                            inMANUAL03 = true;
                            System.out.println("---------------8.");
                        }

                        if (parser.getName().equals("MANUAL04")) {
                            inMANUAL04 = true;
                            System.out.println("---------------9");
                        }

                        if (parser.getName().equals("MANUAL05")) {
                            inMANUAL05 = true;
                            System.out.println("---------------10.");
                        }
                        if (parser.getName().equals("ATT_FILE_NO_MK")) {
                            inATT_FILE_NO_MK = true;
                            System.out.println("---------------11.");
                        }
                        if (parser.getName().equals("MANUAL06")) {
                            inMANUAL06 = true;
                            System.out.println("---------------12.");
                        }
                        if (parser.getName().equals("MANUAL07")) {
                            inMANUAL07 = true;
                            System.out.println("---------------13.");
                        }
                        if (parser.getName().equals("MANUAL08")) {
                            inMANUAL08 = true;
                            System.out.println("---------------14.");
                        }
                        if (parser.getName().equals("MANUAL09")) {
                            inMANUAL09 = true;
                            System.out.println("---------------15.");
                        }
                        if (parser.getName().equals("RCP_PARTS_DTLS")) {
                            inRCP_PARTS_DTLS = true;
                            System.out.println("---------------16.");
                        }
                        if (parser.getName().equals("MANUAL_IMG01")) {
                            inMANUAL_IMG01 = true;
                            System.out.println("---------------17.");
                        }
                        if (parser.getName().equals("RCP_NM")) {
                            inRCP_NM = true;
                            System.out.println("---------------18.");
                        }
                        if (parser.getName().equals("INFO_PRO")) {
                            inINFO_PRO = true;
                            System.out.println("---------------19.");
                        }
                        if (parser.getName().equals("MANUAL_IMG03")) {
                            inMANUAL_IMG03 = true;
                            System.out.println("---------------20.");
                        }
                        if (parser.getName().equals("MANUAL11")) {
                            inMANUAL11 = true;
                            System.out.println("---------------21.");
                        }
                        if (parser.getName().equals("MANUAL_IMG02")) {
                            inMANUAL_IMG02 = true;
                            System.out.println("---------------22.");
                        }
                        if (parser.getName().equals("INFO_CAR")) {
                            inINFO_CAR = true;
                            System.out.println("---------------23.");
                        }
                        if (parser.getName().equals("MANUAL12")) {
                            inMANUAL12 = true;
                            System.out.println("---------------24.");
                        }
                        if (parser.getName().equals("MANUAL_IMG05")) {
                            inMANUAL_IMG05 = true;
                            System.out.println("---------------25.");
                        }
                        if (parser.getName().equals("MANUAL_IMG04")) {
                            inMANUAL_IMG04 = true;
                            System.out.println("---------------26.");
                        }
                        if (parser.getName().equals("MANUAL10")) {
                            inMANUAL10 = true;
                            System.out.println("---------------27.");
                        }
                        if (parser.getName().equals("MANUAL_IMG06")) {
                            inMANUAL_IMG06 = true;
                            System.out.println("---------------28.");
                        }
                        if (parser.getName().equals("MANUAL_IMG07")) {
                            inMANUAL_IMG07 = true;
                            System.out.println("---------------29.");
                        }
                        if (parser.getName().equals("MANUAL_IMG08")) {
                            inMANUAL_IMG08 = true;
                            System.out.println("--------------30.");
                        }
                        if (parser.getName().equals("MANUAL_IMG09")) {
                            inMANUAL_IMG09 = true;
                            System.out.println("---------------31.");
                        }
                        if (parser.getName().equals("MANUAL_IMG10")) {
                            inMANUAL_IMG10 = true;
                            System.out.println("---------------32.");
                        }
                        if (parser.getName().equals("MANUAL_IMG11")) {
                            inMANUAL_IMG11 = true;
                            System.out.println("---------------33.");
                        }
                        if (parser.getName().equals("MANUAL_IMG12")) {
                            inMANUAL_IMG12 = true;
                            System.out.println("---------------34.");
                        }
                        if (parser.getName().equals("INFO_NA")) {
                            inINFO_NA = true;
                            System.out.println("---------------35.");
                        }
                        if (parser.getName().equals("INFO_FAT")) {
                            inINFO_FAT = true;
                            System.out.println("---------------36.");
                        }


                        if (parser.getName().equals("message")) { //message 태그를 만나면 에러 출력
                            //          text.setText(text.getText() + "에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;


                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (inMANUAL01) { //isAddress이 true일 때 태그의 내용을 저장.
                            MANUAL01 = parser.getText();
                            inMANUAL01 = false;
                        }
                        if (inMANUAL02) { //isMapx이 true일 때 태그의 내용을 저장.
                            MANUAL02 = parser.getText();
                            inMANUAL02 = false;
                        }
                        if (inMANUAL03) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL03 = parser.getText();
                            inMANUAL03 = false;
                        }
                        if (inINFO_ENG) { //isMapy이 true일 때 태그의 내용을 저장.
                            INFO_ENG = parser.getText();
                            inINFO_ENG = false;
                        }
                        if (inMANUAL04) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL04 = parser.getText();
                            inMANUAL04 = false;
                        }
                        if (inMANUAL05) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL05 = parser.getText();
                            inMANUAL05 = false;
                        }
                        if (inATT_FILE_NO_MK) { //isMapy이 true일 때 태그의 내용을 저장.
                            ATT_FILE_NO_MK = parser.getText();
                            inATT_FILE_NO_MK = false;
                        }
                        if (inMANUAL06) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL06 = parser.getText();
                            inMANUAL06 = false;
                        }
                        if (inMANUAL07) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL07 = parser.getText();
                            inMANUAL07 = false;
                        }
                        if (inMANUAL08) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL08 = parser.getText();
                            inMANUAL08 = false;
                        }
                        if (inMANUAL09) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL09 = parser.getText();
                            inMANUAL09 = false;
                        }
                        if (inRCP_PARTS_DTLS) { //isMapy이 true일 때 태그의 내용을 저장.
                            RCP_PARTS_DTLS = parser.getText();
                            inRCP_PARTS_DTLS = false;
                        }
                        if (inMANUAL_IMG01) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG01 = parser.getText();
                            inMANUAL_IMG01 = false;
                        }
                        if (inRCP_NM) { //isMapy이 true일 때 태그의 내용을 저장.
                            RCP_NM = parser.getText();
                            inRCP_NM = false;
                        }
                        if (inINFO_PRO) { //isMapy이 true일 때 태그의 내용을 저장.
                            INFO_PRO = parser.getText();
                            inINFO_PRO = false;
                        }
                        if (inMANUAL_IMG03) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG03 = parser.getText();
                            inMANUAL_IMG03 = false;
                        }
                        if (inMANUAL11) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL11 = parser.getText();
                            inMANUAL11 = false;
                        }
                        if (inMANUAL_IMG02) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG02 = parser.getText();
                            inMANUAL_IMG02 = false;
                        }
                        if (inINFO_CAR) { //isMapy이 true일 때 태그의 내용을 저장.
                            INFO_CAR = parser.getText();
                            inINFO_CAR = false;
                        }
                        if (inMANUAL12) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL12 = parser.getText();
                            inMANUAL12 = false;
                        }
                        if (inMANUAL_IMG05) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG05 = parser.getText();
                            inMANUAL_IMG05 = false;
                        }
                        if (inMANUAL_IMG04) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG04 = parser.getText();
                            inMANUAL_IMG04 = false;
                        }
                        if (inMANUAL10) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL10 = parser.getText();
                            inMANUAL10 = false;
                        }

                        if (inMANUAL_IMG06) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG06 = parser.getText();
                            inMANUAL_IMG06 = false;
                        }
                        if (inMANUAL_IMG07) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG07 = parser.getText();
                            inMANUAL_IMG07 = false;
                        }
                        if (inMANUAL_IMG08) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG08 = parser.getText();
                            inMANUAL_IMG08 = false;
                        }
                        if (inMANUAL_IMG09) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG09 = parser.getText();
                            inMANUAL_IMG09 = false;
                        }
                        if (inMANUAL_IMG10) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG10 = parser.getText();
                            inMANUAL_IMG10 = false;
                        }
                        if (inMANUAL_IMG11) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG11 = parser.getText();
                            inMANUAL_IMG11 = false;
                        }
                        if (inMANUAL_IMG12) { //isMapy이 true일 때 태그의 내용을 저장.
                            MANUAL_IMG12 = parser.getText();
                            inMANUAL_IMG12 = false;
                        }
                        if (inINFO_NA) { //isMapy이 true일 때 태그의 내용을 저장.
                            INFO_NA = parser.getText();
                            inINFO_NA = false;
                        }
                        if (inINFO_FAT) { //isMapy이 true일 때 태그의 내용을 저장.
                            INFO_FAT = parser.getText();
                            inINFO_FAT = false;
                        }
                        break;


                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("row")) {

                            //SQlite 넣기

                            values.put("Rcp_nm",RCP_NM);
                            values.put("Rcp_parts_dtls",RCP_PARTS_DTLS);
                            values.put("Manual1",MANUAL01);
                            values.put("Manual2",MANUAL02);
                            values.put("Manual3",MANUAL03);
                            values.put("Manual4",MANUAL04);
                            values.put("Manual5",MANUAL05);
                            values.put("Manual6",MANUAL06);
                            values.put("Manual7",MANUAL07);
                            values.put("Manual8",MANUAL08);
                            values.put("Manual9",MANUAL09);
                            values.put("Manual10",MANUAL10);
                            values.put("Manual11",MANUAL11);
                            values.put("Manual12",MANUAL12);
                            values.put("Manual_img1",MANUAL_IMG01);
                            values.put("Manual_img2",MANUAL_IMG02);
                            values.put("Manual_img3",MANUAL_IMG03);
                            values.put("Manual_img4",MANUAL_IMG04);
                            values.put("Manual_img5",MANUAL_IMG05);
                            values.put("Manual_img6",MANUAL_IMG06);
                            values.put("Manual_img7",MANUAL_IMG07);
                            values.put("Manual_img8",MANUAL_IMG08);
                            values.put("Manual_img9",MANUAL_IMG09);
                            values.put("Manual_img10",MANUAL_IMG10);
                            values.put("Manual_img11",MANUAL_IMG11);
                            values.put("Manual_img12",MANUAL_IMG12);
                            values.put("Info_eng",INFO_ENG);
                            values.put("Info_pro",INFO_PRO);
                            values.put("Info_car",INFO_CAR);
                            values.put("Info_na",INFO_NA);
                            values.put("Info_Fat",INFO_FAT);
                            values.put("Att_file_no_mk",ATT_FILE_NO_MK);

                            db.insert("foodingrediants", null, values);

                            inrow = false;

                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
//            text.setText("에러가..났습니다...");
        }


        String imageUrl = ATT_FILE_NO_MK;
        list_itemArrayList = new ArrayList<Listviewitem>();
        listView = (ListView) findViewById(R.id.list);
        myListAdapter = new MyListAdapter(MainActivity1.this, list_itemArrayList);
        listView.setAdapter(myListAdapter);




        String a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20;
        String sql = "select Att_file_no_mk, Rcp_nm, Info_eng, Info_car, Info_pro, Info_fat, Manual1, Manual2, Manual3, Manual4, Manual5 from foodingrediants;";
        Cursor c = db.rawQuery(sql, null);
        while(c.moveToNext()){
            a1=c.getString(0);
            a2=c.getString(1);
            a3=c.getString(2);
            a4=c.getString(3);
            a5=c.getString(4);
            a6=c.getString(5);
            a7=c.getString(6);
            a8=c.getString(7);
            a9=c.getString(8);
            a10=c.getString(9);
            a11=c.getString(10);
            list_itemArrayList.add(
                    new Listviewitem(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11)
            );
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra("Att_file_no_mk", list_itemArrayList.get(position).getAtt_file_no_mk());
                intent.putExtra("Rcp_nm", list_itemArrayList.get(position).getRcp_nm());
                intent.putExtra("Info_eng",list_itemArrayList.get(position).getInfo_eng());
                intent.putExtra("Info_car",list_itemArrayList.get(position).getInfo_car());
                intent.putExtra("Info_pro",list_itemArrayList.get(position).getInfo_pro());
                intent.putExtra("Info_fat",list_itemArrayList.get(position).getInfo_fat());

                intent.putExtra("Manual1",list_itemArrayList.get(position).getManual1());
                intent.putExtra("Manual2",list_itemArrayList.get(position).getManual2());
                intent.putExtra("Manual3",list_itemArrayList.get(position).getManual3());
                intent.putExtra("Manual4",list_itemArrayList.get(position).getManual4());
                intent.putExtra("Manual5",list_itemArrayList.get(position).getManual5());

                startActivity(intent);
            }

        });


        c.close();
        db.execSQL("delete from foodingrediants");

    }



}

