package com.students.sns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotifyDetails extends AppCompatActivity {
    TextView sender,title,date,body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_details);
        sender=(TextView)findViewById(R.id.sender);
        title=(TextView)findViewById(R.id.title);
        date=(TextView)findViewById(R.id.date);
        body=(TextView)findViewById(R.id.body);
        sender.setText(getIntent().getStringExtra("sender"));
        title.setText(getIntent().getStringExtra("title"));
        date.setText(getIntent().getStringExtra("date"));
        body.setText(getIntent().getStringExtra("body"));
    }
}
