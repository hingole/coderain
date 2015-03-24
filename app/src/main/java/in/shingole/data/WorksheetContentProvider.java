package in.shingole.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.google.common.collect.Lists;

import java.util.ArrayList;

import in.shingole.data.sqlite.Tables;
import in.shingole.data.sqlite.WorksheetSQLiteHelper;

public class WorksheetContentProvider extends ContentProvider {

  WorksheetSQLiteHelper dbHelper;

  // Keep in sync with getType method .
  enum WorksheetURIMatchingCodes {
    NO_MATCH,
    WORKSHEETS_CODE,
    WORKSHEET_ID
  }


  private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    uriMatcher.addURI(WorksheetContentProviderContract.AUTHORITY,
        WorksheetContentProviderContract.WORKSHEETS_CONTENT,
        WorksheetURIMatchingCodes.WORKSHEETS_CODE.ordinal());
    uriMatcher.addURI(WorksheetContentProviderContract.AUTHORITY,
        WorksheetContentProviderContract.CONTENT_URI + "/#",
        WorksheetURIMatchingCodes.WORKSHEET_ID.ordinal());
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
        return WorksheetContentProviderContract.WORKSHEETS_CONTENT;
      case 2:
        return WorksheetContentProviderContract.WORKSHEET_ITEM_CONTENT_TYPE;
    }
    return null;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    // TODO: Implement this to handle requests to insert a new row.
    throw new UnsupportedOperationException("Not yet implemented");
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
        cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        break;
      case 2:
        ArrayList<String> selectionArgList = selectionArgs == null
            ? Lists.<String>newArrayList()
            : Lists.newArrayList(selectionArgs);
        selectionArgList.add(uri.getLastPathSegment());
        qb.setTables(Tables.WorksheetTable.TABLE_NAME);
        qb.appendWhere(Tables.WorksheetTable._ID + " = ? ");
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
}
