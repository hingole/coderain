package in.shingole.whereis.data.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import in.shingole.whereis.data.sqlite.Tables;

/**
 * Contract for WorksheetContentProvider
 */
public class ContentProviderContract {
  static final String AUTHORITY ="in.shingole.whereis.provider";
  static final String PATH_USER ="user";


  private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  /**
   * Contract for User data type.
   */
  public static class User {
    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_DATE_CREATED = Tables.UserTable.COL_DATE_CREATED;
    public static final String COL_LAST_UPDATED = Tables.UserTable.COL_LAST_UPDATED;
    public static final String COL_USER_ID = Tables.UserTable.COL_USER_ID;
    public static final String COL_USER_NAME = Tables.UserTable.COL_USER_NAME;
    public static final String COL_PROFILE_PHOTO = Tables.UserTable.COL_PROFILE_PHOTO;
    public static final String COL_USER_EMAIL = Tables.UserTable.COL_USER_EMAIL;
    public static final String COL_LOGIN_PROVIDER = Tables.UserTable.COL_LOGIN_PROVIDER;

    /**
     * Content Uri for User resource.
     * content://in.shingole.whereis.provider/user
     */
    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

    /**
     * Content type for list of worksheets
     * vnd.android.cursor.dir/vnd.in.shingole.whereis.provider.user
     */
    static final String CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_USER;

    /**
     * Content type for single question item
     * vnd.android.cursor.item/vnd.in.shingole.whereis.provider.user
     */
    static final String CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_USER;
  }
}
