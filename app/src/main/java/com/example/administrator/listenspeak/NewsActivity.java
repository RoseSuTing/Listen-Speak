package com.example.administrator.listenspeak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {
public ImageView img;
public TextView big_text,small_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        img= findViewById(R.id.news_imag);
        big_text= findViewById(R.id.big_title);
        small_text=findViewById(R.id.small_tile);
        init();
    }
    public void init(){
        Intent intent =  getIntent();
        int position = intent.getIntExtra("position",-1);
        if(position == 0) {
            img.setImageResource(R.drawable.user);
            big_text.setText("我喜欢你，但你千万别喜欢我");
            small_text.setText("被囚禁在单相思的性单恋者");
        }
        else  if(position == 1) {
            img.setImageResource(R.drawable.user1);
            big_text.setText("我终于知道曲终人散的寂寞");
            small_text.setText("被囚禁在单相思的性单恋者");
        }
        else if(position == 2){
            img.setImageResource(R.drawable.user2);
            big_text.setText("我喜欢你，但你千万别喜欢我");
            small_text.setText("被囚禁在单相思的性单恋者");}
        else if(position == 3){
            img.setImageResource(R.drawable.user3);
            big_text.setText("我喜欢你，但你千万别喜欢我");
            small_text.setText("被囚禁在单相思的性单恋者");}
        else if(position == 4){
            img.setImageResource(R.drawable.user4);
            big_text.setText("我喜欢你，但你千万别喜欢我");
            small_text.setText("被囚禁在单相思的性单恋者");}
    }
}
