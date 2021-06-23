

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



public class MainActivity extends Activity {







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
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;


    ImageButton intent_btn1;
    ImageButton intent_btn2;
    ImageButton intent_btn3;
    ImageButton intent_btn4;
    ImageButton intent_btn5;

    ImageView image;

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
        setContentView(R.layout.activity_main);

        intent_btn1=findViewById(R.id.tab_info);
        intent_btn2=findViewById(R.id.tab_food);
        intent_btn3=findViewById(R.id.tab_ingrediants);
        intent_btn4=findViewById(R.id.tab_camera);
        intent_btn5=findViewById(R.id.tab_exercise);

        StrictMode.enableDefaults();
        helper =new DBHelper(MainActivity.this,"foodname.db",null,1);
        db=helper.getWritableDatabase();
        helper.onCreate(db);

        intent_btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
/*
        intent_btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
*/
        intent_btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, MainActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        intent_btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, search_with_image.class);
                startActivity(intent);
                finish();
            }
        });
        intent_btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, recommand.class);
                startActivity(intent);
                finish();
            }
        });

   //     text = (TextView) findViewById(R.id.result);
        findViewById(R.id.button).setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){

                        show();

                    }
                }
        );



    }

    void show(){
        EditText edit;
        edit = (EditText) findViewById(R.id.edit);
        String str= edit.getText().toString();//EditText에 작성된 Text얻어오기
        String RCP_PARTS = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/"+key+"/COOKRCP01/xml/1/5/RCP_NM="+RCP_PARTS;
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(),null);

            int parserEvent = parser.getEventType();
            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {


                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("MANUAL01")) {
                            inMANUAL01 = true;
                        }
                        if (parser.getName().equals("MANUAL02")) {
                            inMANUAL02 = true;
                        }
                        if (parser.getName().equals("INFO_ENG")) {
                            inINFO_ENG = true;
                        }
                        if (parser.getName().equals("MANUAL03")) {
                            inMANUAL03 = true;
                        }
                        if (parser.getName().equals("MANUAL04")) {
                            inMANUAL04 = true;
                        }
                        if (parser.getName().equals("MANUAL05")) {
                            inMANUAL05 = true;
                        }
                        if (parser.getName().equals("ATT_FILE_NO_MK")) {
                            inATT_FILE_NO_MK = true;
                        }
                        if (parser.getName().equals("MANUAL06")) {
                            inMANUAL06 = true;
                        }
                        if (parser.getName().equals("MANUAL07")) {
                            inMANUAL07 = true;
                        }
                        if (parser.getName().equals("MANUAL08")) {
                            inMANUAL08 = true;
                        }
                        if (parser.getName().equals("MANUAL09")) {
                            inMANUAL09 = true;
                        }
                        if (parser.getName().equals("RCP_PARTS_DTLS")) {
                            inRCP_PARTS_DTLS = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG01")) {
                            inMANUAL_IMG01 = true;
                        }
                        if (parser.getName().equals("RCP_NM")) {
                            inRCP_NM = true;
                        }
                        if (parser.getName().equals("INFO_PRO")) {
                            inINFO_PRO = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG03")) {
                            inMANUAL_IMG03 = true;
                        }
                        if (parser.getName().equals("MANUAL11")) {
                            inMANUAL11 = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG02")) {
                            inMANUAL_IMG02 = true;
                        }
                        if (parser.getName().equals("INFO_CAR")) {
                            inINFO_CAR = true;
                        }
                        if (parser.getName().equals("MANUAL12")) {
                            inMANUAL12 = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG05")) {
                            inMANUAL_IMG05 = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG04")) {
                            inMANUAL_IMG04 = true;
                        }
                        if (parser.getName().equals("MANUAL10")) {
                            inMANUAL10 = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG06")) {
                            inMANUAL_IMG06 = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG07")) {
                            inMANUAL_IMG07 = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG08")) {
                            inMANUAL_IMG08 = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG09")) {
                            inMANUAL_IMG09 = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG10")) {
                            inMANUAL_IMG10 = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG11")) {
                            inMANUAL_IMG11 = true;
                        }
                        if (parser.getName().equals("MANUAL_IMG12")) {
                            inMANUAL_IMG12 = true;
                        }
                        if (parser.getName().equals("INFO_NA")) {
                            inINFO_NA = true;
                        }
                        if (parser.getName().equals("INFO_FAT")) {
                            inINFO_FAT = true;
                        }

                        if (parser.getName().equals("message")) {
               //             text.setText(text.getText() + "에러");

                        }
                        break;


                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (inMANUAL01) {
                            MANUAL01 = parser.getText();
                            inMANUAL01 = false;
                        }
                        if (inMANUAL02 ) {
                            MANUAL02= parser.getText();
                            inMANUAL02  = false;
                        }
                        if (inMANUAL03) {
                            MANUAL03= parser.getText();
                            inMANUAL03 = false;
                        }
                        if (inINFO_ENG) {
                            INFO_ENG = parser.getText();
                            inINFO_ENG = false;
                        }
                        if (inMANUAL04) {
                            MANUAL04 = parser.getText();
                            inMANUAL04 = false;
                        }
                        if (inMANUAL05) {
                            MANUAL05 = parser.getText();
                            inMANUAL05 = false;
                        }
                        if (inATT_FILE_NO_MK) {
                            ATT_FILE_NO_MK= parser.getText();
                            inATT_FILE_NO_MK = false;
                        }
                        if (inMANUAL06) {
                            MANUAL06 = parser.getText();
                            inMANUAL06 = false;
                        }
                        if (inMANUAL07) {
                            MANUAL07 = parser.getText();
                            inMANUAL07 = false;
                        }
                        if ( inMANUAL08) {
                            MANUAL08 = parser.getText();
                            inMANUAL08 = false;
                        }
                        if (inMANUAL09) {
                            MANUAL09 = parser.getText();
                            inMANUAL09 = false;
                        }
                        if ( inRCP_PARTS_DTLS) {
                            RCP_PARTS_DTLS = parser.getText();
                            inRCP_PARTS_DTLS = false;
                        }
                        if ( inMANUAL_IMG01) {
                            MANUAL_IMG01 = parser.getText();
                            inMANUAL_IMG01 = false;
                        }
                        if ( inRCP_NM) {
                            RCP_NM = parser.getText();
                            inRCP_NM = false;
                        }
                        if (inINFO_PRO) {
                            INFO_PRO = parser.getText();
                            inINFO_PRO = false;
                        }
                        if (inMANUAL_IMG03) {
                            MANUAL_IMG03 = parser.getText();
                            inMANUAL_IMG03 = false;
                        }
                        if (inMANUAL11) {
                            MANUAL11 = parser.getText();
                            inMANUAL11 = false;
                        }
                        if (inMANUAL_IMG02) {
                            MANUAL_IMG02 = parser.getText();
                            inMANUAL_IMG02 = false;
                        }
                        if (inINFO_CAR) {
                            INFO_CAR = parser.getText();
                            inINFO_CAR = false;
                        }
                        if ( inMANUAL12) {
                            MANUAL12 = parser.getText();
                            inMANUAL12 = false;
                        }
                        if (inMANUAL_IMG05) {
                            MANUAL_IMG05 = parser.getText();
                            inMANUAL_IMG05 = false;
                        }
                        if (inMANUAL_IMG04) {
                            MANUAL_IMG04 = parser.getText();
                            inMANUAL_IMG04 = false;
                        }
                        if (inMANUAL10) {
                            MANUAL10 = parser.getText();
                            inMANUAL10 = false;
                        }

                        if (inMANUAL_IMG06) {
                            MANUAL_IMG06 = parser.getText();
                            inMANUAL_IMG06 = false;
                        }
                        if (inMANUAL_IMG07) {
                            MANUAL_IMG07 = parser.getText();
                            inMANUAL_IMG07 = false;
                        }
                        if ( inMANUAL_IMG08) {
                            MANUAL_IMG08 = parser.getText();
                            inMANUAL_IMG08 = false;
                        }
                        if (inMANUAL_IMG09) {
                            MANUAL_IMG09 = parser.getText();
                            inMANUAL_IMG09 = false;
                        }
                        if (inMANUAL_IMG10) {
                            MANUAL_IMG10 = parser.getText();
                            inMANUAL_IMG10 = false;
                        }
                        if (inMANUAL_IMG11) {
                            MANUAL_IMG11 = parser.getText();
                            inMANUAL_IMG11 = false;
                        }
                        if (inMANUAL_IMG12) {
                            MANUAL_IMG12 = parser.getText();
                            inMANUAL_IMG12 = false;
                        }
                        if (inINFO_NA) {
                            INFO_NA = parser.getText();
                            inINFO_NA = false;
                        }
                        if (inINFO_FAT) {
                            INFO_FAT= parser.getText();
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


                            db.insert("foodname",null,values);

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
        list_itemArrayList=new ArrayList<Listviewitem>();
        listView=(ListView)findViewById(R.id.list);

        myListAdapter = new MyListAdapter(MainActivity.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);





        String a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20;
        String sql = "select Att_file_no_mk, Rcp_nm, Info_eng, Info_car, Info_pro, Info_fat, Manual1, Manual2, Manual3, Manual4, Manual5 from foodname;";
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
       db.execSQL("delete from foodname");

    }






}


