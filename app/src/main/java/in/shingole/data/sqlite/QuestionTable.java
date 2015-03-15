package in.shingole.data.sqlite;

/**
 * Problem table
 */
public class QuestionTable implements StandardColumns {

  public static final String TABLE_NAME = "problems";

  public static final String COL_CATEGORY = "category";
  public static final String COL_TYPE = "type";
  public static final String COL_SHORT_DESC = "short_desc";
  public static final String COL_LONG_DESC = "long_desc";
  public static final String COL_DATE_SOLVED = "date_solved";
  public static final String COL_DIFFICULTY_LEVEL = "difficulty_level";
  public static final String COL_NUM_ATTEMPTS = "num_attempts";
  public static final String COL_MAX_ATTEMPTS = "max_attempts";
  public static final String COL_SHORT_ANSWER = "short_answer";
  public static final String COL_LONG_ANSWER = "long_answer";
  public static final String COL_ANSWER_TYPE = "answer_type";
  public static final String COL_HINT = "hint";
  public static final String COL_MULTI_CHOICE_OPTIONS = "multi_choice_options";

  public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
      + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
      + ", " + COL_CATEGORY + COL_TYPE_TEXT
      + ", " + COL_TYPE + COL_TYPE_TEXT
      + ", " + COL_SHORT_DESC + COL_TYPE_TEXT
      + ", " + COL_LONG_DESC + COL_TYPE_TEXT
      + ", " + COL_DATE_CREATED + COL_TYPE_TEXT
      + ", " + COL_LAST_UPDATED + COL_TYPE_TEXT
      + ", " + COL_IS_MARKED_FOR_DELETION + COL_TYPE_INTEGER
      + ", " + COL_DATE_SOLVED + COL_TYPE_TEXT
      + ", " + COL_DATE_SOLVED + COL_TYPE_TEXT
      + ", " + COL_DIFFICULTY_LEVEL + COL_TYPE_INTEGER
      + ", " + COL_NUM_ATTEMPTS + COL_TYPE_INTEGER
      + ", " + COL_MAX_ATTEMPTS + COL_TYPE_INTEGER
      + ", " + COL_SHORT_ANSWER + COL_TYPE_TEXT
      + ", " + COL_LONG_ANSWER + COL_TYPE_TEXT
      + ", " + COL_ANSWER_TYPE + COL_TYPE_INTEGER
      + ", " + COL_HINT + COL_TYPE_TEXT
      + ", " + COL_MULTI_CHOICE_OPTIONS + COL_TYPE_TEXT
      + ");";

}
