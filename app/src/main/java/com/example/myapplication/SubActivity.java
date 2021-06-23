package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class SubActivity extends Activity {

    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Intent intent = getIntent();
        ImageView  Att_file_no_mk= (ImageView) findViewById(R.id.mainimg);
        TextView Rcp_nm=(TextView) findViewById(R.id.rcp_nm);
        TextView Info_eng=(TextView)findViewById(R.id.info_eng);
        TextView Info_car=(TextView)findViewById(R.id.info_car);
        TextView Info_pro=(TextView)findViewById(R.id.info_pro);
        TextView Info_fat=(TextView)findViewById(R.id.info_fat);
        TextView Manual1=(TextView)findViewById(R.id.manual1);
        TextView Manual2=(TextView)findViewById(R.id.manual2);
        TextView Manual3=(TextView)findViewById(R.id.manual3);
        TextView Manual4=(TextView)findViewById(R.id.manual4);
        TextView Manual5=(TextView)findViewById(R.id.manual5);



        img=intent.getStringExtra("Att_file_no_mk");
        Glide.with(this).load(img).into(Att_file_no_mk);

        Rcp_nm.setText( intent.getStringExtra("Rcp_nm"));
        Info_eng.setText("Calories : "+intent.getStringExtra("Info_eng") +"Kcal");
        Info_car.setText("Carbohydrate : "+intent.getStringExtra("Info_car")+"g");
        Info_pro.setText("Protein : "+intent.getStringExtra("Info_pro")+"g");
        Info_fat.setText("Fat : "+intent.getStringExtra("Info_fat")+"g");
        Manual1.setText(intent.getStringExtra("Manual1"));
        Manual2.setText(intent.getStringExtra("Manual2"));
        Manual3.setText(intent.getStringExtra("Manual3"));
        Manual4.setText(intent.getStringExtra("Manual4"));
        Manual5.setText(intent.getStringExtra("Manual5"));

    }
}
