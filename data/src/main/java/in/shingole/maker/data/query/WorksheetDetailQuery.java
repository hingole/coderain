package in.shingole.maker.data.query;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.Date;

import javax.inject.Inject;

import in.shingole.maker.common.Annotations;
import in.shingole.maker.data.provider.MakerContentProviderContract;
import in.shingole.maker.data.model.Worksheet;

/**
 * Helper class for worksheet query
 */
public class WorksheetDetailQuery {

  private final Context context;

  public static class WorksheetCursorWrapper extends CursorWrapper {
    public WorksheetCursorWrapper(Cursor cursor) {
      super(cursor);
    }

    public Worksheet getWorksheet() {
      if (getCount() > 0) {
        moveToFirst();
        Worksheet sheet = new Worksheet();
        sheet.setId(Long.toString(getLong(Projection.COL_ID)));
        sheet.setName(getString(Projection.COL_WORKSHEET_NAME));
        sheet.setDescription(getString(Projection.COL_WORKSHEET_DESCRIPTION));
        sheet.setCategory(getString(Projection.COL_CATEGORY));
        sheet.setDateCreated(new Date(getInt(Projection.COL_DATE_CREATED)));
        sheet.setLastUpdated(new Date(getInt(Projection.COL_LAST_UPDATED)));
        return sheet;
      }
      return null;
    }
  }

  @Inject
  public WorksheetDetailQuery(@Annotations.ForActivity Context context) {
    this.context = context;
  }

  public Loader<Cursor> createWorksheetDetailLoader(String worksheetId) {
    Loader<Cursor> loader = new CursorLoader(context,
        MakerContentProviderContract.Worksheet.CONTENT_URI,
        Projection.PROJECTION,
        MakerContentProviderContract.Worksheet.COL_ID + " = ?",
        new String[] { worksheetId },
        null);
    return loader;
  }

  private interface Projection {
    public static final String[] PROJECTION = {
        MakerContentProviderContract.Worksheet.COL_ID,
        MakerContentProviderContract.Worksheet.COL_WORKSHEET_NAME,
        MakerContentProviderContract.Worksheet.COL_WORKSHEET_DESCRIPTION,
        MakerContentProviderContract.Worksheet.COL_CATEGORY,
        MakerContentProviderContract.Worksheet.COL_DATE_CREATED,
        MakerContentProviderContract.Worksheet.COL_LAST_UPDATED,
    };

    int COL_ID = 0;
    int COL_WORKSHEET_NAME = 1;
    int COL_WORKSHEET_DESCRIPTION = 2;
    int COL_CATEGORY = 3;
    int COL_DATE_CREATED = 4;
    int COL_LAST_UPDATED = 5;
  }
}
