package in.shingole.maker.data.sqlite;

/**
 * Table schema definitions.
 */
public class Tables {
  /**
   * Worksheet table.
   */
  public static class WorksheetTable implements StandardColumns {
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
  /**
   * Problem table
   */
  public static class QuestionTable implements StandardColumns {

    public static final String TABLE_NAME = "problems";

    public static final String COL_CATEGORY = "category";
    public static final String COL_TYPE = "type";
    public static final String COL_SHORT_DESC = "short_desc";
    public static final String COL_LONG_DESC = "long_desc";
    public static final String COL_DIFFICULTY_LEVEL = "difficulty_level";
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
        + ", " + COL_DIFFICULTY_LEVEL + COL_TYPE_INTEGER
        + ", " + COL_MAX_ATTEMPTS + COL_TYPE_INTEGER
        + ", " + COL_SHORT_ANSWER + COL_TYPE_TEXT
        + ", " + COL_LONG_ANSWER + COL_TYPE_TEXT
        + ", " + COL_ANSWER_TYPE + COL_TYPE_INTEGER
        + ", " + COL_HINT + COL_TYPE_TEXT
        + ", " + COL_MULTI_CHOICE_OPTIONS + COL_TYPE_TEXT
        + ");";
  }

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

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
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

  /**
   * Many-to-many worksheet-questions table.
   */
  public static class WorksheetQuestionsTable implements StandardColumns {

    public static final String TABLE_NAME = "worksheet_questions";

    // Name of the project as shown in the application.
    public static final String COL_WORKSHEET_ID = "worksheet_id";
    public static final String COL_QUESTION_ID = "question_id";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
        " ("+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
        + ", " + COL_WORKSHEET_ID + COL_TYPE_INTEGER
        + ", " + COL_QUESTION_ID + COL_TYPE_INTEGER
        + " , FOREIGN KEY( " + COL_WORKSHEET_ID + ") REFERENCES " + WorksheetTable.TABLE_NAME
        + "(" + _ID + ") ON DELETE CASCADE"
        + ", FOREIGN KEY( " + COL_QUESTION_ID + ") REFERENCES " + QuestionTable.TABLE_NAME
        + "(" + _ID + ") ON DELETE CASCADE"
        + " UNIQUE ( " + COL_WORKSHEET_ID + ", " + COL_QUESTION_ID + ") ON CONFLICT REPLACE"
        + ");";

  }
}
