package com.nandakishor.chatgptuiapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nandakishor.chatgptuiapp.models.AuditListItemData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.util.Log;

import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.COLUMN_NAME_DATETIME;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.COLUMN_NAME_PROMPT;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.COLUMN_NAME_RESPONSE;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.COLUMN_NAME_SEQNO;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.PROMPT_TABLE_NAME;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.RESPONSE_TABLE_NAME;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.READ_ALL_DATA;

public class AuditDataProvider {
  private static final String TAG = "AuditDataProvider";

  Context context;

  public AuditDataProvider(Context ctx) {
    context = ctx;
  }

  public boolean insert(AuditListItemData dataItem) {
    try (AuditReaderDBHelper dbHelper = new AuditReaderDBHelper(context);
         SQLiteDatabase db = dbHelper.getWritableDatabase()) {
      ContentValues promptTableValues = new ContentValues();
      promptTableValues.put(COLUMN_NAME_SEQNO, dataItem.getSeqNo());
      promptTableValues.put(COLUMN_NAME_PROMPT, dataItem.getPrompt());
      promptTableValues.put(COLUMN_NAME_DATETIME, dataItem.getDateTime());
      long promptRowId = db.insert(PROMPT_TABLE_NAME, null, promptTableValues);

      ContentValues responseTableValues = new ContentValues();
      responseTableValues.put(COLUMN_NAME_SEQNO, dataItem.getSeqNo());
      responseTableValues.put(COLUMN_NAME_RESPONSE, dataItem.getResponse());
      responseTableValues.put(COLUMN_NAME_DATETIME, dataItem.getDateTime());
      long responseRowId = db.insert(RESPONSE_TABLE_NAME, null, responseTableValues);
    } catch (Exception ex) {
      return false;
    }

    return true;
  }

  public List<AuditListItemData> readAll() {
    Cursor cursor = null;
    List<AuditListItemData> allItemsList = new ArrayList<>();
    try (AuditReaderDBHelper dbHelper = new AuditReaderDBHelper(context);
         SQLiteDatabase db = dbHelper.getReadableDatabase()) {
      String[] projection = {
          COLUMN_NAME_SEQNO, COLUMN_NAME_PROMPT, COLUMN_NAME_RESPONSE, COLUMN_NAME_DATETIME
      };
      cursor = db.rawQuery(READ_ALL_DATA, null);

//      String[] projectionPrompt = {
//          COLUMN_NAME_SEQNO, COLUMN_NAME_PROMPT, COLUMN_NAME_DATETIME
//      };
//
//      String selection = Feed

      while (cursor.moveToNext()) {
        String seqNo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_SEQNO));
        String prompt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PROMPT));
        String response = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_RESPONSE));
        String datetime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DATETIME));
        AuditListItemData item = new AuditListItemData(seqNo, prompt, response, datetime);
        allItemsList.add(item);
      }
    } catch (Exception e) {
      Log.d(TAG, e.toString());
    } finally {
      if (cursor != null)
        cursor.close();
    }

    return allItemsList;
  }
}
