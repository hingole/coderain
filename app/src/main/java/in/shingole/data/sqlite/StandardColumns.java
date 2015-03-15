package in.shingole.data.sqlite;

import android.provider.BaseColumns;

/**
 * Standard columns for all entities.
 */
public interface StandardColumns extends BaseColumns {
  public static final String COL_DATE_CREATED = "date_created";
  public static final String COL_LAST_UPDATED = "last_updated";
  public static final String COL_IS_MARKED_FOR_DELETION = "marked_for_deletion";

  public static final String COL_TYPE_TEXT = " TEXT ";
  public static final String COL_TYPE_NUMERIC = " NUM ";
  public static final String COL_TYPE_INTEGER = " INT ";
  public static final String COL_TYPE_REAL = " REAL ";
}
