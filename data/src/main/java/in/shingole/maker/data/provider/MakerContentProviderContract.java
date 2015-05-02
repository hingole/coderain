package in.shingole.maker.data.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import in.shingole.maker.data.sqlite.Tables;

/**
 * Contract for WorksheetContentProvider
 */
public class MakerContentProviderContract {
  public static final String AUTHORITY ="in.shingole.sheetmaker.provider";
  public static final String WORKSHEETS_CONTENT ="worksheets";

  // content://in.shingole.sheetmaker.provider/worksheets
  public static final Uri CONTENT_URI =
      Uri.parse("content://" + AUTHORITY + "/" + WORKSHEETS_CONTENT);

  // vnd.android.cursor.dir/vnd.in.shingole.sheetmaker.provider.worksheets
  public static final String WORKSHEETS_CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + WORKSHEETS_CONTENT;

  // vnd.android.cursor.dir/vnd.in.shingole.sheetmaker.provider.worksheets
  public static final String WORKSHEET_ITEM_CONTENT_TYPE =
      ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + WORKSHEETS_CONTENT;

  public static final class WORKSHEET_PROJECTIONS {
    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_DATE_CREATED = Tables.WorksheetTable.COL_DATE_CREATED;
    public static final String COL_LAST_UPDATED = Tables.WorksheetTable.COL_LAST_UPDATED;
    public static final String COL_WORKSHEET_NAME = Tables.WorksheetTable.COL_NAME;
    public static final String COL_WORKSHEET_DESCRIPTION = Tables.WorksheetTable.COL_DESC;
    public static final String COL_CATEGORY = Tables.WorksheetTable.COL_CATEGORY;
  }
}
