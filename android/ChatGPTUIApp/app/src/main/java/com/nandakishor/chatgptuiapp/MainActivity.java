package com.nandakishor.chatgptuiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nandakishor.chatgptuiapp.database.AuditReaderDBHelper;
import com.nandakishor.chatgptuiapp.tasks.LocalDatabaseTask;
import com.nandakishor.chatgptuiapp.tasks.RemoteResponseTask;
import com.nandakishor.chatgptuiapp.views.AuditListAdapter;
import com.nandakishor.chatgptuiapp.models.AuditListItemData;
import com.nandakishor.chatgptuiapp.models.GptResponse;
import com.nandakishor.chatgptuiapp.views.Typewriter;

import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements RemoteResponseTask.ResponseCallback, LocalDatabaseTask.DatabaseCallback {

  EditText prompt;
  Typewriter response;
  Button sendBtn;
  Button cancelBtn;
  RecyclerView auditItemsRv;
  AuditListAdapter adapter;

  AuditReaderDBHelper dbHelper;
  RemoteResponseTask remoteResponseTask;
  LocalDatabaseTask localDatabaseTask;

  Button saveAuditBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    prompt = findViewById(R.id.prompt_input);
    response = findViewById(R.id.response_txt);
    response.setCharacterDelay(100);
    sendBtn = findViewById(R.id.send_btn);
    cancelBtn = findViewById(R.id.cancel_btn);
    saveAuditBtn = findViewById(R.id.save_audit_btn);

    sendBtn.setOnClickListener(view -> {
      hitOpenAIAPI();
    });

    cancelBtn.setOnClickListener(view -> {
      if (remoteResponseTask != null) {
        remoteResponseTask.cancel(true);
      }
      Toast.makeText(this, "GPT Request Cancelled!", Toast.LENGTH_SHORT).show();
    });

    saveAuditBtn.setOnClickListener(view -> {
      auditItemsRv.scrollToPosition(adapter.getItemCount() - 1);
    });

    adapter = new AuditListAdapter();
    auditItemsRv = findViewById(R.id.auditRv);
    auditItemsRv.setAdapter(adapter);
    auditItemsRv.setLayoutManager(new LinearLayoutManager(this));


    dbHelper = new AuditReaderDBHelper(this);

    hitSQLiteContent(null);
  }

  private void hitSQLiteContent(AuditListItemData item) {
//    if (item == null) return;

    if (localDatabaseTask != null && remoteResponseTask.getStatus() == AsyncTask.Status.RUNNING) {
      localDatabaseTask.cancel(true);
    }
    localDatabaseTask = new LocalDatabaseTask(new WeakReference<>(dbHelper), item, this);
    localDatabaseTask.execute();
  }

  private void hitOpenAIAPI() {
    if (remoteResponseTask != null && remoteResponseTask.getStatus() == AsyncTask.Status.RUNNING) {
      Toast.makeText(this, "Process already running, hit Cancel", Toast.LENGTH_SHORT).show();
      return;
    }

    remoteResponseTask = new RemoteResponseTask(new WeakReference<>(this));
    remoteResponseTask.setPrompt(prompt.getText().toString());
    remoteResponseTask.execute();
  }

  @Override
  public void responseSuccess(GptResponse response) {
    String gptResponseText = response.getChoices().get(0).getMessage().getContent();
    this.response.animateText(gptResponseText);

    String uuid = UUID.randomUUID().toString();

    AuditListItemData item = new AuditListItemData(uuid, prompt.getText().toString(),
        gptResponseText,
        LocalDateTime.now().toString());
    hitSQLiteContent(item);
  }

  @Override
  public void responseFailed(String errorMessage) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void progress(boolean loader) {
    if (loader) {
      this.response.animateText("Loading...");
    }
  }

  @Override
  protected void onDestroy() {
    dbHelper.close();
    super.onDestroy();
  }

  @Override
  public void fetchSuccess(List<AuditListItemData> updatedItems) {
    adapter.setData(updatedItems);
  }

  @Override
  public void fetchFailed(String errorMsg) {
    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
  }
}