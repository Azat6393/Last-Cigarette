package com.kastudio.sonsigaram;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class LastContentProvider extends android.content.ContentProvider {

    static final String PROVIDER_NAME = "com.kastudio.sonsigaram.LastContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/last";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String USERNAME = "username";
    static final String TIME = "time";
    static final String FIYAT = "fiyat";
    static final String NAME = "name";
    static final String SIGARA = "sigara";
    static final String PAKET = "paket";
    static final String MONEY = "money";
    static final String PROFILE_ONE = "profileOne";
    static final String PROFILE_TWO = "profileTwo";
    static final String PROFILE_THREE_HOUR = "profileThreeHour";
    static final String PROFILE_THREE_DAY = "profileThreeDay";
    static final String PROFILE_THREE_MONTH = "profileThreeMonth";
    static final String MOTIVATION = "motivation";

    static final int LAST = 1;
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"last",LAST);
    }
    private static HashMap<String, String> LAST_PROJECTION_MAP;

    //------------------------- DATABASE ----------------------------

    private SQLiteDatabase sqLiteDatabase;
    static final String DATABASE_NAME = "Last";
    static final String TABLE_NAME = "last";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DATABASE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(id INTEGER PRIMARY KEY, " +
            "username TEXT, " +
            "time INTEGER, " +
            "fiyat REAL, " +
            "name TEXT, " +
            "sigara INTEGER, " +
            "paket INTEGER, " +
            "money TEXT, " +
            "profileOne INTEGER, " +
            "profileTwo REAL, " +
            "profileThreeHour INTEGER, " +
            "profileThreeDay INTEGER, " +
            "motivation TEXT, " +
            "profileThreeMonth INTEGER);";


    private static class DataBaseHelper extends SQLiteOpenHelper{

        public DataBaseHelper(@Nullable Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DATABASE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


    //-------------------------- Content Provide ------------------------------------

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        return sqLiteDatabase != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)){
            case LAST:
                sqLiteQueryBuilder.setProjectionMap(LAST_PROJECTION_MAP);
                break;
            default:
        }
        if (sortOrder == null || sortOrder.matches("")){
            sortOrder = NAME;
        }
        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long rowID = sqLiteDatabase.insert(TABLE_NAME,"",values);

        if (rowID > 0){
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI,rowID);
            getContext().getContentResolver().notifyChange(newUri,null,false);
            return newUri;
        }
        throw  new SQLException("Error!");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int rowCount = 0;

        switch (uriMatcher.match(uri)){
            case LAST:
                rowCount = sqLiteDatabase.delete(TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Failed URI");
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return rowCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int rowCount = 0;

        switch (uriMatcher.match(uri)){
            case LAST:
                rowCount = sqLiteDatabase.update(TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Failed URI");
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return rowCount;
    }
}
