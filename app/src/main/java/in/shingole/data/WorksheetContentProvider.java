package in.shingole.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import in.shingole.data.model.TestData;

public class WorksheetContentProvider extends ContentProvider {

  // Keep in sync with getType method .
  enum WorksheetURIMatchingCodes {
    NO_MATCH,
    WORKSHEETS_CODE,
    WORKSHEET_ID;
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

  public WorksheetContentProvider() {
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
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {
    switch (uriMatcher.match(uri)) {
      case 1:
        if (TextUtils.isEmpty(sortOrder)) sortOrder = "_ID ASC";
        TestData.sampleWorksheet();
        break;
      case 2:
        selection = selection + "_ID = " + uri.getLastPathSegment();
        break;
    }
    return null;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {
    // TODO: Implement this to handle requests to update one or more rows.
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
