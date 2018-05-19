package com.example.administrator.listenspeak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class LikeActivity extends AppCompatActivity {

    public ArrayList<User> UserList = new ArrayList<User>();
    private ListView listview;
    public ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        listview = findViewById(R.id.listview_like);
        imageView=findViewById(R.id.like_topic);
        ListenAdapter listenAdapter =
                new ListenAdapter(
                        this, // Current context
                        R.layout.layout, // the layout for each item in the list
                        UserList); // the arrayList to feed the arrayAdapter
        listview.setAdapter(listenAdapter);
        //启用监听器
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LikeActivity.this,PersonActivity.class);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeActivity.this,TopicActivity.class);
                startActivity(intent);
            }
        });
    }
}
