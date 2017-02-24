package in.shingole.whereis.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Debug;
import android.text.TextUtils;

import java.text.DateFormat;
import java.util.Date;

import in.shingole.whereis.data.BuildConfig;


/**
 * SQLiteHelper for creating db.
 */
public class WorksheetSQLiteHelper extends SQLiteOpenHelper {

  // If you change the database schema, you must increment the database version.
  private static final int DATABASE_VERSION = 1;

  // The name of the database file on the file system
  private static final String DATABASE_NAME = "WhereIs.db";
  private java.text.DateFormat df;

  public WorksheetSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    if (BuildConfig.TRACING_ENABLED) {
      Debug.startMethodTracing("WorksheetSQLiteHelper.onCreate");
    }
    if (BuildConfig.RESET_DB_ONSTART) {
      db.execSQL("DROP TABLE IF EXISTS " + Tables.UserTable.TABLE_NAME + ";");
    }
    db.execSQL(Tables.UserTable.SQL_CREATE_TABLE);
    if (BuildConfig.TRACING_ENABLED) {
      Debug.stopMethodTracing();
    }
  }

  public void onOpen(SQLiteDatabase db) {
  }

  private void bindStringOrNull(SQLiteStatement stmt, int colIndex, String value) {
    if (value != null) {
      stmt.bindString(colIndex, value);
    } else {
      stmt.bindNull(colIndex);
    }
  }

  private void bindDateOrNull(SQLiteStatement stmt, int colIndex, Date date) {
    if (date != null) {
      stmt.bindString(colIndex, getDateFormat().format(date));
    } else {
      stmt.bindNull(colIndex);
    }
  }

  String createInsertQuery(String tableName, String ... columnNames) {
    StringBuilder builder = new StringBuilder();
    builder.append("INSERT INTO ")
        .append(tableName);
    if (columnNames.length > 0) {
      builder.append(" (");
    }
    builder.append(TextUtils.join(",", columnNames));
    if (columnNames.length > 0) {
      builder.append(" ) values ( ");
      for (int i = 0; i < columnNames.length; i++) {
        if (i > 0) {
          builder.append(", ");
        }
        builder.append(" ? ");
      }
      builder.append(")");
    }
    return builder.toString();
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //TODO:Fix this so that we don't delete old data.
    onCreate(db);
  }

  private DateFormat getDateFormat() {
    if (df == null) {
      df = DateFormat.getDateTimeInstance();
    }
    return df;
  }
}
