package com.example.administrator.listenspeak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager vp;
    private int[] imageResIds; //存放图片资源id的数组
    private ArrayList<ImageView> imageViews; //存放图片的集合
    private int lastPosition;
    private boolean isRunning = false; //viewpager是否在自动轮询
    private MyPagerAdapter adapter = new MyPagerAdapter();
    public ListView listView_topic;
    public ArrayList<Topic> studentsList = new ArrayList<Topic>();
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        listView_topic = findViewById(R.id.listview_topic);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Retrieve the parcelable
            Feedback feedback = bundle.getParcelable("feedback");
            // Get the from the object
            String userName = feedback.getName();
            TextView display = findViewById(R.id.display);
            display.setVisibility(View.VISIBLE);
            String prompt = userName.substring(0, 1).toUpperCase() + userName.substring(1) + " " + getString(R.string.account_created);
            display.setText(prompt);
        }

        initViews();
        initData();
        initAdapter();
        //开启图片的自动轮询
        new

                Thread() {
                    @Override
                    public void run() {
                        isRunning = true;
                        while (isRunning) {
                            try {
                                Thread.sleep(7000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() { //在子线程中开启子线程
                                    //往下翻一页（setCurrentItem方法用来设置ViewPager的当前页）
                                    vp.setCurrentItem(vp.getCurrentItem() + 1);
                                }
                            });
                        }
                    }
                }.start();

        MyAdapter myAdapter =
                new MyAdapter(
                        getBaseContext(), // Current context
                        R.layout.layout2, // the layout for each item in the list
                        studentsList); // the arrayList to feed the arrayAdapter

        final int[] img = new int[]{R.drawable.user1, R.drawable.user2, R.drawable.user3, R.drawable.user4, R.drawable.user5, R.drawable.user6, R.drawable.user7};
        String[] text1 = new String[]{"一枝花", "一种相思", "月里湾", "邓飞", "新歌", "小洁洁", "露露"};
        String[] topic = new String[]{"外表汉子，内心妹子","倾城四海","想和你一起并肩看夕阳","我懂得你要说什么","山有木兮卿有你","一身诗意","关于你的传言"};
        for (int i = 0; i < img.length; i++) {
            studentsList.add(new Topic(text1[i],topic[i],img[i]));
        }
        listView_topic.setAdapter(myAdapter);

    }
    /*
   初始化视图
     */
    private void initViews() {
        //初始化ViewPager控件
        vp = (ViewPager) findViewById(R.id.vp);
        //设置ViewPager的滚动监听
        vp.setOnPageChangeListener(this);
    }
    /*
      初始化数据
     */
    private void initData() {
        //初始化填充ViewPager的图片资源
        imageResIds = new int[]{R.drawable.user1,R.drawable.user2,R.drawable.user3,R.drawable.user4,R.drawable.user5};
        //保存图片资源的集合
        imageViews = new ArrayList<>();
        ImageView imageView;
        View pointView;
        //循环遍历图片资源，然后保存到集合中
        for (int i = 0; i < imageResIds.length; i++){
            //添加图片到集合中
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResIds[i]);
            imageViews.add(imageView);

            //加小白点，指示器（这里的小圆点定义在了drawable下的选择器中了，也可以用小图片代替）

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(8, 8);
            if (i != 0){
                //如果不是第一个点，则设置点的左边距
                layoutParams.leftMargin = 10;
            }

        }
    }
    /*
       初始化适配器
      */
    private void initAdapter() {
        lastPosition = 0; //设置之前的位置为第一个
        vp.setAdapter(adapter);
        //设置默认显示中间的某个位置（这样可以左右滑动），这个数只有在整数范围内，可以随便设置
        vp.setCurrentItem(5000000); //显示5000000这个位置的图片
    }

    //界面销毁时，停止viewpager的轮询
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    /*
      自定义适配器，继承自PagerAdapter
     */
    class MyPagerAdapter extends PagerAdapter {

        //返回显示数据的总条数，为了实现无限循环，把返回的值设置为最大整数
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        //指定复用的判断逻辑，固定写法：view == object
        @Override
        public boolean isViewFromObject(View view, Object object) {
            //当创建新的条目，又反回来，判断view是否可以被复用(即是否存在)
            return view == object;
        }
        //返回要显示的条目内容
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            //container  容器  相当于用来存放imageView
            //从集合中获得图片
            final int newPosition = position % 5; //数组中总共有5张图片，超过数组长度时，取摸，防止下标越界
            ImageView imageView = imageViews.get(newPosition);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TopicActivity.this,NewsActivity.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }
            });
            //把图片添加到container中
            container.addView(imageView);
            //把图片返回给框架，用来缓存
            return imageView;
        }

        //销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //object:刚才创建的对象，即要销毁的对象
            container.removeView((View) object);
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//当前的位置可能很大，为了防止下标越界，对要显示的图片的总数进行取余
        int newPosition = position % 5;
        //设置描述信息
        lastPosition = newPosition; //记录之前的点
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
