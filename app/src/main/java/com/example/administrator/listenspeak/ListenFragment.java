package com.example.administrator.listenspeak;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class ListenFragment extends  android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private ListenAdapter listenAdapter;
    public ArrayList<User> UserList = new ArrayList<User>();
    private ListView listview;
    private ImageView play;
    private boolean sigle = false;
    private boolean sdcardExit;
    private File myRecAudioDir;
    private File myPlayFile;
    private ViewPager vp;
    public ListenFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ListenFragment newInstance(String param1, String param2) {
        ListenFragment fragment = new ListenFragment();
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
        return inflater.inflate(R.layout.fragment_listen, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listview=getActivity().findViewById(R.id.listview);
        play = getActivity().findViewById(R.id.play);

        // 判断sd Card是否插入
        sdcardExit = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        // 取得sd card路径作为录音文件的位置
        if (sdcardExit) {
            String pathStr = Environment.getExternalStorageDirectory().getPath() + "/Listen";
            myRecAudioDir = new File(pathStr);
            Log.d("TAG", pathStr);
        }
        ListenAdapter listenAdapter =
                new ListenAdapter(
                        getActivity(), // Current context
                        R.layout.layout, // the layout for each item in the list
                        UserList); // the arrayList to feed the arrayAdapter

        myPlayFile=null;
        getRecordFiles();
        listview.setAdapter(listenAdapter);

        /*// 播放
        play.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigle = true;
                // TODO Auto-generated method stub
                if (myPlayFile != null && myPlayFile.exists()) {
                    // 打开播放程序
                    openFile(myPlayFile);
                } else {
                    Toast.makeText(getActivity(), "你选的是一个空文件", Toast.LENGTH_LONG).show();
                    Log.d("没有选择文件","没有选择文件");
                }
            }
        });*/
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 当有单点击文件名时将删除按钮及播放按钮Enable
                play.setEnabled(true);
                //delete.setEnabled(true);
                myPlayFile = new File(myRecAudioDir.getAbsolutePath() + File.separator + ((TextView) arg1).getText().toString());
                DecimalFormat df = new DecimalFormat("#.000");

                //myTextView.setText("你选的是" + ((TextView) arg1).getText().toString());
                Toast.makeText(getActivity(),"你选的是" + (((TextView) arg1).getText()), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void openFile(File f) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        String type = getMIMEType(f);
        intent.setDataAndType(Uri.fromFile(f), type);
        startActivity(intent);
    }

    private String getMIMEType(File f) {
        String end = f.getName().substring(f.getName().lastIndexOf(".") + 1,
                f.getName().length()).toLowerCase();
        String type = "";
        if (end.equals("mp3") || end.equals("aac") || end.equals("amr")
                || end.equals("mpeg") || end.equals("mp4")) {
            type = "audio";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg")) {
            type = "image";
        } else {
            type = "*";
        }
        type += "/";
        return type;
    }

    //获取文件
    private void getRecordFiles() {
        // TODO Auto-generated method stub
        SharedPreferences sharedPreferences1= getActivity().getSharedPreferences("text", Context.MODE_PRIVATE);
        String email = sharedPreferences1.getString("email", "");
        SharedPreferences sharedPreferences2= getActivity().getSharedPreferences("Title", Context.MODE_PRIVATE);
        String title = sharedPreferences2.getString("title", "");
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        int log = sharedPreferences.getInt("log",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences3= getActivity().getSharedPreferences("ID", Context.MODE_PRIVATE);
        int id = sharedPreferences3.getInt("id",Context.MODE_PRIVATE);
        final int[] img = new int[]{R.drawable.user1, R.drawable.user2,  R.drawable.user3,  R.drawable.user4,  R.drawable.user5};
        Integer[] mThumbIds  = { R.drawable.image1, R.drawable.image2,
                R.drawable.image3, R.drawable.image4, R.drawable.image5,
                R.drawable.image6, R.drawable.image7 };
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
                            UserList.add(new User(email,files[i].getName(),img[log],mThumbIds[id]));
                    }
                }
            }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onResume() {
        int id = getActivity().getIntent().getIntExtra("id", 0);
        if(id==2){
            vp.setCurrentItem(2);
        }
        super.onResume();
    }
}
