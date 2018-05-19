package com.example.administrator.listenspeak;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PoolActivity extends AppCompatActivity {
public TextView usernname,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool);

        usernname= findViewById(R.id.world_name);
        time = findViewById(R.id.world_time);

        SimpleDateFormat formatter =new SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        time.setText(str);

        SharedPreferences sharedPreferences1= getSharedPreferences("text", PoolActivity.MODE_PRIVATE);
        String email = sharedPreferences1.getString("email", "");
        usernname.setText(email);
    }
}
