package com.students.sns;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    EditText userName;
    EditText userPass;
    DataBaseManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton=(Button)findViewById(R.id.login_button);
        userName=(EditText)findViewById(R.id.s_id);
        userPass=(EditText)findViewById(R.id.s_pass);
        dbManager=new DataBaseManager(LoginActivity.this);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(LoginActivity.this, spinnerLogin.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                String []user=userName.getText().toString().split("-");
                Toast.makeText(LoginActivity.this, user[0], Toast.LENGTH_SHORT).show();
                if(user[0].equals("inst")){
                    instructorLogin();

                }
                else {
                    studentLogin();


                }


            }
        });


    }

    public void studentLogin(){
        StringRequest loginString=new StringRequest(Request.Method.POST, Server.URL+Server.STUDENT_LOGIN_PAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jStudent=new JSONObject(s);


                            SharedPreferences shared=getSharedPreferences("LOGIN_INFO",MODE_PRIVATE);
                            SharedPreferences.Editor editor=shared.edit();
                            editor.putString("s_name",jStudent.getString("s_name"));
                            editor.putString("c_id",jStudent.getString("c_id"));
                            editor.putString("sec_id",jStudent.getString("sec_id"));
                            editor.putString("s_level",jStudent.getString("s_level"));
                            editor.putString("status","logged");
                            editor.putString("type","student");
                            editor.commit();
                            Intent studentIntent=new Intent(LoginActivity.this,StudentActivity.class);
                            startActivity(studentIntent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Incorrect Id or Password", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LoginActivity.this,"Can not Access the url",Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>loginParameters=new HashMap<String, String>();
                loginParameters.put("id",userName.getText().toString());
                loginParameters.put("pass",userPass.getText().toString());

                return loginParameters;
            }
        };


        RequestQueue loginQueue=Volley.newRequestQueue(LoginActivity.this);
        loginQueue.add(loginString);


    }

    public  void instructorLogin(){
        StringRequest loginString=new StringRequest(Request.Method.POST, Server.URL+Server.INST_LOGIN_PAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
                            JSONObject jInstructor=new JSONObject(s);
                            SharedPreferences shared=getSharedPreferences("LOGIN_INFO",MODE_PRIVATE);
                            SharedPreferences.Editor editor=shared.edit();
                            editor.putString("instId",jInstructor.getString("a_id"));
                            editor.putString("instName",jInstructor.getString("a_name"));
                            editor.putString("status","logged");
                            editor.putString("type","inst");
                            editor.commit();
                            Intent instIntent=new Intent(LoginActivity.this,InstDashboard.class);
                            startActivity(instIntent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Incorrect Id or Password", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LoginActivity.this,"Can not Access the url\n"+Server.URL+Server.INST_LOGIN_PAGE,Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String,String>loginParameters=new HashMap<String, String>();
                loginParameters.put("id",userName.getText().toString());
                loginParameters.put("pass",userPass.getText().toString());

                return loginParameters;
            }
        };


        RequestQueue loginQueue=Volley.newRequestQueue(LoginActivity.this);
        loginQueue.add(loginString);



    }
}
