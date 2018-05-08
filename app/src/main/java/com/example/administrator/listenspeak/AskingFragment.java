package com.example.administrator.listenspeak;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

public class AskingFragment extends  android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    public TextView Time_text;
    public Button but_save, but_stop;
    public ImageView said;

    private File myRecAudioFile;
    /**
     * 录音保存路径
     **/
    private File myRecAudioDir;
    private File myshareDir;
    private File myPlayFile;
    private MediaRecorder mMediaRecorder01;
    private int mMinute;
    private boolean xx = true;
    /**
     * 存放音频文件列表
     **/

    /**
     * 文件存在
     **/
    private boolean sdcardExit;
    /**
     * 是否停止录音
     **/
    private boolean isStopRecord;
    private String length1 = null;
    private final String SUFFIX = ".amr";
    /**
     * 记录需要合成的几段amr语音文件
     **/
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> recordFiles = new ArrayList<String>();
    //Time
    int second = 0;
    int minute = 0;
    /**
     * 计时器
     **/
    Timer timer;
    /**
     * 是否暂停标志位
     **/
    private boolean isPause;
    private boolean isSave;
    /**
     * 在暂停状态中
     **/
    private boolean inThePause;
    public AskingFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static AskingFragment newInstance(String param1, String param2) {
        AskingFragment fragment = new AskingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asking, container, false);
    }
    @SuppressLint("CutPasteId")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // final User user ;
         Time_text = getActivity().findViewById(R.id.time_text);
         but_stop = getActivity().findViewById(R.id.but_stop_said);
         said = getActivity().findViewById(R.id.img_said);
         but_save = getActivity().findViewById(R.id.but_ok);


        but_stop.setEnabled(false);
        but_save.setEnabled(false);

        myPlayFile = null;
        // 判断sd Card是否插入
        sdcardExit = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        // 取得sd card路径作为录音文件的位置
        if (sdcardExit) {
            String pathStr = Environment.getExternalStorageDirectory().getPath() + "/Listen";
            String pathStr1 = Environment.getExternalStorageDirectory().getPath() + "/Share";
            myRecAudioDir = new File(pathStr);
            myshareDir= new File(pathStr1);
            if (!myRecAudioDir.exists()) {
                myRecAudioDir.mkdirs();
                Log.v("录音", "创建录音文件！" + myRecAudioDir.exists());
            }
            if (!myshareDir.exists()) {
                myshareDir.mkdirs();
                Log.v("录音", "创建录音文件！" + myshareDir.exists());
            }
        }
        getRecordFiles();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, recordFiles);

        said.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                second = 0;
                minute = 0;
                list.clear();
                start();
            }
        });
        but_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPause = true;
                //已经暂停过了，再次点击按钮 开始录音，录音状态在录音中
                if (inThePause) {
                    but_stop.setText("Stop");
                    start();
                    inThePause = false;
                }
                //正在录音，点击暂停,现在录音状态为暂停
                else {
                    //当前正在录音的文件名，全程
                    list.add(myRecAudioFile.getPath());
                    inThePause = true;
                    recodeStop();
                    //start();
                    but_stop.setText("Continue");
                    //计时停止
                    timer.cancel();
                }
            }
        });

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xx = false;
                timer.cancel();
                //这里写暂停处理的 文件！加上list里面 语音合成起来
                if (isPause) {
                    //在暂停状态按下结束键,处理list就可以了
                    if (inThePause) {
                        getInputCollection(list, false);
                        Time_text.setText("Recording 00:00");
                    }
                    //在正在录音时，处理list里面的和正在录音的语音
                    else {
                        list.add(myRecAudioFile.getPath());
                        recodeStop();
                        getInputCollection(list, true);
                    }
                    //还原标志位
                    isPause = false;
                    inThePause = false;
                    but_stop.setText("pause");
                }
                //若录音没有经过任何暂停
                else {
                    if (myRecAudioFile != null) {
                        // 停止录音
                        mMediaRecorder01.stop();
                        mMediaRecorder01.release();
                        mMediaRecorder01 = null;
                        // 将录音频文件给Adapter
                        adapter.add(myRecAudioFile.getName());
                        DecimalFormat df = new DecimalFormat("#.000");
                        if (myRecAudioFile.length() <= 1024 * 1024) {
                            length1 = df.format(myRecAudioFile.length() / 1024.0) + "K";
                        } else {
                            length1 = df.format(myRecAudioFile.length() / 1024.0 / 1024) + "M";
                        }
                        System.out.println(length1);
                        Time_text.setText("Recording 00:00");
                    }
                }
                //停止录音了
                isStopRecord = true;
                if (isStopRecord = true) {
                    Intent intent = new Intent(getActivity(), SaveActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    //停止录音
    protected void recodeStop() {
        if (mMediaRecorder01 != null && !isStopRecord) {
            // 停止录音
            mMediaRecorder01.stop();
            mMediaRecorder01.release();
            mMediaRecorder01 = null;
        }
        timer.cancel();
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

    private void start() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                second++;
                if (second >= 60) {
                    second = 0;
                    minute++;
                }
                handler.sendEmptyMessage(1);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 1000);
        try {
            if (!sdcardExit) {
                Toast.makeText(getActivity(), "请插入SD card",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String mMinute1 = getTime();
            Toast.makeText(getActivity(), "当前时间是:" + mMinute1, Toast.LENGTH_LONG).show();

            // 创建音频文件
            if(isSave == true) {

                myRecAudioFile = new File(myRecAudioDir, mMinute1 + SUFFIX);
                mMediaRecorder01 = new MediaRecorder();

                // 设置录音为麦克风
                mMediaRecorder01.setAudioSource(MediaRecorder.AudioSource.MIC);
                mMediaRecorder01.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                mMediaRecorder01.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                //录音文件保存这里
                mMediaRecorder01.setOutputFile(myRecAudioFile.getAbsolutePath());
                mMediaRecorder01.prepare();
                mMediaRecorder01.start();
            }else if(isSave == false){
                myRecAudioFile = new File(myshareDir, mMinute1 + SUFFIX);
                mMediaRecorder01 = new MediaRecorder();

                // 设置录音为麦克风
                mMediaRecorder01.setAudioSource(MediaRecorder.AudioSource.MIC);
                mMediaRecorder01.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                mMediaRecorder01.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                //录音文件保存这里
                mMediaRecorder01.setOutputFile(myRecAudioFile.getAbsolutePath());
                mMediaRecorder01.prepare();
                mMediaRecorder01.start();
            }

            mMediaRecorder01.setOnInfoListener(new MediaRecorder.OnInfoListener() {

                @SuppressLint("WrongConstant")
                @Override
                public void onInfo(MediaRecorder mr, int what, int extra) {
                    // TODO Auto-generated method stub
                    int a = mr.getMaxAmplitude();
                    Toast.makeText(getActivity(), a, 500).show();
                }
            });
            Time_text.setText("Recording......");
            but_stop.setEnabled(true);
            but_save.setEnabled(true);
            isStopRecord = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取系统时间
    public String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH：mm：ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        System.out.println("当前时间");
        return time;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Time_text.setText("Recording：" + minute + ":" + second);
        }
    };


    public void getInputCollection(List list, boolean isAddLastRecord) {
        String mMinute1 = getTime();
        Toast.makeText(getActivity(), "当前时间是:" + mMinute1, Toast.LENGTH_LONG).show();

        // 创建音频文件,合并的文件放这里
        File file1 = new File(myRecAudioDir, mMinute1 + SUFFIX);
        FileOutputStream fileOutputStream = null;

        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream = new FileOutputStream(file1);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //list里面为暂停录音 所产生的 几段录音文件的名字，中间几段文件的减去前面的6个字节头文件

        for (int i = 0; i < list.size(); i++) {
            File file = new File((String) list.get(i));
            Log.d("list的长度", list.size() + "");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] myByte = new byte[fileInputStream.available()];
                //文件长度
                int length = myByte.length;

                //头文件
                if (i == 0) {
                    while (fileInputStream.read(myByte) != -1) {
                        fileOutputStream.write(myByte, 0, length);
                    }
                }

                //之后的文件，去掉头文件就可以了
                else {
                    while (fileInputStream.read(myByte) != -1) {

                        fileOutputStream.write(myByte, 6, length - 6);
                    }
                }
                fileOutputStream.flush();
                fileInputStream.close();
                System.out.println("合成文件长度：" + file1.length());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //结束后关闭流
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //合成一个文件后，删除之前暂停录音所保存的零碎合成文件
        deleteListRecord(isAddLastRecord);
        //
        adapter.add(file1.getName());
    }

    private void deleteListRecord(boolean isAddLastRecord) {
        for (int i = 0; i < list.size(); i++) {
            File file = new File((String) list.get(i));
            if (file.exists()) {
                file.delete();
            }
        }
        //正在暂停后，继续录音的这一段音频文件
        if (isAddLastRecord) {
            myRecAudioFile.delete();
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
