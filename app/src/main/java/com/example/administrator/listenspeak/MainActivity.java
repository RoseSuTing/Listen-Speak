package com.example.administrator.listenspeak;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    private TextView mTextMessage;
    private BottomNavigationView mNavigationView;
    public BottomNavigationViewHelper bottomNavigationViewHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        mNavigationView = findViewById(R.id.navigation);
        bottomNavigationViewHelper.disableShiftMode(mNavigationView);
        mNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.listen:
                                ListenFragment listenFragment = new ListenFragment();
                                addFragment(listenFragment,"fragment_listen");
                                return true;
                            case R.id.asking:
                                AskingFragment askingFragment = new AskingFragment();
                                addFragment(askingFragment,"fragment_asking");
                                return true;
                            case R.id.world:
                               WorldFragment worldFragment = new WorldFragment();
                               addFragment(worldFragment,"fragment_world");
                                return true;
                            case R.id.my:
                                MyFragment myFragment = new MyFragment();
                                addFragment(myFragment,"fragment_my");
                                return true;
                        }
                        return false;
                    }
                });
    }
    public void addFragment(android.app.Fragment fragment, String tag) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, fragment, tag);
        transaction.commit();
    }

}
