package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by geomathe on 11/20/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="BankDatabase.db";


    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getReadableDatabase();
        Log.i("DB","Construct");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        Log.i("DB","Create");
       // SQLiteDatabase db  = this.getWritableDatabase();
        db.execSQL("create table " + "bank" +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)");
        db.execSQL("CREATE TABLE "+"sender" + "( `sid` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `bid` INTEGER NOT NULL, `sname` TEXT NOT NULL )");
        db.execSQL("CREATE TABLE "+"user" + "( `uid` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `bid` INTEGER NOT NULL, `acno` INTEGER NOT NULL )");
        db.execSQL("CREATE TABLE "+"expense" + "( `eid` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `etext` TEXT NOT NULL )");

        insertbank(db,1, "Allahabad bank");
        insertbank(db,2, "SBI");
        this.insertsender(db,1, "BT-ALBANK");
        this.insertsender(db,1, "VK-ALBANK");
        this.insertsender(db,1, "BW-ALBANK");
        this.insertsender(db,2, "BZ-ATMSBI");
        this.insertsender(db,2, "BP-ATMSBI");
    }

    public boolean dbcheck(String number)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from sender where sname like '"+number+"'",null);
        return res.getCount()>0?true:false;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        Log.i("DB","Upgrade");
        db.execSQL("DROP TABLE IF EXISTS bank");
        onCreate(db);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from bank",null);
        return res;
    }

    public boolean insertbank(SQLiteDatabase db, int id,String bank)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("NAME",bank);
        long result = db.insert("bank",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertsender(SQLiteDatabase db, int bid,String sname)
    {
        ContentValues contentValues = new ContentValues();
        //contentValues.put("ID",id);
        contentValues.put("bid", bid);
        contentValues.put("sname", sname);
        long result = db.insert("sender", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertuser(int bid,String acno)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("ID",id);
        contentValues.put("bid", bid);
        contentValues.put("acno", acno);
        long result = db.insert("user", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertExpense(String etext)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //contentValues.put("ID",id);
        contentValues.put("etext", etext);
        long result = db.insert("expense", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllExpenses() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from expense",null);
        return res;
    }
}
