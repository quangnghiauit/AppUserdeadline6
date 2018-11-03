package com.app.nqn.appstudent.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import android.support.annotation.Nullable;

import com.app.nqn.appstudent.model.User;


public class DBManager extends SQLiteOpenHelper {

    private final String TAG = "DBManager";
    private static final String DATABASE_NAME="user_manager";
    private static final String TABLE_NAME = "user";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER = "phone";
    private static final String GENDER = "gender";
    private static int VERSION = 1;

    private Context context;
    private String SQLQuery = "CREATE TABLE "+ TABLE_NAME +" (" +
            ID + " integer primary key, "+
            NAME + " TEXT, " +
            EMAIL+ " TEXT, "+
            PHONE_NUMBER+ " TEXT, "+
            GENDER + " TEXT)";




    public DBManager(@Nullable Context context) {

        super(context, DATABASE_NAME, null, VERSION);
        this.context= context;
        Log.d(TAG,"DBManager: ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQLQuery);
        Log.d(TAG,"OnCreate: ");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,"onUpgrade: ");

    }



    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,user.getmName());
        values.put(EMAIL,user.getmEmai());
        values.put(PHONE_NUMBER,user.getmPhoneNumber());

        db.insert(TABLE_NAME,null,values);
        db.close();

        Log.d(TAG,"addUser Successfully");
    }
}
