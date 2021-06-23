package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class search_with_image extends AppCompatActivity {

    //-----------
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

    String food_name;

    TextView text;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;

    ImageView image;

    DBHelper helper;
    SQLiteDatabase db;

    ContentValues values=new ContentValues();

    ListView listView;
    SimpleCursorAdapter adap;

    MyListAdapter myListAdapter;
    ArrayList<Listviewitem> list_itemArrayList;

    //-----------


    String selectedImagePath;
    private ImageView imageview;
    private static final int PERMISSION_REQUEST = 1001;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private int[] grantResults;
    List<String> permissionsList = new ArrayList<>();
    private int requestCode;

    public void openSomeActivityForResult(ActivityResultLauncher<Intent> someActivityResultLauncher) {
        /*
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

         */
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermissions();
        setContentView(R.layout.activity_search_with_image);
        imageview = findViewById(R.id.image_result);
        System.out.println("aa");
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        selectedImagePath = getPath(getApplicationContext(), uri);
                        imageview.setImageURI(uri);
                        Toast.makeText(getApplicationContext(), selectedImagePath, Toast.LENGTH_LONG).show();
                        connectServer();
                        //System.out.println(food_name);
                        //show();
                    }
                });


        //StrictMode.enableDefaults();
        helper =new DBHelper(search_with_image.this,"foodname.db",null,1);
        db=helper.getWritableDatabase();
        helper.onCreate(db);

        openSomeActivityForResult(someActivityResultLauncher);

        //show();
    }
    private void initPermissions() {
        permissionsList.clear();

        // Determine which permissions have not been granted
        for(String permission : permissions){
            if(ActivityCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                permissionsList.add(permission);
            }
        }

        //Request permission
        if(!permissionsList.isEmpty()){
            String[] permissions = permissionsList.toArray(new String[permissionsList.size()]);// Convert List to array
            ActivityCompat.requestPermissions(search_with_image.this, permissions, PERMISSION_REQUEST);
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_REQUEST:
                break;
            default:
                break;
        }
    }




    void postRequest(String postUrl, RequestBody postBody) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView responseText = findViewById(R.id.responseText);
                        responseText.setText("Failed to Connect to Server");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView responseText = findViewById(R.id.responseText);
                        try {
                            food_name = response.body().string();
                            responseText.setText(food_name);
                            System.out.println(food_name);
                            response.close();
                            show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    public void connectServer(){

        //EditText ipv4AddressView = findViewById(R.id.IPAddress);
        //String ipv4Address = ipv4AddressView.getText().toString(); 라면

        //String postUrl= "http://"+"169.254.78.114"+":"+portNumber+"/";101.101.164.14
        String postUrl= "http://"+"58.235.17.138:5000/";
        //String postUrl= "http://"+"192.168.1.74:5000/";
        //String postUrl= "http://"+"169.254.37.67:5000/";
        //String postUrl= "http://"+"101.101.164.14:5000/";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        // Read BitMap by file path
        Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath, options);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        RequestBody postBodyImage = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "androidFlask.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .build();

        //TextView responseText = findViewById(R.id.responseText);
        //responseText.setText("Please wait ...");
        postRequest(postUrl, postBodyImage);
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 201);

        //Intent intent = new Intent(this,search_with_image.class);
        //intent.setType("*/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(intent, 1);
    }
    /*
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(requestCode,resCode,data);
        if(resCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            selectedImagePath = getPath(getApplicationContext(), uri);
            imageview.setImageURI(uri);
        }
    }*/



    // Implementation of the getPath() method and all its requirements is taken from the StackOverflow Paul Burke's answer: https://stackoverflow.com/a/20559175/5426539
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    void show(){
        //EditText에 작성된 Text얻어오기 김치찌개
        System.out.println("after : "+food_name);
        String RCP_PARTS = URLEncoder.encode(food_name);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
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
                            System.out.println(MANUAL01);
                            inMANUAL01 = false;
                        }
                        if (inMANUAL02 ) {
                            MANUAL02= parser.getText();
                            System.out.println(MANUAL02);
                            inMANUAL02  = false;
                        }
                        if (inMANUAL03) {
                            MANUAL03= parser.getText();
                            System.out.println(MANUAL03);
                            inMANUAL03 = false;
                        }
                        if (inINFO_ENG) {
                            INFO_ENG = parser.getText();
                            System.out.println(MANUAL04);
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
                            System.out.println("rea"+food_name);
                            Intent intent = getIntent();
                            TextView Rcp_nm=(TextView) findViewById(R.id.responseText);
                            TextView Info_eng=(TextView)findViewById(R.id.info_eng);
                            TextView Info_car=(TextView)findViewById(R.id.info_car);
                            TextView Info_pro=(TextView)findViewById(R.id.info_pro);
                            TextView Info_fat=(TextView)findViewById(R.id.info_fat);
                            TextView Manual1=(TextView)findViewById(R.id.manual1);
                            TextView Manual2=(TextView)findViewById(R.id.manual2);
                            TextView Manual3=(TextView)findViewById(R.id.manual3);
                            TextView Manual4=(TextView)findViewById(R.id.manual4);
                            TextView Manual5=(TextView)findViewById(R.id.manual5);

                            Rcp_nm.setText(food_name);
                            System.out.println("kkk"+food_name);
                            System.out.println("eng"+INFO_ENG);
                            Info_eng.setText("Calories : " + INFO_ENG + "Kcal");
                            Info_car.setText("Carbohydrate : " + INFO_CAR + "g");
                            Info_pro.setText("Protein : "+INFO_PRO+"g");
                            Info_fat.setText("Fat : "+INFO_FAT+"g");
                            Manual1.setText(MANUAL01);
                            Manual2.setText(MANUAL02);
                            Manual3.setText(MANUAL03);
                            Manual4.setText(MANUAL04);
                            Manual5.setText(MANUAL05);
                            break;
                            //SQlite 넣기 양념게장


                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
//            text.setText("에러가..났습니다...");
        }



    }


}
