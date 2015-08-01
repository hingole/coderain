package in.shingole.maker.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Debug;
import android.text.TextUtils;

import com.google.common.collect.ImmutableList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.shingole.maker.data.BuildConfig;
import in.shingole.maker.data.model.Question;
import in.shingole.maker.data.model.TestData;
import in.shingole.maker.data.model.Worksheet;

/**
 * SQLiteHelper for creating db.
 */
public class WorksheetSQLiteHelper extends SQLiteOpenHelper {

  // If you change the database schema, you must increment the database version.
  public static final int DATABASE_VERSION = 2;

  // The name of the database file on the file system
  public static final String DATABASE_NAME = "SheetMaker.db";
  private java.text.DateFormat df;

  public WorksheetSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    if (BuildConfig.TRACING_ENABLED) {
      Debug.startMethodTracing("WorksheetSQLiteHelper.onCreate");
    }
    //TODO: Remove drop table from production version.
    db.execSQL("DROP TABLE IF EXISTS " + Tables.WorksheetQuestionsTable.TABLE_NAME + ";");
    db.execSQL("DROP TABLE IF EXISTS " + Tables.WorksheetTable.TABLE_NAME + ";");
    db.execSQL("DROP TABLE IF EXISTS " + Tables.QuestionTable.TABLE_NAME + ";");
    db.execSQL("DROP TABLE IF EXISTS " + Tables.UserTable.TABLE_NAME + ";");

    db.execSQL(Tables.UserTable.SQL_CREATE_TABLE);
    db.execSQL(Tables.QuestionTable.SQL_CREATE_TABLE);
    db.execSQL(Tables.WorksheetTable.SQL_CREATE_TABLE);
    db.execSQL(Tables.WorksheetQuestionsTable.SQL_CREATE_TABLE);
    if (BuildConfig.TRACING_ENABLED) {
      Debug.stopMethodTracing();
    }
  }

  public void onOpen(SQLiteDatabase db) {
    if (BuildConfig.TRACING_ENABLED) {
      Debug.startMethodTracing("WorksheetSQLiteHelper.insertTestData");
    }

    //insertTestData(db);

    if (BuildConfig.TRACING_ENABLED) {
      Debug.stopMethodTracing();
    }
  }

  public void insertTestData(SQLiteDatabase db) {
    if (db.isReadOnly()) {
      return;
    }
    List<Worksheet> worksheets = TestData.sampleWorksheet();
    for (Worksheet worksheet : worksheets) {
      long worksheetId = insertWorksheet(db, worksheet);
      worksheet.setId(Long.toString(worksheetId));
      for (Question question : worksheet.getQuestionList()) {
        long questionId = insertQuestion(db, question);
        question.setId((Long.toString(questionId)));
      }
      insertWorksheetQuestions(db, worksheet);
    }
  }

  void insertWorksheetQuestions(SQLiteDatabase db, Worksheet sheet) {
    if (BuildConfig.TRACING_ENABLED) {
      Debug.startMethodTracing("WorksheetSQLiteHelper.insertWorksheetQuestions");
    }

    String query = createInsertQuery(Tables.WorksheetQuestionsTable.TABLE_NAME,
        Tables.WorksheetQuestionsTable.COL_WORKSHEET_ID,
        Tables.WorksheetQuestionsTable.COL_QUESTION_ID);
    SQLiteStatement stmt = db.compileStatement(query);
    Long sheetId = Long.valueOf(sheet.getId());
    for (Question question : sheet.getQuestionList()) {
      stmt.bindLong(1, sheetId);
      Long questionId = Long.valueOf(question.getId());
      stmt.bindLong(2, questionId);
      Long id = stmt.executeInsert();
      question.setId(Long.toString(id));
    }
    if (BuildConfig.TRACING_ENABLED) {
      Debug.stopMethodTracing();
    }
  }

  Long insertWorksheet(SQLiteDatabase db, Worksheet sheet) {
    if (BuildConfig.TRACING_ENABLED) {
      Debug.startMethodTracing("WorksheetSQLiteHelper.insertWorksheet");
    }

    List<String> columns = ImmutableList.of(Tables.WorksheetTable.COL_NAME, Tables.WorksheetTable.COL_CATEGORY,
        Tables.WorksheetTable.COL_DESC, Tables.WorksheetTable.COL_DATE_CREATED);
    SQLiteStatement stmt = db.compileStatement("INSERT INTO " + Tables.WorksheetTable.TABLE_NAME +
        " ( " + TextUtils.join(",", columns) + " ) values (? , ? , ? , ?)");
    stmt.bindString(1, sheet.getName());
    stmt.bindString(2, sheet.getCategory());
    stmt.bindString(3, sheet.getDescription());
    stmt.bindString(4, getDateFormat().format(new Date()));
    long returnValue = stmt.executeInsert();
    if (BuildConfig.TRACING_ENABLED) {
      Debug.stopMethodTracing();
    }
    return returnValue;
  }

  Long insertQuestion(SQLiteDatabase db, Question question) {
    if (BuildConfig.TRACING_ENABLED) {
      Debug.startMethodTracing("WorksheetSQLiteHelper.insertQuestion");
    }

    String query = createInsertQuery(Tables.QuestionTable.TABLE_NAME,
        Tables.QuestionTable.COL_CATEGORY,
        Tables.QuestionTable.COL_TYPE,
        Tables.QuestionTable.COL_SHORT_DESC,
        Tables.QuestionTable.COL_LONG_DESC,
        Tables.QuestionTable.COL_DATE_CREATED,
        Tables.QuestionTable.COL_DIFFICULTY_LEVEL,
        Tables.QuestionTable.COL_HINT,
        Tables.QuestionTable.COL_ANSWER_TYPE,
        Tables.QuestionTable.COL_IS_MARKED_FOR_DELETION,
        Tables.QuestionTable.COL_LAST_UPDATED,
        Tables.QuestionTable.COL_MAX_ATTEMPTS,
        Tables.QuestionTable.COL_SHORT_ANSWER,
        Tables.QuestionTable.COL_LONG_ANSWER,
        Tables.QuestionTable.COL_MULTI_CHOICE_OPTIONS);
    SQLiteStatement stmt = db.compileStatement(query);
    int colIndex = 1;

    bindStringOrNull(stmt, colIndex++, question.getCategory());
    stmt.bindLong(colIndex++, question.getType().ordinal());
    bindStringOrNull(stmt, colIndex++, question.getShortDescription());
    bindStringOrNull(stmt, colIndex++, question.getLongDescription());
    bindDateOrNull(stmt, colIndex++, question.getDateCreated());
    stmt.bindLong(colIndex++, question.getDifficultyLevel().ordinal());
    bindStringOrNull(stmt, colIndex++, question.getHint());
    stmt.bindLong(colIndex++, question.getAnswerType().ordinal());
    stmt.bindLong(colIndex++, question.isMarkedForDeletion() ? 1L : 0L);
    bindDateOrNull(stmt, colIndex++, question.getLastUpdated());
    stmt.bindLong(colIndex++, question.getMaxAttempts());
    bindStringOrNull(stmt, colIndex++, question.getShortAnswer());
    bindStringOrNull(stmt, colIndex++, question.getLongAnswer());
    String multipleChoice = question.getMultipleChoiceOptions() == null
        ? null
        : TextUtils.join("::", question.getMultipleChoiceOptions());
    bindStringOrNull(stmt, colIndex++, multipleChoice);
    long returnValue = stmt.executeInsert();
    if (BuildConfig.TRACING_ENABLED) {
      Debug.stopMethodTracing();
    }
    return returnValue;
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
      df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
    }
    return df;
  }
}
