package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;



public class MyListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Listviewitem> list_itemArrayList;

    ViewHolder viewholder;

    class ViewHolder{
        ImageView profile_imageView;
        TextView content_textView1;
        TextView content_textView2;
        TextView content_textView3;
        TextView content_textView4;
        TextView content_textView5;

    }





    public MyListAdapter(Context context, ArrayList<Listviewitem> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }


    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemArrayList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.show_list,null);
            viewholder=new ViewHolder();

            viewholder.content_textView1 = (TextView)convertView.findViewById(R.id.text1);
            viewholder.content_textView2 = (TextView)convertView.findViewById(R.id.text2);
            viewholder.content_textView3 = (TextView)convertView.findViewById(R.id.text3);
            viewholder.content_textView4= (TextView)convertView.findViewById(R.id.text4);
            viewholder.content_textView5 = (TextView)convertView.findViewById(R.id.text5);
            viewholder.profile_imageView = (ImageView)convertView.findViewById(R.id.image1);
            convertView.setTag(viewholder);
        }
        else{
            viewholder=(ViewHolder)convertView.getTag();

        }
        viewholder.content_textView1.setText(list_itemArrayList.get(position).getRcp_nm());
        viewholder.content_textView2.setText(list_itemArrayList.get(position).getInfo_eng() +"Kcal");
        viewholder.content_textView3.setText(list_itemArrayList.get(position).getInfo_car() +"g");
        viewholder.content_textView4.setText(list_itemArrayList.get(position).getInfo_pro()+"g");
        viewholder.content_textView5.setText(list_itemArrayList.get(position).getInfo_fat()+"g");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(false);
        requestOptions.centerCrop();
        requestOptions.circleCrop();
        requestOptions.signature(new ObjectKey(System.currentTimeMillis()));

        RequestOptions circle = new RequestOptions().centerCrop().circleCrop();
        Glide.with(context).load(list_itemArrayList.get(position).getAtt_file_no_mk()).apply(circle).into(viewholder.profile_imageView);

        return convertView;
    }
}
