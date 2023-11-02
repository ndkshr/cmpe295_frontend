package com.nandakishor.chatgptuiapp.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import android.util.Log;

import com.google.gson.Gson;
import com.nandakishor.chatgptuiapp.models.GptResponse;
import com.nandakishor.chatgptuiapp.models.Message;
import com.nandakishor.chatgptuiapp.models.RequestBody;

public class RemoteResponseTask extends AsyncTask<String, Integer, GptResponse> {

  public interface ResponseCallback {
    void responseSuccess(GptResponse res);
    void responseFailed(String msg);

    void progress(boolean loading);
  }

  String prompt = "";
  private static final String API_URL = "https://api.openai.com/v1/chat/completions";

  private static final String API_KEY = "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";
  private static final String MODEL = "gpt-3.5-turbo";
  private static final double TEMPERATURE = 0.7;

  private static final String TAG = "RemoteResponseTask";

  GptResponse gptResponse;

  StringBuilder sb = new StringBuilder();

  ResponseCallback callback;

  public RemoteResponseTask(WeakReference<ResponseCallback> callback) {
    this.callback = callback.get();
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected GptResponse doInBackground(String... strings) {
    publishProgress(0);
    Log.d(TAG, "Async Task starting");
    try {
      URL url = new URL(API_URL);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
      conn.setDoOutput(true);

      Gson gson = new Gson();
      Message msg = new Message("user", prompt);
      List<Message> messageList = new ArrayList<>();
      messageList.add(msg);
      RequestBody body = new RequestBody(MODEL, messageList, TEMPERATURE);
      String bodyJson = gson.toJson(body);

      // Write body to input
      try (OutputStream os = conn.getOutputStream()) {
        byte[] input = bodyJson.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }

      conn.connect();

      // Read response from output
      try (BufferedReader br = new BufferedReader((new InputStreamReader((conn.getInputStream()))))) {
        String response;
        while ((response = br.readLine()) != null) {
          sb.append(response);
        }
      }

      gptResponse = gson.fromJson(sb.toString(), GptResponse.class);

    } catch (Exception e) {
      Log.d(TAG, e.toString());
      return null;

    }
    Log.d(TAG, "Async Task finished");

    return gptResponse;
  }

  @Override
  protected void onProgressUpdate(Integer... values) {
    super.onProgressUpdate(values);
    callback.progress(true);
  }

  @Override
  protected void onPostExecute(GptResponse res) {
    super.onPostExecute(res);
    if (res == null) {
      callback.responseFailed("Response Failed");
      return;
    }

    Log.d(TAG, "Response = \n" + sb.toString());
    callback.responseSuccess(gptResponse);
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }
}
