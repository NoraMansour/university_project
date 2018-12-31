package com.students.sns;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class InstDashboard extends AppCompatActivity {
    CardView pushNotify,archivedNotifies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inst_dashboard);
        pushNotify=(CardView)findViewById(R.id.push_notify);
        archivedNotifies=(CardView)findViewById(R.id.archived_notifies);
        pushNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent notifyIntent=new Intent(InstDashboard.this,SendNotify.class);
                startActivity(notifyIntent);

            }
        });
        archivedNotifies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent archivedIntent=new Intent(InstDashboard.this,ArchivedNotifies.class);
                startActivity(archivedIntent);


            }
        });
    }
}
