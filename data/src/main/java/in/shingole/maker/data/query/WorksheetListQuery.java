package in.shingole.maker.data.query;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import in.shingole.maker.data.provider.MakerContentProviderContract;
import in.shingole.maker.data.model.Worksheet;

/**
 * Created by shingole on 4/7/15.
 */
public class WorksheetListQuery {

  private final Context context;

  public static class WorksheetCursorWrapper extends CursorWrapper {
    Map<String, Worksheet> cache = new HashMap<>();
    public WorksheetCursorWrapper(Cursor cursor) {
      super(cursor);
    }

    public Worksheet getWorksheet() {
      String id = Long.toString(getLong(Projection.COL_ID));
      if (cache.containsKey(id)) {
        return cache.get(id);
      }
      Worksheet sheet = new Worksheet();
      sheet.setId(id);
      sheet.setName(getString(Projection.COL_WORKSHEET_NAME));
      cache.put(id, sheet);
      return sheet;
    }
  }

  @Inject
  public WorksheetListQuery(Context context) {
    this.context = context;
  }

  public Loader<Cursor> createWorksheetListLoader() {
    return new CursorLoader(context,
        MakerContentProviderContract.Worksheet.CONTENT_URI,
        Projection.PROJECTION,
        null, null,
        MakerContentProviderContract.Worksheet.COL_DATE_CREATED + " DESC");
  }

  private interface Projection {
    public static final String[] PROJECTION = {
        MakerContentProviderContract.Worksheet.COL_ID,
        MakerContentProviderContract.Worksheet.COL_WORKSHEET_NAME,
    };

    int COL_ID = 0;
    int COL_WORKSHEET_NAME = 1;
  }

}
