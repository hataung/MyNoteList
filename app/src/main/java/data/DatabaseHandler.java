package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.MyNote;

/**
 * Created by ayeayezan on 24/02/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    private final ArrayList<MyNote> noteList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create your data table
        String CREATE_NOTES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" + Constants.KEY_ID +
                " INTEGER PRIMARY KEY, " + Constants.TITLE_NAME + " TEXT, " + Constants.CONTENT_NAME + " TEXT, " +
                Constants.DATE_NAME + " LONG);";//after long) ; or :??? check if you have running error.

        db.execSQL(CREATE_NOTES_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + Constants.TABLE_NAME);

        Log.v("ONUPGRADE","DROPING THE TABLE AND CREATING A NEW ONE!");

        //create a new one
        onCreate(db);

    }

    //delete a note
    public void deleteNote(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ? ", new String[]{ String.valueOf(id)});

        db.close();

    }



    //add content to table

    public void addNotes( MyNote note){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.TITLE_NAME,note.getTitle());
        values.put(Constants.CONTENT_NAME,note.getContent());

        /*
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String mydate = dateFormat.format(date);
        values.put(Constants.DATE_NAME,mydate);
        //if these codes are inserted, the date is not correct.
        */
        values.put(Constants.DATE_NAME,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);

        //Log.v("Note Successfully!", "yeah!!!");// you can check if it works without commenting Intent i ... in main activity

        db.close();


    }


    //get or retrieve all notes

    public ArrayList<MyNote> getNotes() {

        String selectQuery = " SELECT * FROM " + Constants.TABLE_NAME + " WHERE 1 ";

        //SQLiteDatabase db = this.getReadableDatabase();// the same as getWritableDatabase()???
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        /*
        //this code does not work in my pc.
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID,
                Constants.TITLE_NAME, Constants.CONTENT_NAME, Constants.DATE_NAME,
        }, null, null, null, null, Constants.DATE_NAME + " DESC ");// before }, need , after Constants.DATE_NAME???
        */

        /*
        Cursor cursor = db.query(selectQuery, new String[]{Constants.KEY_ID,
                Constants.TITLE_NAME, Constants.CONTENT_NAME, Constants.DATE_NAME
        },null, null, null, null, Constants.DATE_NAME + "DESC");
        */

        if (cursor.moveToFirst()){

            do {

                MyNote note = new MyNote();
                note.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                note.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));

                note.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                //note.setRecordDate(cursor.getString(cursor.getColumnIndex(Constants.DATE_NAME)));


                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String dateData = sdf.getDateTimeInstance().format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                //java.text.DateFormat dateFormat = java.text.DateFormat.getDateTimeInstance();
                //DateFormat dateFormat = DateFormat.getDateTimeInstance();
                //String dateData = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());
                //String dateData = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                note.setRecordDate(dateData);


                noteList.add(note);


            } while(cursor.moveToNext());
        }
        cursor.close();

        return noteList;
    }
}
