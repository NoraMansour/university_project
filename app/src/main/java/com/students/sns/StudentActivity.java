package com.students.sns;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentActivity extends AppCompatActivity {
    DataBaseManager dbManager;
    SQLiteDatabase db;
    Cursor cur;
    RecyclerView nRecyclerView;
    String collage_id,section_id,level,n_id;
    List<Notifications> notificationList;
    RecyclerView.Adapter adapter;
    NavigationView studentNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        studentNavView=(NavigationView)findViewById(R.id.student_nav_view);
        dbManager=new DataBaseManager(StudentActivity.this);
        db=dbManager.getWritableDatabase();
        notificationList=new ArrayList<>();
        nRecyclerView=(RecyclerView)findViewById(R.id.n_recyverlView);
        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(StudentActivity.this));
        getNotificationParams();
        loadNotifications();

        getNotifications();
        studentNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.logout){


                    Intent logoutIntent=new Intent(StudentActivity.this,SplashScreen.class);
                    startActivity(logoutIntent);
                }
                else if(item.getItemId()==R.id.about){

                    AlertDialog.Builder builder=new AlertDialog.Builder(StudentActivity.this);
                    builder.setTitle("Programmed By ");
                    builder.setMessage("Sahar Alariqi\n Sabreen\n Nora Yeheay \n Enas Al-Dahbali");
                    AlertDialog alert=builder.create();
                    alert.show();

                }
                else if(item.getItemId()==R.id.notification){
                    Intent notifyIntent=new Intent(StudentActivity.this,StudentActivity.class);
                    startActivity(notifyIntent);
                }

                return false;
            }
        });


    }

    public void getNotifications(){

        cur=dbManager.getNotifications(db);
        if(cur.getCount()==0){
            Toast.makeText(StudentActivity.this,"no notifcations",Toast.LENGTH_LONG).show();
        }
        else{
            cur.moveToFirst();
            do{
                notificationList.add(new Notifications(cur.getString(0),cur.getString(1),cur.getString(2),cur.getString(3)));

            }while(cur.moveToNext());
            adapter=new NotificationsAdapter(StudentActivity.this,notificationList);
            nRecyclerView.setAdapter(adapter);




        }
    }

    public void getNotificationParams() {
        SharedPreferences shared = getSharedPreferences("LOGIN_INFO",MODE_PRIVATE);

        collage_id=shared.getString("c_id",null);
        section_id=shared.getString("sec_id",null);
        level=shared.getString("s_level",null);
        n_id=String.valueOf(dbManager.getMaxId(db));

    }



    public void loadNotifications(){



        StringRequest nString=new StringRequest(Request.Method.POST,Server.URL+Server.N_PAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONArray notificationArray=new JSONArray(s);
                            for(int i=0;i<notificationArray.length();i++){
                                JSONObject notify=notificationArray.getJSONObject(i);
                                int id=notify.getInt("n_id");
                                String title=notify.getString("n_title");
                                String text=notify.getString("n_text");
                                String date=notify.getString("n_date");
                                String sender=notify.getString("n_sender");
                                dbManager.addNotify(id,title,text,sender,date,db);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(StudentActivity.this, "no new Notifications", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(StudentActivity.this, "can not access web page check your Connections", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("c_id", collage_id);
                params.put("s_id", section_id);
                params.put("level", level);
                params.put("maxId",n_id);

                return params;
            }};
        RequestQueue notifiesQueue= Volley.newRequestQueue(StudentActivity.this);
        notifiesQueue.add(nString);


    }






}
