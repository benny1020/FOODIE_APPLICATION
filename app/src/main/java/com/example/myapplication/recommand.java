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
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
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

public class recommand extends AppCompatActivity {
    String selectedImagePath_breakfast;
    String selectedImagePath_lunch;
    String selectedImagePath_dinner;
    String breakfast;
    String lunch;
    String dinner;
    String gim = "김치찌개";
    float total_calories = 0;
    int num;
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    if(num == 1)//breakfast
                    {
                        selectedImagePath_breakfast = getPath(getApplicationContext(), uri);

                        Toast.makeText(getApplicationContext(), selectedImagePath_breakfast, Toast.LENGTH_LONG).show();
                        connectServer(selectedImagePath_breakfast);
                        System.out.println("after connect " + breakfast);

                    }
                    else if(num==2)//lunch
                    {
                        selectedImagePath_lunch = getPath(getApplicationContext(), uri);

                        Toast.makeText(getApplicationContext(), selectedImagePath_lunch, Toast.LENGTH_LONG).show();
                        connectServer(selectedImagePath_lunch);

                    }
                    else
                    {
                        selectedImagePath_dinner = getPath(getApplicationContext(), uri);

                        Toast.makeText(getApplicationContext(), selectedImagePath_dinner, Toast.LENGTH_LONG).show();
                        connectServer(selectedImagePath_dinner);

                    }

                    //System.out.println(food_name);
                    //show();
                }
            });

    boolean inrow = false, inMANUAL01 = false, inMANUAL02 = false, inMANUAL03 = false;
    boolean inINFO_ENG = false, inMANUAL04 = false, inMANUAL05 = false, inATT_FILE_NO_MK = false, inMANUAL06 = false, inMANUAL07 = false;
    boolean inMANUAL08 = false, inMANUAL09 = false, inRCP_PARTS_DTLS = false, inMANUAL_IMG01 = false, inRCP_NM = false, inINFO_PRO = false;
    boolean inMANUAL_IMG03 = false, inMANUAL11 = false, inMANUAL_IMG02 = false, inINFO_CAR = false, inMANUAL12 = false, inMANUAL_IMG05 = false;
    boolean inMANUAL_IMG04 = false, inMANUAL10 = false, inMANUAL_IMG06 = false, inMANUAL_IMG07 = false;
    boolean inMANUAL_IMG08 = false, inMANUAL_IMG09 = false, inMANUAL_IMG10 = false, inMANUAL_IMG11 = false, inMANUAL_IMG12 = false;
    boolean inINFO_NA = false, inINFO_FAT = false;
    //INFO_ENG=열량, ATT_FILE_NO_MK=전체이미지, RCP_PARTS_DTLS=재료들, RCP_NM=음식명,INFO_PRO=단백질, INFO_CAR=탄수화물,
    // INFO_NA=나트륨, INFO_FAT=지방
    String MANUAL01 = null, MANUAL02 = null, MANUAL03 = null, MANUAL04 = null, MANUAL05 = null, MANUAL06 = null;
    String MANUAL07 = null, MANUAL08 = null, MANUAL09 = null, MANUAL10 = null, MANUAL11 = null, MANUAL12 = null, MANUAL_IMG01 = null;
    String MANUAL_IMG02 = null, MANUAL_IMG03 = null, MANUAL_IMG04 = null, MANUAL_IMG05 = null, MANUAL_IMG06 = null, MANUAL_IMG07 = null, MANUAL_IMG08 = null;
    String MANUAL_IMG09 = null, MANUAL_IMG10 = null, MANUAL_IMG11 = null, MANUAL_IMG12 = null, INFO_ENG = null, ATT_FILE_NO_MK = null, RCP_PARTS_DTLS = null;
    String RCP_NM = null, INFO_PRO = null, INFO_CAR = null, INFO_NA = null, INFO_FAT = null;


    String key = "2d1637715c0a40a0b0d5";


    private ImageView imageview;
    private static final int PERMISSION_REQUEST = 1001;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private int[] grantResults;
    List<String> permissionsList = new ArrayList<>();
    private int requestCode;

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

    DBHelper helper2;
    SQLiteDatabase db2;

    ContentValues values = new ContentValues();

    ListView listView;
    SimpleCursorAdapter adapter1;
    MyListAdapter myListAdapter;
    ArrayList<Listviewitem> list_itemArrayList;


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
    public void gallery(int nume)
    {
        initPermissions();
        num = nume;
        openSomeActivityForResult(someActivityResultLauncher);
    }

    double getdailycalories()
    {
        helper2 =new DBHelper(recommand.this,"info.db",null,1);
        db2=helper2.getWritableDatabase();String a1="";
        String a2="";
        String a3="";
        int height,weight;
        String sql = "select name,height,weight from info;";
        Cursor c = db2.rawQuery(sql,null);
        while(c.moveToNext())
        {

            a1=c.getString(0);
            a2=c.getString(1);
            a3=c.getString(2);
            System.out.println(a2+" "+a3);
        }
        c.close();
        height=Integer.parseInt(a2);
        weight=Integer.parseInt(a3);

        double standard_weight=(double)(height-100)*0.9;
        double activity_index=(double)(35);
        System.out.println("calor : "+(double)standard_weight*activity_index);
        return (double)standard_weight*activity_index;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermissions();
        setContentView(R.layout.activity_recommand);


        breakfast = "김치찌개";
        lunch="된장찌개";
        dinner="미역국";

        intent_btn1=findViewById(R.id.tab_info);
        intent_btn2=findViewById(R.id.tab_food);
        intent_btn3=findViewById(R.id.tab_ingrediants);
        intent_btn4=findViewById(R.id.tab_camera);
        intent_btn5=findViewById(R.id.tab_exercise);

        StrictMode.enableDefaults();
        helper = new DBHelper(recommand.this, "exercise.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);
        db.execSQL("delete from exercise");
        findViewById(R.id.breakfast).setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        num = 1;
                        gallery(1);
                        //show_breakfast();
                    }
                }
        );
        findViewById(R.id.lunch).setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        num = 2;
                        gallery(2);
                        //show_lunch();
                    }
                }
        );
        findViewById(R.id.dinner).setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        num = 3;
                        gallery(3);
                        //show_dinner();
                    }
                }
        );

        findViewById(R.id.exercise).setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        TextView tot = findViewById(R.id.total);
                        TextView rec = findViewById(R.id.recommand);
                        double my_calories =  getdailycalories();
                        tot.setText("오늘 섭취한 총 칼로리 : " +String.valueOf(total_calories) + " Kcal");
                        System.out.println("mycal" + my_calories);
                        System.out.println("total_cal" + total_calories);
                        if( my_calories > total_calories) // 더먹어야하는경우
                        {
                            rec.setText( String.valueOf( Math.round(my_calories-total_calories) ) +"Kcal만큼 더 섭취하셔야합니다");
                        }
                        else
                        {
                            rec.setText( String.valueOf( Math.round(total_calories-my_calories) ) +"Kcal만큼 빼셔야합니다\n"+ String.valueOf( Math.round((total_calories-my_calories)*30))+"걸음만큼 뛰시길 권장합니다" );
                        }

                    }
                }
        );

        /*

         */
        findViewById(R.id.clear).setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        db.execSQL("delete from exercise");
                        if(list_itemArrayList!=null){
                            list_itemArrayList.clear();
                            myListAdapter.notifyDataSetChanged();
                        }
                        total_calories = 0;
                        TextView tot = findViewById(R.id.total);
                        TextView rec = findViewById(R.id.recommand);
                        tot.setText("");
                        rec.setText("");
                    }
                }
        );
        intent_btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(recommand.this, InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        intent_btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(recommand.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        intent_btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(recommand.this, MainActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        intent_btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(recommand.this, search_with_image.class);
                startActivity(intent);
                finish();
            }
        });
        /*
        intent_btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(recommand.this, recommand.class);
                startActivity(intent);
                finish();
            }
        });
*/

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
            ActivityCompat.requestPermissions(recommand.this, permissions, PERMISSION_REQUEST);
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
                        System.out.println("failure");
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
                            breakfast = response.body().string();
                            System.out.println("response" + breakfast);
                            lunch = breakfast;
                            dinner = lunch;
                            if( num == 1)
                                show_breakfast();
                            else if(num == 2)
                                show_lunch();
                            else
                                show_dinner();

                            response.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            response.close();
                        }
                    }
                });
            }
        });
    }

    public void connectServer(String selectedImagePath){
        System.out.println("asdasd");
        //EditText ipv4AddressView = findViewById(R.id.IPAddress);
        //String ipv4Address = ipv4AddressView.getText().toString(); 당근

        //String postUrl= "http://"+"169.254.78.114"+":"+portNumber+"/";101.101.164.14 양파
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


    void show_breakfast() {
        if(list_itemArrayList!=null){
            list_itemArrayList.clear();
            myListAdapter.notifyDataSetChanged();
        }
        //gallery(1);

        String str=breakfast;
        System.out.println("start_breakfast" + breakfast);
        int check=0;
        String RCP_PARTS = breakfast;//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        System.out.println("real  "+RCP_PARTS);
        String queryUrl = "http://openapi.foodsafetykorea.go.kr/api/" + key + "/COOKRCP01/xml/1/5/RCP_NM=" + RCP_PARTS;
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            while (parserEvent != XmlPullParser.END_DOCUMENT && check!=1) {
                System.out.println(parserEvent);
                System.out.println("here");
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
                        if (inMANUAL02) {
                            MANUAL02 = parser.getText();
                            inMANUAL02 = false;
                        }
                        if (inMANUAL03) {
                            MANUAL03 = parser.getText();
                            inMANUAL03 = false;
                        }
                        if (inINFO_ENG) {
                            INFO_ENG = parser.getText();
                            total_calories += Float.parseFloat(INFO_ENG);
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
                            ATT_FILE_NO_MK = selectedImagePath_breakfast;
                            System.out.println("sel " + selectedImagePath_breakfast);
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
                        if (inMANUAL08) {
                            MANUAL08 = parser.getText();
                            inMANUAL08 = false;
                        }
                        if (inMANUAL09) {
                            MANUAL09 = parser.getText();
                            inMANUAL09 = false;
                        }
                        if (inRCP_PARTS_DTLS) {
                            RCP_PARTS_DTLS = parser.getText();
                            inRCP_PARTS_DTLS = false;
                        }
                        if (inMANUAL_IMG01) {
                            MANUAL_IMG01 = parser.getText();
                            inMANUAL_IMG01 = false;
                        }
                        if (inRCP_NM) {
                            RCP_NM = breakfast;
                            System.out.println("name" + RCP_NM);
                            inRCP_NM = false;
                        }
                        if (inINFO_PRO) {
                            INFO_PRO = parser.getText();
                            System.out.println("protein"+INFO_PRO);
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
                        if (inMANUAL12) {
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
                        if (inMANUAL_IMG08) {
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
                            INFO_FAT = parser.getText();
                            inINFO_FAT = false;
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("row")) {

                            //SQlite 넣기


                            values.put("Rcp_nm", RCP_NM);
                            values.put("Rcp_parts_dtls", RCP_PARTS_DTLS);
                            values.put("Manual1", MANUAL01);
                            values.put("Manual2", MANUAL02);
                            values.put("Manual3", MANUAL03);
                            values.put("Manual4", MANUAL04);
                            values.put("Manual5", MANUAL05);
                            values.put("Manual6", MANUAL06);
                            values.put("Manual7", MANUAL07);
                            values.put("Manual8", MANUAL08);
                            values.put("Manual9", MANUAL09);
                            values.put("Manual10", MANUAL10);
                            values.put("Manual11", MANUAL11);
                            values.put("Manual12", MANUAL12);
                            values.put("Manual_img1", MANUAL_IMG01);
                            values.put("Manual_img2", MANUAL_IMG02);
                            values.put("Manual_img3", MANUAL_IMG03);
                            values.put("Manual_img4", MANUAL_IMG04);
                            values.put("Manual_img5", MANUAL_IMG05);
                            values.put("Manual_img6", MANUAL_IMG06);
                            values.put("Manual_img7", MANUAL_IMG07);
                            values.put("Manual_img8", MANUAL_IMG08);
                            values.put("Manual_img9", MANUAL_IMG09);
                            values.put("Manual_img10", MANUAL_IMG10);
                            values.put("Manual_img11", MANUAL_IMG11);
                            values.put("Manual_img12", MANUAL_IMG12);
                            values.put("Info_eng", INFO_ENG);
                            values.put("Info_pro", INFO_PRO);
                            values.put("Info_car", INFO_CAR);
                            values.put("Info_na", INFO_NA);
                            values.put("Info_Fat", INFO_FAT);
                            values.put("Att_file_no_mk", ATT_FILE_NO_MK);


                            db.insert("exercise", null, values);

                            System.out.println(ATT_FILE_NO_MK);

                            inrow = false;
                            check++;
                        }
                        break;
                }

                parserEvent = parser.next();

            }
        } catch (Exception e) {
//            text.setText("에러가..났습니다...");
        }



        //첫번째 data exercise db에 저장

        String imageUrl = ATT_FILE_NO_MK;
        list_itemArrayList = new ArrayList<Listviewitem>();

        listView = (ListView) findViewById(R.id.list);

        myListAdapter = new MyListAdapter(recommand.this, list_itemArrayList);
        listView.setAdapter(myListAdapter);


        String a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20;
        String sql = "select Att_file_no_mk, Rcp_nm, Info_eng, Info_car, Info_pro, Info_fat, Manual1, Manual2, Manual3, Manual4, Manual5 from exercise;";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            a1 = c.getString(0);
            a2 = c.getString(1);
            a3 = c.getString(2);
            a4 = c.getString(3);
            a5 = c.getString(4);
            a6 = c.getString(5);
            a7 = c.getString(6);
            a8 = c.getString(7);
            a9 = c.getString(8);
            a10 = c.getString(9);
            a11 = c.getString(10);
            list_itemArrayList.add(
                    new Listviewitem(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
            );
        }
        c.close();


    }

    void show_lunch() {
        if(list_itemArrayList!=null){
            list_itemArrayList.clear();
            myListAdapter.notifyDataSetChanged();
        }

        String str=lunch;
        int check=0;
        String RCP_PARTS = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String queryUrl = "http://openapi.foodsafetykorea.go.kr/api/" + key + "/COOKRCP01/xml/1/5/RCP_NM=" + RCP_PARTS;
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            while (parserEvent != XmlPullParser.END_DOCUMENT && check!=1) {
                System.out.println(parserEvent);
                System.out.println("here");
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
                        if (inMANUAL02) {
                            MANUAL02 = parser.getText();
                            inMANUAL02 = false;
                        }
                        if (inMANUAL03) {
                            MANUAL03 = parser.getText();
                            inMANUAL03 = false;
                        }
                        if (inINFO_ENG) {
                            INFO_ENG = parser.getText();
                            total_calories += Float.parseFloat(INFO_ENG);
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
                            ATT_FILE_NO_MK = selectedImagePath_lunch;
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
                        if (inMANUAL08) {
                            MANUAL08 = parser.getText();
                            inMANUAL08 = false;
                        }
                        if (inMANUAL09) {
                            MANUAL09 = parser.getText();
                            inMANUAL09 = false;
                        }
                        if (inRCP_PARTS_DTLS) {
                            RCP_PARTS_DTLS = parser.getText();
                            inRCP_PARTS_DTLS = false;
                        }
                        if (inMANUAL_IMG01) {
                            MANUAL_IMG01 = parser.getText();
                            inMANUAL_IMG01 = false;
                        }
                        if (inRCP_NM) {
                            RCP_NM = lunch;
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
                        if (inMANUAL12) {
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
                        if (inMANUAL_IMG08) {
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
                            INFO_FAT = parser.getText();
                            inINFO_FAT = false;
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("row")) {

                            //SQlite 넣기


                            values.put("Rcp_nm", RCP_NM);
                            values.put("Rcp_parts_dtls", RCP_PARTS_DTLS);
                            values.put("Manual1", MANUAL01);
                            values.put("Manual2", MANUAL02);
                            values.put("Manual3", MANUAL03);
                            values.put("Manual4", MANUAL04);
                            values.put("Manual5", MANUAL05);
                            values.put("Manual6", MANUAL06);
                            values.put("Manual7", MANUAL07);
                            values.put("Manual8", MANUAL08);
                            values.put("Manual9", MANUAL09);
                            values.put("Manual10", MANUAL10);
                            values.put("Manual11", MANUAL11);
                            values.put("Manual12", MANUAL12);
                            values.put("Manual_img1", MANUAL_IMG01);
                            values.put("Manual_img2", MANUAL_IMG02);
                            values.put("Manual_img3", MANUAL_IMG03);
                            values.put("Manual_img4", MANUAL_IMG04);
                            values.put("Manual_img5", MANUAL_IMG05);
                            values.put("Manual_img6", MANUAL_IMG06);
                            values.put("Manual_img7", MANUAL_IMG07);
                            values.put("Manual_img8", MANUAL_IMG08);
                            values.put("Manual_img9", MANUAL_IMG09);
                            values.put("Manual_img10", MANUAL_IMG10);
                            values.put("Manual_img11", MANUAL_IMG11);
                            values.put("Manual_img12", MANUAL_IMG12);
                            values.put("Info_eng", INFO_ENG);
                            values.put("Info_pro", INFO_PRO);
                            values.put("Info_car", INFO_CAR);
                            values.put("Info_na", INFO_NA);
                            values.put("Info_Fat", INFO_FAT);
                            values.put("Att_file_no_mk", ATT_FILE_NO_MK);


                            db.insert("exercise", null, values);

                            System.out.println(ATT_FILE_NO_MK);

                            inrow = false;
                            check++;
                        }
                        break;
                }

                parserEvent = parser.next();

            }
        } catch (Exception e) {
//            text.setText("에러가..났습니다...");
        }



        //첫번째 data exercise db에 저장

        String imageUrl = ATT_FILE_NO_MK;
        list_itemArrayList = new ArrayList<Listviewitem>();
        listView = (ListView) findViewById(R.id.list);

        myListAdapter = new MyListAdapter(recommand.this, list_itemArrayList);
        listView.setAdapter(myListAdapter);


        String a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20;
        String sql = "select Att_file_no_mk, Rcp_nm, Info_eng, Info_car, Info_pro, Info_fat, Manual1, Manual2, Manual3, Manual4, Manual5 from exercise;";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            a1 = c.getString(0);
            a2 = c.getString(1);
            a3 = c.getString(2);
            a4 = c.getString(3);
            a5 = c.getString(4);
            a6 = c.getString(5);
            a7 = c.getString(6);
            a8 = c.getString(7);
            a9 = c.getString(8);
            a10 = c.getString(9);
            a11 = c.getString(10);
            list_itemArrayList.add(
                    new Listviewitem(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
            );
        }
        c.close();


    }

    void show_dinner() {
        if(list_itemArrayList!=null){
            list_itemArrayList.clear();
            myListAdapter.notifyDataSetChanged();
        }
        String str=dinner;
        int check=0;
        String RCP_PARTS = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String queryUrl = "http://openapi.foodsafetykorea.go.kr/api/" + key + "/COOKRCP01/xml/1/5/RCP_NM=" + RCP_PARTS;
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            while (parserEvent != XmlPullParser.END_DOCUMENT && check!=1) {
                System.out.println(parserEvent);
                System.out.println("here");
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
                        if (inMANUAL02) {
                            MANUAL02 = parser.getText();
                            inMANUAL02 = false;
                        }
                        if (inMANUAL03) {
                            MANUAL03 = parser.getText();
                            inMANUAL03 = false;
                        }
                        if (inINFO_ENG) {
                            INFO_ENG = parser.getText();
                            total_calories += Float.parseFloat(INFO_ENG);
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
                            ATT_FILE_NO_MK = selectedImagePath_dinner;
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
                        if (inMANUAL08) {
                            MANUAL08 = parser.getText();
                            inMANUAL08 = false;
                        }
                        if (inMANUAL09) {
                            MANUAL09 = parser.getText();
                            inMANUAL09 = false;
                        }
                        if (inRCP_PARTS_DTLS) {
                            RCP_PARTS_DTLS = parser.getText();
                            inRCP_PARTS_DTLS = false;
                        }
                        if (inMANUAL_IMG01) {
                            MANUAL_IMG01 = parser.getText();
                            inMANUAL_IMG01 = false;
                        }
                        if (inRCP_NM) {
                            RCP_NM = dinner;
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
                        if (inMANUAL12) {
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
                        if (inMANUAL_IMG08) {
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
                            INFO_FAT = parser.getText();
                            inINFO_FAT = false;
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("row")) {

                            //SQlite 넣기
                            values.put("Rcp_nm", RCP_NM);
                            values.put("Rcp_parts_dtls", RCP_PARTS_DTLS);
                            values.put("Manual1", MANUAL01);
                            values.put("Manual2", MANUAL02);
                            values.put("Manual3", MANUAL03);
                            values.put("Manual4", MANUAL04);
                            values.put("Manual5", MANUAL05);
                            values.put("Manual6", MANUAL06);
                            values.put("Manual7", MANUAL07);
                            values.put("Manual8", MANUAL08);
                            values.put("Manual9", MANUAL09);
                            values.put("Manual10", MANUAL10);
                            values.put("Manual11", MANUAL11);
                            values.put("Manual12", MANUAL12);
                            values.put("Manual_img1", MANUAL_IMG01);
                            values.put("Manual_img2", MANUAL_IMG02);
                            values.put("Manual_img3", MANUAL_IMG03);
                            values.put("Manual_img4", MANUAL_IMG04);
                            values.put("Manual_img5", MANUAL_IMG05);
                            values.put("Manual_img6", MANUAL_IMG06);
                            values.put("Manual_img7", MANUAL_IMG07);
                            values.put("Manual_img8", MANUAL_IMG08);
                            values.put("Manual_img9", MANUAL_IMG09);
                            values.put("Manual_img10", MANUAL_IMG10);
                            values.put("Manual_img11", MANUAL_IMG11);
                            values.put("Manual_img12", MANUAL_IMG12);
                            values.put("Info_eng", INFO_ENG);
                            values.put("Info_pro", INFO_PRO);
                            values.put("Info_car", INFO_CAR);
                            values.put("Info_na", INFO_NA);
                            values.put("Info_Fat", INFO_FAT);
                            values.put("Att_file_no_mk", ATT_FILE_NO_MK);


                            db.insert("exercise", null, values);

                            System.out.println(ATT_FILE_NO_MK);

                            inrow = false;
                            check++;
                        }
                        break;
                }

                parserEvent = parser.next();

            }
        } catch (Exception e) {
//            text.setText("에러가..났습니다...");
        }



        //첫번째 data exercise db에 저장

        String imageUrl = ATT_FILE_NO_MK;
        list_itemArrayList = new ArrayList<Listviewitem>();
        listView = (ListView) findViewById(R.id.list);

        myListAdapter = new MyListAdapter(recommand.this, list_itemArrayList);
        listView.setAdapter(myListAdapter);


        String a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20;
        String sql = "select Att_file_no_mk, Rcp_nm, Info_eng, Info_car, Info_pro, Info_fat, Manual1, Manual2, Manual3, Manual4, Manual5 from exercise;";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            a1 = c.getString(0);
            a2 = c.getString(1);
            a3 = c.getString(2);
            a4 = c.getString(3);
            a5 = c.getString(4);
            a6 = c.getString(5);
            a7 = c.getString(6);
            a8 = c.getString(7);
            a9 = c.getString(8);
            a10 = c.getString(9);
            a11 = c.getString(10);
            list_itemArrayList.add(
                    new Listviewitem(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
            );
        }
        c.close();


    }




}