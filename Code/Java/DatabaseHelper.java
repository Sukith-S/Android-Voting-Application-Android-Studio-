package com.example.onlinevotingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{
    static final String DATABASE = "VotingDB";
    static final int DATABASE_VERSION=1;
    static final String ADMIN_TABLE = "Admin_table";
    static  final String A_ID = "a_id";
    static final String NAME = "username";
    static final String A_PASSWORD = "a_password";
    static final String VOTER_TABLE ="Voter_table";
    static  final String V_ID = "v_id";
    static final String USN = "usn";
    static final String V_PASSWORD = "v_password";
    static final String PHNO = "ph_no";
    static final String VOTECASTED_TABLE = "Votecasted_table";
    static  final String VC_ID = "vc_id";
    static final String VC_USN = "vc_usn";
    static final String VOTE_CASTED = "vote_casted";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ADMIN_TABLE+"(a_id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT NOT NULL,a_password TEXT NOT NULL)");
        db.execSQL("CREATE TABLE "+VOTER_TABLE+"(v_id INTEGER PRIMARY KEY AUTOINCREMENT,usn TEXT UNIQUE NOT NULL,v_password TEXT NOT NULL,ph_no TEXT)");
        db.execSQL("CREATE TABLE "+VOTECASTED_TABLE+"(vc_id INTEGER PRIMARY KEY AUTOINCREMENT,vc_usn TEXT UNIQUE NOT NULL,vote_casted TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ADMIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+VOTER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+VOTECASTED_TABLE);
        onCreate(db);
    }

    public void insertData(String ADMIN, String admin)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long result;
        ContentValues values=new ContentValues();
        values.put(NAME,ADMIN);
        values.put(A_PASSWORD,admin);
        result=db.insert(ADMIN_TABLE,null,values);
    }
    public boolean searchadmin(String name, String pwd)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ADMIN_TABLE+" WHERE username = '"+name+"'"+" AND a_password = '"+pwd+"'",null);
        if(cursor.moveToFirst())
            return true;
        else
            return false;
    }
    public boolean addVoter(String usn,String pwd)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long voter;
        ContentValues values=new ContentValues();
        values.put(USN,usn);
        values.put(V_PASSWORD,pwd);
        voter=db.insert(VOTER_TABLE,null,values);
        if(voter==-1)
            return false;
        else
            return true;
    }
    public boolean searchvoter(String usn,String pwd)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+VOTER_TABLE+" WHERE usn = '"+usn+"'"+" AND v_password = '"+pwd+"'",null);
        if(cursor.moveToFirst())
            return true;
        else
            return false;
    }
    public boolean addVoteCasted(String usn,String vote)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long vote_cast;
        ContentValues values=new ContentValues();
        values.put(VC_USN,usn);
        values.put(VOTE_CASTED,vote);
        vote_cast=db.insert(VOTECASTED_TABLE,null,values);
        if(vote_cast==-1)
            return false;
        else
            return true;
    }
    public ArrayList<String> voterlist()
    {
        String itemname = null;
        final ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT vc_usn FROM "+VOTECASTED_TABLE,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() != true) {
            itemname = cursor.getString(cursor.getColumnIndex("vc_usn"));
            list.add(itemname);
            cursor.moveToNext();
        }
        return list;
    }
    public int viewResult(String vote)
    {
        int count=0;
        String itemname;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT vote_casted FROM "+VOTECASTED_TABLE+" WHERE vote_casted = '"+vote+"'",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() != true) {
            itemname = cursor.getString(cursor.getColumnIndex("vote_casted"));
            count++;
            cursor.moveToNext();
        }
        return count;
    }
    public String fetchpwd(String usn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT v_password FROM "+VOTER_TABLE+" WHERE usn = '"+usn+"'",null);
        if(cursor.moveToFirst())
        {
            String pwd = cursor.getString(cursor.getColumnIndex("v_password"));
            return pwd;
        }
        else
            return "None";
    }
    public boolean addphno(String usn,String phno)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("UPDATE "+VOTER_TABLE+" SET ph_no = "+"'"+phno+"'"+ "WHERE usn = "+"'"+usn+"'");
        return true;
    }
}
