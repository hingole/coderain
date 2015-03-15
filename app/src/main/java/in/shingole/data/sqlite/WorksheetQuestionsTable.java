package in.shingole.data.sqlite;

/**
 * One-to-many worksheet-questions table.
 */
public class WorksheetQuestionsTable implements StandardColumns {

  public static final String TABLE_NAME = "worksheet_questions";

  // Name of the project as shown in the application.
  public static final String COL_WORKSHEET_ID = "worksheet_id";
  public static final String COL_QUESTION_ID = "question_id";

  public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
      " ("+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
      + ", " + COL_WORKSHEET_ID + COL_TYPE_INTEGER
      + ", " + COL_QUESTION_ID + COL_TYPE_INTEGER
      + ");";

}
