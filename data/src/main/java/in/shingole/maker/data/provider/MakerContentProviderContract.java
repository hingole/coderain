package in.shingole.maker.data.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import in.shingole.maker.data.sqlite.Tables;

/**
 * Contract for WorksheetContentProvider
 */
public class MakerContentProviderContract {
  public static final String AUTHORITY ="in.shingole.maker.provider";
  public static final String PATH_WORKSHEET ="worksheet";
  public static final String PATH_QUESTION ="question";

  private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  /**
   * Contract for worksheet data type.
   */
  public static class Worksheet {
    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_DATE_CREATED = Tables.WorksheetTable.COL_DATE_CREATED;
    public static final String COL_LAST_UPDATED = Tables.WorksheetTable.COL_LAST_UPDATED;
    public static final String COL_WORKSHEET_NAME = Tables.WorksheetTable.COL_NAME;
    public static final String COL_WORKSHEET_DESCRIPTION = Tables.WorksheetTable.COL_DESC;
    public static final String COL_CATEGORY = Tables.WorksheetTable.COL_CATEGORY;

    /**
     * Content URI for Worksheet
     * content://in.shingole.maker.provider/worksheet
     */
    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORKSHEET).build();

    /**
     * Content type for list of worksheets
     * vnd.android.cursor.dir/vnd.in.shingole.maker.provider.worksheet
     */
     public static final String CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_WORKSHEET;

    /**
     * Content type for single worksheet item
     * vnd.android.cursor.item/vnd.in.shingole.maker.provider.worksheet
     */
    public static final String CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_WORKSHEET;

    public static Uri getContentUriForQuestions(String worksheetId) {
      return CONTENT_URI.buildUpon()
          .appendPath(worksheetId)
          .appendPath(PATH_QUESTION)
          .build();
    }
  }

  /**
   * Contract for Question data type.
   */
  public static class Question {
    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_DATE_CREATED = Tables.QuestionTable.COL_DATE_CREATED;
    public static final String COL_LAST_UPDATED = Tables.QuestionTable.COL_LAST_UPDATED;
    public static final String COL_LONG_DESC = Tables.QuestionTable.COL_LONG_DESC;
    public static final String COL_SHORT_DESC = Tables.QuestionTable.COL_SHORT_DESC;
    public static final String COL_CATEGORY = Tables.QuestionTable.COL_CATEGORY;
    public static final String COL_SHORT_ANSWER = Tables.QuestionTable.COL_SHORT_ANSWER;
    public static final String COL_LONG_ANSWER = Tables.QuestionTable.COL_LONG_ANSWER;
    public static final String COL_ANSWER_TYPE = Tables.QuestionTable.COL_ANSWER_TYPE;
    public static final String COL_TYPE = Tables.QuestionTable.COL_TYPE;
    public static final String COL_DIFFICULTY_LEVEL = Tables.QuestionTable.COL_DIFFICULTY_LEVEL;
    public static final String COL_MULTI_CHOICE_OPTIONS =
        Tables.QuestionTable.COL_MULTI_CHOICE_OPTIONS;

    /**
     * Content Uri for Question resource.
     * content://in.shingole.maker.provider/worksheet
     */
    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_QUESTION).build();

    /**
     * Content type for list of worksheets
     * vnd.android.cursor.dir/vnd.in.shingole.maker.provider.question
     */
    public static final String CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_QUESTION;

    /**
     * Content type for single question item
     * vnd.android.cursor.item/vnd.in.shingole.maker.provider.question
     */
    public static final String CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_QUESTION;
  }
}
