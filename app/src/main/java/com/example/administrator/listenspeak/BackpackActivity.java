package com.example.administrator.listenspeak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BackpackActivity extends AppCompatActivity {
private Button login_out;
private ImageView account,password,push;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpack);
        login_out = findViewById(R.id.login_out);
        account = findViewById(R.id.account);
        password = findViewById(R.id.account);
        push = findViewById(R.id.account);
        //退出登陆
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logout();
        }
        login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BackpackActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BackpackActivity.this,PasswordActivity.class);
                startActivity(intent);
            }
        });
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BackpackActivity.this,PushActivity.class);
                startActivity(intent);
            }
        });
    }
    public void logout(){
        session.setLogin(false);
        // Redirect the user to the login activity
        startActivity(new Intent(BackpackActivity.this, LoginActivity.class));
        finish();
    }

}
