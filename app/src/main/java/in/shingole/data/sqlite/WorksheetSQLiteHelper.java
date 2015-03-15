package in.shingole.data.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * SQLiteHelper for creating db.
 */
public class WorksheetSQLiteHelper extends SQLiteOpenHelper {

  // If you change the database schema, you must increment the database version.
  public static final int DATABASE_VERSION = 1;

  // The name of the database file on the file system
  public static final String DATABASE_NAME = "SheetMaker.db";

  public WorksheetSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(QuestionTable.SQL_CREATE_TABLE);
    db.execSQL(WorksheetTable.SQL_CREATE_TABLE);
    db.execSQL(WorksheetQuestionsTable.SQL_CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
