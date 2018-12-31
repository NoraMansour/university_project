package com.students.sns;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ArchivedNotifies extends AppCompatActivity {
    RecyclerView archivedRecyclerView;
    RecyclerView.Adapter adapter;
    DataBaseManager dbManager;
    SQLiteDatabase db;
    Cursor cur;
    List<ArchivedNotifiesList> archivedNotifies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_notifies);
        archivedRecyclerView=(RecyclerView)findViewById(R.id.archived_recycler_view);
        archivedRecyclerView.setHasFixedSize(true);
        archivedRecyclerView.setLayoutManager(new LinearLayoutManager(ArchivedNotifies.this));
        dbManager=new DataBaseManager(ArchivedNotifies.this);
        db=dbManager.getWritableDatabase();
        cur=dbManager.getInstNotifies(db);
        archivedNotifies=new ArrayList<>();

        if(cur!=null){
            cur.moveToFirst();
            do{
                archivedNotifies.add(new ArchivedNotifiesList(cur.getString(1),cur.getString(2),cur.getString(3),cur.getString(4),cur.getString(5),cur.getString(6)));


            }while(cur.moveToNext());
            adapter=new ArchivedNotifiesListAdapter(ArchivedNotifies.this,archivedNotifies);
            archivedRecyclerView.setAdapter(adapter);
        }
        else{
            Toast.makeText(ArchivedNotifies.this, "no Archived Notifications Found", Toast.LENGTH_SHORT).show();
        }
    }
}
