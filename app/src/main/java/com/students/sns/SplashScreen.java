package com.students.sns;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread splashThread=new Thread(){

            @Override
            public void run() {
                super.run();
                try {

                    sleep(5000);




                    Intent loginIntent=new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(loginIntent);
/*
                    SharedPreferences shared=getSharedPreferences("LOGIN_INFO",MODE_PRIVATE);
                    if(shared.contains("status")&&shared.contains("type")){
                        if(shared.getString("status",null).equals("logged") && shared.getString("type",null).equals("inst")){
                            sleep(5000);
                            Intent instIntent=new Intent(SplashScreen.this,SendNotify.class);
                            startActivity(instIntent);

                        }
                        else if(shared.getString("status",null).equals("logged") && shared.getString("type",null).equals("student")){
                            sleep(5000);
                            Intent stuIntent=new Intent(SplashScreen.this,StudentActivity.class);
                            startActivity(stuIntent);

                        }
                        else {
                            Intent loginIntent=new Intent(SplashScreen.this,LoginActivity.class);
                            startActivity(loginIntent);

                        }
                    }
                    else {
                        Intent loginIntent=new Intent(SplashScreen.this,LoginActivity.class);
                        startActivity(loginIntent);

                    }*/



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        splashThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
