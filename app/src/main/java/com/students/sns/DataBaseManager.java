package com.students.sns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by meem on 9/12/2017.
 */

public class DataBaseManager extends SQLiteOpenHelper {
    public static String DB_NAME="sns_sns";
    public static int DB_VERSION=1;
    Context ctx;
    public DataBaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        ctx=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS notifications(n_id INTEGER NOT NULL,n_title VARCHAR,n_text TEXT,n_sender VARCHAR,n_date VARCHAR,n_read VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS archived_notifications(n_id INTEGER PRIMARY KEY AUTOINCREMENT,n_title VARCHAR,n_text TEXT,n_collage VARCHAR,n_section VARCHAR,n_level VARCHAR,n_date VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getInstNotifies(SQLiteDatabase db){
        Cursor cur= db.rawQuery("select * from archived_notifications order by n_id desc",null);
        if(cur.getCount()>0)
            return  cur;
        return null;
    }


    public Cursor getNotifications (SQLiteDatabase db){
        return db.rawQuery("select n_title,n_text,n_sender,n_date from notifications order by n_id desc",null);

    }

    public int getMaxId(SQLiteDatabase db){
         Cursor cur=db.rawQuery("select max(n_id)from notifications",null);
        cur.moveToFirst();
        return cur.getInt(0);



        //return 1;
    }
    public void addNotify(int id,String title,String text,String sender,String date,SQLiteDatabase db){
        ContentValues values=new ContentValues();
        values.put("n_id",id);
        values.put("n_title",title);
        values.put("n_text",text);
        values.put("n_sender",sender);
        values.put("n_date",date);
        db.insert("notifications",null,values);
    }
    public void archiveNotify(String title,String text,String collage,String section,String level,String date,SQLiteDatabase db){
        ContentValues values=new ContentValues();
        values.put("n_title",title);
        values.put("n_text",text);
        values.put("n_collage",collage);
        values.put("n_section",section);
        values.put("n_level",level);
        values.put("n_date",date);
        db.insert("archived_notifications",null,values);


    }
    public void deleteRows(SQLiteDatabase db){
        db.execSQL("delete from notifications");
    }
}
