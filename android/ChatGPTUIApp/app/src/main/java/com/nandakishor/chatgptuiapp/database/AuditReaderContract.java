package com.nandakishor.chatgptuiapp.database;

import android.provider.BaseColumns;

import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.COLUMN_NAME_DATETIME;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.COLUMN_NAME_PROMPT;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.COLUMN_NAME_RESPONSE;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.COLUMN_NAME_SEQNO;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.PROMPT_TABLE_NAME;
import static com.nandakishor.chatgptuiapp.database.AuditReaderContract.AuditPromptEntry.RESPONSE_TABLE_NAME;

public class AuditReaderContract {
  private AuditReaderContract() {}

  public static class AuditPromptEntry implements BaseColumns {
    public static final String PROMPT_TABLE_NAME = "AuditPrompt";
    public static final String RESPONSE_TABLE_NAME = "AuditResponse";
    public static final String COLUMN_NAME_SEQNO = "seqno";
    public static final String COLUMN_NAME_PROMPT = "prompt";
    public static final String COLUMN_NAME_DATETIME = "datetime";
    public static final String COLUMN_NAME_RESPONSE = "response";
  }

  public static final String SQL_CREATE_PROMPT_ENTRIES =
      String.format("CREATE TABLE %s (%s INTEGER, %s TEXT, %s TEXT)",
          PROMPT_TABLE_NAME, COLUMN_NAME_SEQNO, COLUMN_NAME_PROMPT, COLUMN_NAME_DATETIME);

  public static final String SQL_CREATE_RESPONSE_ENTRIES =
      String.format("CREATE TABLE %s (%s INTEGER, %s TEXT, %s TEXT)",
          RESPONSE_TABLE_NAME, COLUMN_NAME_SEQNO, COLUMN_NAME_RESPONSE, COLUMN_NAME_DATETIME);

  public static final String SQL_DELETE_PROMPT_ENTRIES =
      String.format("DROP TABLE IF EXISTS %s", PROMPT_TABLE_NAME);

  public static final String SQL_DELETE_RESPONSE_ENTRIES =
      String.format("DROP TABLE IF EXISTS %s", RESPONSE_TABLE_NAME);

  public static final String READ_ALL_DATA =
      String.format("SELECT AuditPrompt.seqno, AuditPrompt.prompt, AuditPrompt.datetime, AuditResponse.response FROM AuditPrompt INNER JOIN AuditResponse " +
          "ON AuditPrompt.seqno = AuditResponse.seqno");

}
