package in.shingole.data.sqlite;

/**
 * Worksheet table.
 */
public class WorksheetTable implements StandardColumns {
  public static final String TABLE_NAME = "worksheets";

  public static final String COL_NAME = "name";
  public static final String COL_DESC = "desc";
  public static final String COL_CATEGORY = "category";

  public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
      + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT "
      + ", " + COL_CATEGORY + COL_TYPE_TEXT
      + ", " + COL_NAME + COL_TYPE_TEXT
      + ", " + COL_DESC + COL_TYPE_TEXT
      + ", " + COL_DATE_CREATED + COL_TYPE_TEXT
      + ", " + COL_LAST_UPDATED + COL_TYPE_TEXT
      + ");";

}
