package in.shingole.whereis.data.sqlite;

/**
 * Table schema definitions.
 */
public class Tables {
  /**
   * User table
   */
  public static class UserTable implements StandardColumns {

    public static final String TABLE_NAME = "users";

    public static final String COL_USER_ID = "user_id";
    public static final String COL_USER_NAME = "user_name";
    public static final String COL_PROFILE_PHOTO = "user_profile_photo";
    public static final String COL_USER_EMAIL = "user_email";
    public static final String COL_LOGIN_PROVIDER = "login_provider";

    static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
        + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
        + ", " + COL_USER_ID + COL_TYPE_TEXT
        + ", " + COL_LOGIN_PROVIDER + COL_TYPE_TEXT
        + ", " + COL_USER_NAME + COL_TYPE_TEXT
        + ", " + COL_PROFILE_PHOTO + COL_TYPE_TEXT
        + ", " + COL_USER_EMAIL + COL_TYPE_TEXT
        + ", " + COL_DATE_CREATED + COL_TYPE_TEXT
        + ", " + COL_LAST_UPDATED + COL_TYPE_TEXT
        + ", " + COL_IS_MARKED_FOR_DELETION + COL_TYPE_INTEGER
        + ");";
  }

}
