package com.nandakishor.chatgptuiapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nandakishor.chatgptuiapp.models.AuditListItemData;

import java.util.Collections;
import android.util.Log;
import java.util.List;

import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.SQL_CREATE_PROMPT_ENTRIES;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.SQL_CREATE_RESPONSE_ENTRIES;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.SQL_DELETE_PROMPT_ENTRIES;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.SQL_DELETE_RESPONSE_ENTRIES;

public class AuditReaderDBHelper extends SQLiteOpenHelper {

  private static final String TAG = "AuditReaderDBHelper";
  private AuditDataProvider dataProvider;

  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "ChatGptUiAppAuditData.db";

  public AuditReaderDBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    dataProvider = new AuditDataProvider(context);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    try {
      sqLiteDatabase.execSQL(SQL_CREATE_PROMPT_ENTRIES);
      sqLiteDatabase.execSQL(SQL_CREATE_RESPONSE_ENTRIES);
    } catch (Exception e) {
      Log.d(TAG, "Table already exists --> " + e);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL(SQL_DELETE_PROMPT_ENTRIES);
    sqLiteDatabase.execSQL(SQL_DELETE_RESPONSE_ENTRIES);
  }

  public boolean insert(AuditListItemData item) {
    return dataProvider.insert(item);
  }

  public List<AuditListItemData> fetchAll() {
    return dataProvider.readAll();
  }
}
