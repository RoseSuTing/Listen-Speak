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
import android.widget.AdapterView.OnItemClickListener;
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
    public ArrayList<User> UserList = new ArrayList<User>();
    private ListView listview;
    private ImageView play;
    private boolean sigle = false;
    private boolean sdcardExit;

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

        listview = getActivity().findViewById(R.id.listview);
        play = getActivity().findViewById(R.id.play);
        ImageView img_like = getActivity().findViewById(R.id.like);

        img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LikeActivity.class);
                startActivity(intent);
            }
        });

        ListenAdapter listenAdapter =
                new ListenAdapter(
                        getActivity(), // Current context
                        R.layout.layout, // the layout for each item in the list
                        UserList); // the arrayList to feed the arrayAdapter
        String[] email = new String[]{"1457969720@qq.com", "122435436@163.com", "1634235346@qq.com", "14324678@qq.com", "32432546@qq.com", "325436567@qq.com", "326576578@qq.com"};
        String[] title = new String[]{"bdidbn", "wfqgrgq", "reger", "wtwt", "werewt", "rwewetrw", "wetwq"};
        final int[] img = new int[]{R.drawable.user1, R.drawable.user2,  R.drawable.user3,  R.drawable.user4,  R.drawable.user5,R.drawable.image6,R.id.img_said};
        Integer[] mThumbIds  = { R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7 };
        for (int i = 0; i < email.length; i++) {
            UserList.add(new User(email[i], title[i], img[i], mThumbIds[i]));
        }
        listview.setAdapter(listenAdapter);


        //启用监听器
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent = new Intent(getActivity(),PersonActivity.class);
              startActivity(intent);
            }
        });


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
