package in.shingole.data;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Contract for WorksheetContentProvider
 */
public class WorksheetContentProviderContract {
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
}
