package com.example.administrator.listenspeak;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity {
    //The arrayList to populate the listView
    private boolean sdcardExit;
    private File myRecAudioDir;
    private File myPlayFile;
    public ListView listView;
    /**按钮背景图片的标志位**/
    private boolean sigle = false;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> recordFiles = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        // 判断sd Card是否插入
        sdcardExit = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        // 取得sd card路径作为录音文件的位置
        if (sdcardExit) {
            String pathStr = Environment.getExternalStorageDirectory().getPath() + "/Listen";
            myRecAudioDir = new File(pathStr);
            Log.d("TAG", pathStr);
        }
        //Todo: The following is just for a demo purpose

        myPlayFile=null;
        // 取得sd card 目录里的.arm文件
        getRecordFiles();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recordFiles);

        //The last step is to connect the listView and the Java class and to populate the list View using the ArrayAdapter
        listView = findViewById(R.id.listView_history);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(CollectionActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });
    }

    //获取文件
    private void getRecordFiles() {
        // TODO Auto-generated method stub
        recordFiles = new ArrayList<String>();
        if (sdcardExit) {
            File files[] = myRecAudioDir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().indexOf(".") >= 0) { // 只取.amr 文件
                        String fileS = files[i].getName().substring(
                                files[i].getName().indexOf("."));
                        if (fileS.toLowerCase().equals(".mp3")
                                || fileS.toLowerCase().equals(".amr")
                                || fileS.toLowerCase().equals(".mp4"))
                            recordFiles.add(files[i].getName());
                    }
                }
            }
        }
    }
    /* Buttons */

}
