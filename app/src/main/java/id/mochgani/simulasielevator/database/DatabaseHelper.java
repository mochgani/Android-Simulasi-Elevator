package id.mochgani.simulasielevator.database;

/**
 * Created by mochgani on 16/12/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 5;
    private static final String SESSION_TABLE = "t_session";
    private static final String HISTORY_TABLE = "t_history";
    private static final String DATABASE_NAME = "db_simulasielevator";

    public static final String KEY_ID = "_id";

    public static final String KEY_FLOOR = "floor";
    public static final String KEY_DATE = "date_update";

    public static final String KEY_COND = "condition";
    public static final String KEY_DEST = "destination_floor";
    public static final String KEY_STATUS = "status";

    private static final String SESSION_TABLE_CREATE =
            "CREATE TABLE " + SESSION_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_FLOOR + " INTEGER, " +
                    KEY_DATE + " TEXT );";

    private static final String HISTORY_TABLE_CREATE =
            "CREATE TABLE " + HISTORY_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_FLOOR + " INTEGER, " +
                    KEY_COND + " TEXT, " +
                    KEY_DEST + " INTEGER, " +
                    KEY_STATUS + " TEXT, " +
                    KEY_DATE + " TEXT );";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SESSION_TABLE_CREATE);
        Log.d(TAG, "Tabel Session Terbuat");

        db.execSQL(HISTORY_TABLE_CREATE);
        Log.d(TAG, "Tabel History Terbuat");

        fillSession(db);
    }

    public void fillSession(SQLiteDatabase db) {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = df.format(c);

        ContentValues values = new ContentValues();

        values.put(KEY_FLOOR, 1);
        values.put(KEY_DATE, dateNow);
        db.insert(SESSION_TABLE, null, values);
    }

    public String getDataSingle(String namaTabel, String fieldWhere, String valueWhere, String fieldOutput){
        String query = "SELECT  * FROM " + namaTabel + " WHERE " + fieldWhere + "='" + valueWhere + "'";

        Cursor cursor = null;
        String word = null;

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            word = cursor.getString(cursor.getColumnIndex(fieldOutput));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            cursor.close();
            return word;
        }
    }

    public int cekSession(){
        String query = "SELECT * FROM " + SESSION_TABLE;

        Cursor cursor = null;
        int floor = 1;

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            floor = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_FLOOR)));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            cursor.close();
            return floor;
        }
    }

    public long insertHistory(int floor, String condition, int destinationFloor) {
        long newId = 0;

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = df.format(c);

        ContentValues values = new ContentValues();
        values.put(KEY_FLOOR, floor);
        values.put(KEY_COND, condition);
        values.put(KEY_DEST, destinationFloor);
        values.put(KEY_STATUS, "PENDING");
        values.put(KEY_DATE, dateNow);
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(HISTORY_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    public int[] getHistory(String kondisi){
        String query = "SELECT * FROM " + HISTORY_TABLE + " WHERE status='PENDING' ORDER BY " + KEY_DEST +" " + kondisi;

        Cursor cursor = null;
        int[] daftar = new int[0];

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            daftar = new int[cursor.getCount()];
            cursor.moveToFirst();
            for (int i=0; i < cursor.getCount(); i++){
                cursor.moveToPosition(i);
                daftar[i] = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_DEST)));
            }
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return daftar;
        }
    }

    public int updateHistory(int dest) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            ContentValues values = new ContentValues();
            values.put(KEY_STATUS, "DONE");

            mNumberOfRowsUpdated = mWritableDB.update(HISTORY_TABLE, //table to change
                    values, // new values to insert
                    KEY_DEST + " = ?", // selection criteria for row (in this case, the _id column)
                    new String[]{String.valueOf(dest)}); //selection args; the actual value of the id

        } catch (Exception e) {
            Log.d (TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + SESSION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE);
        onCreate(db);
    }
}
