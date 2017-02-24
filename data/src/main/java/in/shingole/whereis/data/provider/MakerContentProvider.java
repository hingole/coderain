package in.shingole.whereis.data.provider;

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
import android.os.Debug;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import in.shingole.whereis.data.BuildConfig;
import in.shingole.whereis.data.sqlite.Tables;
import in.shingole.whereis.data.sqlite.WorksheetSQLiteHelper;

public class MakerContentProvider extends ContentProvider {

  WorksheetSQLiteHelper dbHelper;

  private static final int USERS = 101;
  private static final int USER_ID = 102;

  private final ThreadLocal<Boolean> mIsInBatchMode = new ThreadLocal<>();
  private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    uriMatcher.addURI(ContentProviderContract.AUTHORITY,
        ContentProviderContract.PATH_USER,
        USERS);
    uriMatcher.addURI(ContentProviderContract.AUTHORITY,
        ContentProviderContract.PATH_USER + "/#",
        USER_ID);
  }

  @Override
  public int delete(@NonNull  Uri uri, String selection, String[] selectionArgs) {
    // Implement this to handle requests to delete one or more rows.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public String getType(@NonNull Uri uri) {
    // Keep in sync with WorksheetURIMatchingCodes enum.
    switch (uriMatcher.match(uri)) {
      case USERS:
        return ContentProviderContract.User.CONTENT_TYPE;
      case USER_ID:
        return ContentProviderContract.User.CONTENT_ITEM_TYPE;
    }
    return null;
  }

  @Override
  public Uri insert(@NonNull Uri uri, ContentValues values) {
    if (BuildConfig.TRACING_ENABLED) {
      Debug.startMethodTracing("MakerContentProvider.insert");
    }

    Uri resourceUri = null;
    long result;
    switch (uriMatcher.match(uri)) {
      case USERS:
        result = dbHelper.getWritableDatabase().insertOrThrow(
            Tables.UserTable.TABLE_NAME, null, values);
        if (result > 0) {
          resourceUri = ContentUris.withAppendedId(uri, result);
        }
        break;
      default:
        return null;
    }
    if (!isInBatchMode() && resourceUri != null && getContext() != null &&
        getContext().getContentResolver() != null) {
      getContext().getContentResolver().notifyChange(
          resourceUri,
          null);
    }
    if (BuildConfig.TRACING_ENABLED) {
      Debug.stopMethodTracing();
    }
    return resourceUri;
  }

  @Override
  public boolean onCreate() {
    if (BuildConfig.TRACING_ENABLED) {
      Debug.startMethodTracing("MakerContentProvider.onCreate");
    }

    dbHelper = new WorksheetSQLiteHelper(super.getContext());

    if (BuildConfig.TRACING_ENABLED) {
      Debug.stopMethodTracing();
    }

    return true;
  }

  @Override
  public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {
    if (BuildConfig.TRACING_ENABLED) {
      Debug.startMethodTracing("MakerContentProvider.query");
    }

    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    Cursor cursor = null;
    switch (uriMatcher.match(uri)) {
      case USERS:
        qb.setTables(Tables.UserTable.TABLE_NAME);
        if (TextUtils.isEmpty(sortOrder)) {
          sortOrder = Tables.UserTable.COL_DATE_CREATED
              + " DESC";
        }
        logQuery(qb, projection, selection, sortOrder);
        cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        break;
    }
    if (BuildConfig.TRACING_ENABLED) {
      Debug.stopMethodTracing();
    }

    return cursor;
  }

  @Override
  public int update(@NonNull Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {
    // TODO: Implement this to handle requests to update one or more rows.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  @NonNull
  public ContentProviderResult[] applyBatch(
      @NonNull ArrayList<ContentProviderOperation> operations)
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
      if (getContext() != null && getContext().getContentResolver() != null) {
        getContext().getContentResolver().notifyChange(
            ContentProviderContract.User.CONTENT_URI,
            null);
      }
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

  private void logQuery(SQLiteQueryBuilder builder, String[] projection, String selection,
                        String sortOrder) {
    if (BuildConfig.DEBUG) {
      Log.v(MakerContentProvider.class.getName(),
          "query: " + builder.buildQuery(projection, selection, null, null, sortOrder, null));
    }
  }
}
