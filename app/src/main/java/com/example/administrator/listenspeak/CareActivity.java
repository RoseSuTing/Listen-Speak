package com.example.administrator.listenspeak;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CareActivity extends AppCompatActivity {
public ListView listView;
    private CareAdapter careAdapter;
    public ArrayList<ImageUser> userList = new ArrayList<ImageUser>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care);
        listView= findViewById(R.id.care_listview);
        careAdapter= new CareAdapter(
                        this,
                         R.layout.layout4_care, // the layout for each item in the list
                         userList ); // the arrayList to feed the arrayAdapter
        final int[] img = new int[]{R.drawable.ic_dashboard_black_24dp,
                R.drawable.image1, R.drawable.image2, R.drawable.image4, R.drawable.image3,
                R.drawable.image5, R.drawable.image6, R.drawable.image6, R.drawable.image7};
        for (int i = 0; i < img.length; i++) {
            userList.add(new ImageUser(img[i]));
        }
        listView.setAdapter(careAdapter);
    }
}
