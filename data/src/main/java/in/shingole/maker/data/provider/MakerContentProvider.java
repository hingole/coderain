package in.shingole.maker.data.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.v7.appcompat.BuildConfig;
import android.text.TextUtils;
import android.util.Log;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import in.shingole.maker.data.sqlite.Tables;
import in.shingole.maker.data.sqlite.WorksheetSQLiteHelper;

public class MakerContentProvider extends ContentProvider {

  WorksheetSQLiteHelper dbHelper;

  private static final int WORKSHEETS = 100;
  private static final int WORKSHEET_ID = 101;
  private static final int WORKSHEET_ID_QUESTIONS = 102;
  private static final int QUESTIONS = 103;
  private static final int QUESTION_ID = 104;

  private final ThreadLocal<Boolean> mIsInBatchMode = new ThreadLocal<Boolean>();
  private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    uriMatcher.addURI(MakerContentProviderContract.AUTHORITY,
        MakerContentProviderContract.PATH_QUESTION,
        QUESTIONS);
    uriMatcher.addURI(MakerContentProviderContract.AUTHORITY,
        MakerContentProviderContract.PATH_QUESTION + "/#",
        QUESTION_ID);

    uriMatcher.addURI(MakerContentProviderContract.AUTHORITY,
        MakerContentProviderContract.PATH_WORKSHEET,
        WORKSHEETS);
    uriMatcher.addURI(MakerContentProviderContract.AUTHORITY,
        MakerContentProviderContract.PATH_WORKSHEET + "/#",
        WORKSHEET_ID);
    uriMatcher.addURI(MakerContentProviderContract.AUTHORITY,
        MakerContentProviderContract.PATH_WORKSHEET + "/#/"
            + MakerContentProviderContract.PATH_QUESTION,
        WORKSHEET_ID_QUESTIONS);
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    // Implement this to handle requests to delete one or more rows.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public String getType(Uri uri) {
    // Keep in sync with WorksheetURIMatchingCodes enum.
    switch (uriMatcher.match(uri)) {
      case WORKSHEETS:
        return MakerContentProviderContract.Worksheet.CONTENT_TYPE;
      case WORKSHEET_ID:
        return MakerContentProviderContract.Worksheet.CONTENT_ITEM_TYPE;
      case WORKSHEET_ID_QUESTIONS:
      case QUESTIONS:
        return MakerContentProviderContract.Question.CONTENT_TYPE;
      case QUESTION_ID:
        return MakerContentProviderContract.Question.CONTENT_ITEM_TYPE;
    }
    return null;
  }

  private String getWorksheetIdSegment(Uri uri) {
    List<String> segments = uri.getPathSegments();
    assert(segments.size() > 2);
    return segments.get(segments.size() - 2);
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    Uri resourceUri = null;
    long result = 0;
    switch (uriMatcher.match(uri)) {
      case QUESTIONS:
        result = dbHelper.getWritableDatabase().insertOrThrow(
            Tables.QuestionTable.TABLE_NAME, null, values);
        if (result > 0) {
          resourceUri = ContentUris.withAppendedId(uri, result);
        }

        break;
      case WORKSHEET_ID_QUESTIONS:
        String worksheetId = getWorksheetIdSegment(uri);
        result = dbHelper.getWritableDatabase().insertOrThrow(
            Tables.QuestionTable.TABLE_NAME, null, values);
        if (result > 0) {
          ContentValues worksheetQuestionValues = new ContentValues();
          worksheetQuestionValues.put(Tables.WorksheetQuestionsTable.COL_WORKSHEET_ID, worksheetId);
          worksheetQuestionValues.put(Tables.WorksheetQuestionsTable.COL_QUESTION_ID, result);
          long worksheetQuestionId = dbHelper.getWritableDatabase().insertOrThrow(
              Tables.WorksheetQuestionsTable.TABLE_NAME, null, worksheetQuestionValues);
          if (worksheetQuestionId > 0) {
            resourceUri = ContentUris.withAppendedId(uri, worksheetQuestionId);
          }
        }
        break;
      case WORKSHEETS:
        result = dbHelper.getWritableDatabase().insertOrThrow(
            Tables.WorksheetTable.TABLE_NAME, null, values);
        if (result > 0) {
          resourceUri = ContentUris.withAppendedId(uri, result);
        }
        break;
      default:
        return null;
    }
    if (!isInBatchMode() && resourceUri != null) {
      getContext().getContentResolver().notifyChange(
          resourceUri,
          null);
    }
    return resourceUri;
  }

  @Override
  public boolean onCreate() {
    dbHelper = new WorksheetSQLiteHelper(super.getContext());
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    Cursor cursor = null;
    switch (uriMatcher.match(uri)) {
      case WORKSHEET_ID_QUESTIONS: {
        String worksheetId = getWorksheetIdSegment(uri);
        qb.setTables(Tables.QuestionTable.TABLE_NAME
            + " join "
            + Tables.WorksheetQuestionsTable.TABLE_NAME
            + " on (" + Tables.QuestionTable.TABLE_NAME + "." + Tables.QuestionTable._ID
            + " = "
            + Tables.WorksheetQuestionsTable.TABLE_NAME + "."
            + Tables.WorksheetQuestionsTable.COL_QUESTION_ID + " ) ");
        String questionsSelection =
            (TextUtils.isEmpty(selection) ? "" : selection + " and ")
            + Tables.WorksheetQuestionsTable.TABLE_NAME
            + "." + Tables.WorksheetQuestionsTable.COL_WORKSHEET_ID
            + " = ?";
        ArrayList<String> selectionArgList = selectionArgs == null
            ? Lists.<String>newArrayList()
            : Lists.newArrayList(selectionArgs);
        selectionArgList.add(worksheetId);

        if (TextUtils.isEmpty(sortOrder)) {
          sortOrder = Tables.WorksheetTable.COL_DATE_CREATED
              + " DESC";
        }
        logQuery(qb, projection, questionsSelection, sortOrder);
        cursor = qb.query(db,
            projection,
            questionsSelection,
            selectionArgList.toArray(new String[selectionArgList.size()]),
            null,
            null,
            sortOrder);
        break;
      }
      case WORKSHEETS:
        qb.setTables(Tables.WorksheetTable.TABLE_NAME);
        if (TextUtils.isEmpty(sortOrder)) {
          sortOrder = Tables.WorksheetTable.COL_DATE_CREATED
              + " DESC";
        }
        logQuery(qb, projection, selection, sortOrder);
        cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        break;
      case WORKSHEET_ID:
        ArrayList<String> selectionArgList = selectionArgs == null
            ? Lists.<String>newArrayList()
            : Lists.newArrayList(selectionArgs);
        selectionArgList.add(uri.getLastPathSegment());
        qb.setTables(Tables.WorksheetTable.TABLE_NAME);
        qb.appendWhere(Tables.WorksheetTable._ID + " = ? ");
        logQuery(qb, projection, selection, sortOrder);
        cursor = qb.query(db,
            projection,
            selection,
            selectionArgList.toArray(new String[selectionArgList.size()]),
            null,
            null,
            null);
        break;
    }
    return cursor;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {
    // TODO: Implement this to handle requests to update one or more rows.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public ContentProviderResult[] applyBatch(
      ArrayList<ContentProviderOperation> operations)
      throws OperationApplicationException {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    mIsInBatchMode.set(true);
    // the next line works because SQLiteDatabase
    // uses a thread local SQLiteSession object for
    // all manipulations
    db.beginTransaction();
    try {
      final ContentProviderResult[] retResult = super.applyBatch(operations);
      db.setTransactionSuccessful();
      getContext().getContentResolver().notifyChange(
          MakerContentProviderContract.Worksheet.CONTENT_URI,
          null);
      return retResult;
    }
    finally {
      mIsInBatchMode.remove();
      db.endTransaction();
    }
  }

  private boolean isInBatchMode() {
    return mIsInBatchMode.get() != null && mIsInBatchMode.get();
  }


  private void logQuery(SQLiteQueryBuilder builder, String[] projection, String selection, String sortOrder) {
    if (BuildConfig.DEBUG) {
      Log.v(MakerContentProvider.class.getName(),
          "query: " + builder.buildQuery(projection, selection, null, null, sortOrder, null));
    }
  }
}
