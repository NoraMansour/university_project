package com.students.sns;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendNotify extends AppCompatActivity {
    EditText notifyTitle,notifyText;
    Spinner collages,sections,levels;
    Button sendNotify;
    String inst_id;
    String collageData[];
    String sectionData[];
    NavigationView instNavView;
    DataBaseManager dbManager;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notify);
        notifyTitle=(EditText)findViewById(R.id.notify_title);
        notifyText=(EditText)findViewById(R.id.notify_text);
        collages=(Spinner)findViewById(R.id.collage);
        sections=(Spinner)findViewById(R.id.section);
        levels=(Spinner)findViewById(R.id.level);
        sendNotify=(Button)findViewById(R.id.send_notify);
        instNavView=(NavigationView)findViewById(R.id.inst_nav_view);
        final SharedPreferences shared=getSharedPreferences("LOGIN_INFO",MODE_PRIVATE);
        final SharedPreferences.Editor editor=shared.edit();
        inst_id=shared.getString("instId",null);
        loadCollages();
        collages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                collageData=collages.getSelectedItem().toString().split(":");
                if(!(collageData[0].equals("0"))){
                    loadSections(collageData[0]);
                  //  Toast.makeText(SendNotify.this, collageData[0], Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sectionData=sections.getSelectedItem().toString().split(":");
                if(!(sectionData[0].equals("0"))){
                    loadLevels(sectionData[0],collageData[0]);

                    //Toast.makeText(SendNotify.this, sectionData[0]+":"+collageData[0], Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        sendNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notifyTitle.getText().toString().equals("") || notifyText.getText().toString().equals("") || collages.getSelectedItem().toString().equals("0:choose a collage form list")
                        || sections.getSelectedItem().toString().equals("") || sections.getSelectedItem().toString().equals("0:choose a section from list")
                        || levels.getSelectedItem().toString().equals("") || levels.getSelectedItem().toString().equals("choose a level form list")) {

                    Toast.makeText(SendNotify.this, "all fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    send();
                }

            }
        });

        instNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.logout){

                    editor.clear();
                    editor.commit();
                    Intent logoutIntent=new Intent(SendNotify.this,SplashScreen.class);
                    startActivity(logoutIntent);
                }
                else if(item.getItemId()==R.id.about){

                    AlertDialog.Builder builder=new AlertDialog.Builder(SendNotify.this);
                    builder.setTitle("Programmed By ");
                    builder.setMessage("Sahar Alariqi\n Sabreen\n Nora Yeheay \n Enas Al-Dahbali");
                    AlertDialog alert=builder.create();
                    alert.show();

                }
                return false;
            }
        });

    }




    public void send(){




        StringRequest sendNotifyString=new StringRequest(Request.Method.POST, Server.URL+Server.ADD_NOTIFY_PAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Toast.makeText(SendNotify.this, s, Toast.LENGTH_LONG).show();
                        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                        dbManager=new DataBaseManager(SendNotify.this);
                        db=dbManager.getWritableDatabase();

                        String []collage=collages.getSelectedItem().toString().split(":");
                        String [] section=sections.getSelectedItem().toString().split(":");
                        dbManager.archiveNotify(notifyTitle.getText().toString(),notifyText.getText().toString(),collage[1],section[1],levels.getSelectedItem().toString(),date,db);
                        Intent instDashIntent=new Intent(SendNotify.this,InstDashboard.class);
                        startActivity(instDashIntent);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SendNotify.this, "Can not connect to Host please check your connection ", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String>notifyData=new HashMap<>();
                notifyData.put("inst_id",inst_id);
                notifyData.put("collage",collageData[0]);
                notifyData.put("section",sectionData[0]);
                notifyData.put("addNotify","yes");
                notifyData.put("n_title",notifyTitle.getText().toString());
                notifyData.put("n_text",notifyText.getText().toString());
                notifyData.put("level",levels.getSelectedItem().toString());

                return notifyData;
            }
        };
        RequestQueue levelsQueue= Volley.newRequestQueue(SendNotify.this);
        levelsQueue.add(sendNotifyString);




    }

    public void loadLevels(final String sec, final String coll){


        StringRequest levelsStringRequest=new StringRequest(Request.Method.POST,Server.URL+Server.LEVELS_PAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        ArrayList<String> inst_levels=new ArrayList<>();
                        inst_levels.add("choose a level form list");
                        try {

                            JSONArray JLevels=new JSONArray(s);
                            for(int i=0;i<JLevels.length();i++){
                                JSONObject levelObject=JLevels.getJSONObject(i);
                                String row=levelObject.getString("level");

                                inst_levels.add(row);

                            }
                            ArrayAdapter adapter=new ArrayAdapter(SendNotify.this,R.layout.support_simple_spinner_dropdown_item,inst_levels);
                            levels.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SendNotify.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SendNotify.this, "Can not connect to Host please check your connection ", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String>levelData=new HashMap<>();
                levelData.put("inst_id",inst_id);
                levelData.put("c_id",coll);
                levelData.put("s_id",sec);

                return levelData;
            }
        };
        RequestQueue levelsQueue= Volley.newRequestQueue(SendNotify.this);
        levelsQueue.add(levelsStringRequest);




    }

    public void loadSections(final String collageId) {
        StringRequest sectionStringRequest=new StringRequest(Request.Method.POST, Server.URL+Server.SECTIONS_PAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Toast.makeText(SendNotify.this, "changed", Toast.LENGTH_SHORT).show();
                        ArrayList<String> instSections=new ArrayList<>();
                        instSections.add("0:choose a section from list");
                        try {
                          //  Toast.makeText(SendNotify.this, s, Toast.LENGTH_LONG).show();
                            JSONArray JCollages=new JSONArray(s);
                            for(int i=0;i<JCollages.length();i++){
                                JSONObject collageObject=JCollages.getJSONObject(i);
                                String row=collageObject.getString("section_id")+":"+collageObject.getString("section_name");
                                instSections.add(row);
                            }
                            ArrayAdapter adapter=new ArrayAdapter(SendNotify.this,R.layout.support_simple_spinner_dropdown_item,instSections);
                            sections.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SendNotify.this, "Can not connect to Host please check your connection ", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>instData=new HashMap<>();
                instData.put("inst_id",inst_id);
                instData.put("collage_id",collageId);
                return instData;
            }
        };
        RequestQueue sectionQueue= Volley.newRequestQueue(SendNotify.this);
        sectionQueue.add(sectionStringRequest);




    }

    public void loadCollages() {
        StringRequest collageStringRequest=new StringRequest(Request.Method.POST,Server.URL+Server.COLLAGES_PAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        ArrayList<String> instCollages=new ArrayList<>();
                        instCollages.add("0:choose a collage form list");
                        try {

                            JSONArray JCollages=new JSONArray(s);
                            for(int i=0;i<JCollages.length();i++){
                                JSONObject collageObject=JCollages.getJSONObject(i);
                                String row=collageObject.getString("collage_id")+":"+collageObject.getString("collage_name");
                                instCollages.add(row);
                            }
                            ArrayAdapter adapter=new ArrayAdapter(SendNotify.this,R.layout.support_simple_spinner_dropdown_item,instCollages);
                            collages.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SendNotify.this, "Can not connect to Host please check your connection ", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>instId=new HashMap<>();
                instId.put("inst_id",inst_id);
                return instId;
            }
        };
        RequestQueue collageQueue= Volley.newRequestQueue(SendNotify.this);
        collageQueue.add(collageStringRequest);


    }
}
