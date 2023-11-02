package com.nandakishor.chatgptuiapp.tasks;

import android.os.AsyncTask;

import com.nandakishor.chatgptuiapp.database.AuditReaderDBHelper;
import com.nandakishor.chatgptuiapp.models.AuditListItemData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalDatabaseTask extends AsyncTask<AuditListItemData, Integer, List<AuditListItemData>> {

  public interface DatabaseCallback {
    void fetchSuccess(List<AuditListItemData> updatedItems);
    void fetchFailed(String errorMsg);
  }

  private WeakReference<AuditReaderDBHelper> dbHelper;
  private AuditListItemData itemData;

  private DatabaseCallback callback;

  public LocalDatabaseTask(
      WeakReference<AuditReaderDBHelper> dbHelper,
      AuditListItemData itemData,
      DatabaseCallback callback
  ) {
    this.dbHelper = dbHelper;
    this.itemData = itemData;
    this.callback = callback;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected List<AuditListItemData> doInBackground(AuditListItemData... auditListItemData) {


    if (itemData != null) {
      dbHelper.get().insert(itemData);
    }

    // fetch everything from db and return

    return new ArrayList<>(dbHelper.get().fetchAll());
  }

  @Override
  protected void onProgressUpdate(Integer... values) {
    super.onProgressUpdate(values);
  }

  @Override
  protected void onPostExecute(List<AuditListItemData> auditListItemData) {
    super.onPostExecute(auditListItemData);
    if (auditListItemData.isEmpty()) {
      callback.fetchFailed("Could not fetch items from SQLite table.");
      return;
    }

    callback.fetchSuccess(auditListItemData);
  }
}
