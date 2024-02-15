package com.example.nb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.nb.DataModel.itemDataModel;

import java.util.ArrayList;

public class myDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="NotesDB";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_USERS = "users";
    private static final String KEY_USER_ID = "UserId";
    private static final String KEY_USER_EMAIL = "UserEmail";
    private static final String TABLE_NOTES="notes";
    private static final String KEY_ID="Id";
    private static final String KEY_NOTE="Note";


    public myDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NOTES+
                "("+KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_NOTE+" TEXT "+")");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_USERS+
                "("+KEY_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_USER_EMAIL+" TEXT"+ ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < 2 && newVersion >= 2) {
            sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NOTES + " ADD COLUMN NEW_COLUMN_NAME TEXT");
            sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN NEW_COLUMN_NAME TEXT");

        }
    }
    public void insertUser(String email){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(KEY_USER_EMAIL,email);
        db.insert(TABLE_USERS,null,value);
    }
    public void insertNote(String Note){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NOTE,Note);
        db.insert(TABLE_NOTES,null,values);
    }
    public ArrayList<itemDataModel> fetchNotes(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(" SELECT * FROM "+ TABLE_NOTES,null);
        ArrayList<itemDataModel> arrNotes=new ArrayList<>();
        while(cursor.moveToNext()){
            itemDataModel model=new itemDataModel(cursor.getInt(0),cursor.getString(1));
            arrNotes.add(model);

        }
        return arrNotes;
    }
}
