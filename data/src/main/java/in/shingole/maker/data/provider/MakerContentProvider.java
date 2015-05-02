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

import in.shingole.maker.data.sqlite.Tables;
import in.shingole.maker.data.sqlite.WorksheetSQLiteHelper;

public class MakerContentProvider extends ContentProvider {

  WorksheetSQLiteHelper dbHelper;

  // Keep in sync with getType method .
  enum WorksheetURIMatchingCodes {
    NO_MATCH,
    WORKSHEETS,
    WORKSHEET
  }

  private final ThreadLocal<Boolean> mIsInBatchMode = new ThreadLocal<Boolean>();
  private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    uriMatcher.addURI(MakerContentProviderContract.AUTHORITY,
        MakerContentProviderContract.WORKSHEETS_CONTENT,
        WorksheetURIMatchingCodes.WORKSHEETS.ordinal());
    uriMatcher.addURI(MakerContentProviderContract.AUTHORITY,
        MakerContentProviderContract.WORKSHEETS_CONTENT + "/#",
        WorksheetURIMatchingCodes.WORKSHEET.ordinal());
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
      case 1:
        return MakerContentProviderContract.WORKSHEETS_CONTENT;
      case 2:
        return MakerContentProviderContract.WORKSHEET_ITEM_CONTENT_TYPE;
    }
    return null;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    Uri resourceUri = null;
    switch (uriMatcher.match(uri)) {
      case 1:
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.insertOrThrow(Tables.WorksheetTable.TABLE_NAME, null, values);
        if (result > 0) {
          resourceUri = ContentUris.withAppendedId(uri, result);
        }
      default:
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
      case 1:
        qb.setTables(Tables.WorksheetTable.TABLE_NAME);
        if (TextUtils.isEmpty(sortOrder)) {
          sortOrder = Tables.WorksheetTable.COL_DATE_CREATED
              + " DESC";
        }
        logQuery(qb, projection, selection, sortOrder);
        cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        break;
      case 2:
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
      getContext().getContentResolver().notifyChange(MakerContentProviderContract.CONTENT_URI,
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
      Log.v("maker", "query: " + builder.buildQuery(projection, selection, null, null, sortOrder, null));
    }
  }
}
