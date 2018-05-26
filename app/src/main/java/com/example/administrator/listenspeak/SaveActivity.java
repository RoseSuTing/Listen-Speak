package com.example.administrator.listenspeak;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.graphics.ImageFormat.JPEG;

public class SaveActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        ViewSwitcher.ViewFactory {
// 声明ImageSwitcher
     private android.widget.ImageSwitcher mSwitcher;
     private EditText title;
    private Button bt_ok;
    private ViewPager vp;
    int position_i;
    // 图片数组ID
     private Integer[] mThumbIds  = { R.drawable.image1, R.drawable.image2,
            R.drawable.image3, R.drawable.image4, R.drawable.image5,
            R.drawable.image6, R.drawable.image7 };
       //大图片源数组
    private Integer[] mImageIds = {R.drawable.image1, R.drawable.image2,
            R.drawable.image3, R.drawable.image4, R.drawable.image5,
            R.drawable.image6, R.drawable.image7  };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_save);

         mSwitcher =  findViewById(R.id.switcher);
         bt_ok=findViewById(R.id.ok);


        //注意在使用一个ImageSwitcher之前，
        //一定要调用setFactory方法，要不setImageResource这个方法会报空指针异常。
        mSwitcher.setFactory( SaveActivity.this);
        //设置动画效果
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right));

        android.widget.Gallery g =  findViewById(R.id.gallery);

        //添加OnItemSelectedListener监听器
        g.setAdapter(new ImageAdapter(this));
        g.setOnItemSelectedListener( this);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(SaveActivity.this, MainActivity.class);
                //一定要指定是第几个pager，因为要跳到ThreeFragment，这里填写2
                i.putExtra("id",2);
                startActivity(i);
                Calendar now = new GregorianCalendar();
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                String fileName = simpleDate.format(now.getTime());

              //  saveBitmapToLocal(fileName,mThumbIds[position_i]);
            }
        });
       }

    //创建内部类ImageAdapter
    public class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }
        public int getCount() {
            return mThumbIds.length;
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);

            i.setImageResource(mThumbIds[position]);
            SharedPreferences.Editor mySharedPreferences = getSharedPreferences("ID", MODE_PRIVATE).edit();
            mySharedPreferences.putInt("id",position);
            mySharedPreferences.commit();
            //设置边界对齐
            i.setAdjustViewBounds(true);
            //设置布局参数
            i.setLayoutParams(new Gallery.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            //设置背景资源
            i.setBackgroundResource(R.drawable.register);
            return i;
        }
        private Context mContext;
    }

    @Override
    //实现onItemSelected()方法，更换图片
    public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
        //设置图片资源
        mSwitcher.setImageResource(mImageIds[position]);
        position_i= position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) { }

    @Override
    //实现makeView()方法，为ImageView设置布局格式
    public View makeView() {
        ImageView i = new ImageView(this);
        //设置背景颜色
        i.setBackgroundColor(0xFF000000);
        //设置比例类型
        i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //设置布局参数
        i.setLayoutParams(new ImageSwitcher.LayoutParams(
                ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT));
        return i;
    }

    protected void onResume() {
        int id = getIntent().getIntExtra("id", 0);
        if (id == 2) {
            android.support.v4.app.Fragment fragmen = new android.support.v4.app.Fragment();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.viewpager, fragmen);
            transaction.commit();
            vp.setCurrentItem(2);//
            //帮助跳转到指定子fragment
            Intent i=new Intent();
            i.setClass(SaveActivity.this,ListenFragment.class);
            i.putExtra("id",2);
        }
        super.onResume();
    }

    public static void saveBitmapToLocal(String fileName, Bitmap bitmap) throws FileNotFoundException {
        try {
            String pathStr = Environment.getExternalStorageDirectory().getPath() + "/Picture";
            File file = new File(pathStr,fileName);
            File FileParent = file.getParentFile();
            if(!FileParent.exists())
            {
                FileParent.mkdirs();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));

        } catch(Exception e)
        {
            e.printStackTrace();

        }

    }
}
