package com.example.administrator.listenspeak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class KindActivity extends AppCompatActivity {
private ImageView first,second,third,fourth,sixth,eighth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind);

        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
        sixth = findViewById(R.id.sixth);
        eighth = findViewById(R.id.eighth);
        Button sure = findViewById(R.id.sure);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               first.setImageResource(R.drawable.boder);
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                second.setImageResource(R.drawable.boder);
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                third.setImageResource(R.drawable.boder);
            }
        });
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourth.setImageResource(R.drawable.boder);
            }
        });

        sixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sixth.setImageResource(R.drawable.boder);
            }
        });
        eighth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eighth.setImageResource(R.drawable.boder);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KindActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
