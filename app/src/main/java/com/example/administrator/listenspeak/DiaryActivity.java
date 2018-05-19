package com.example.administrator.listenspeak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {
    public ArrayList<Diary> studentsList = new ArrayList<Diary>();
    private ListView listview;
    public ImageView write;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        listview= findViewById(R.id.dia_listview);
        write = findViewById(R.id.write);

        DiarAdapter diarAdapter=
                new DiarAdapter(
                        getBaseContext(), // Current context
                        R.layout.layout4_diary, // the layout for each item in the list
                        studentsList); // the arrayList to feed the arrayAdapter

        final int[] img = new int[]{R.drawable.ic_launcher_background, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
        String[] month = new String[]{"20", "12", "16", "17", "23", "21", "27"};
        String[] week = new String[]{"周一", "周三", "周二", "周四", "周六", "周天", "周五"};
        String[] time = new String[]{"11:00", "13:22", "19:34", "05:22", "23:58", "17:05", "03:00"};
        String[] Title = new String[]{"逛街", "傲慢与偏见", "时间简史", "金枝", "火锅", "无邪", "等会"};
        for (int i = 0; i < img.length; i++) {
            studentsList.add(new Diary(month[i],week[i],time[i],Title[i],img[i]));
        }
        listview.setAdapter(diarAdapter);
        diarAdapter.setDefSelect(0);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this,WriteActivity.class);
                startActivity(intent);
            }
        });
    }
}
