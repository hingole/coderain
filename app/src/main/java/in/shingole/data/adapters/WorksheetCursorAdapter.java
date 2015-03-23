package in.shingole.data.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import in.shingole.R;
import in.shingole.data.WorksheetContentProviderContract;
import in.shingole.data.model.Worksheet;

/**
 * Cursor adapter for worksheet.
 */
public class WorksheetCursorAdapter extends SimpleCursorAdapter {
  private final LayoutInflater inflater;

  private Cursor cursor;

  public WorksheetCursorAdapter(Context context,
                                Cursor cursor) {
    super(context,
        0,
        cursor,
        new String[]{
            WorksheetContentProviderContract.WORKSHEET_PROJECTIONS.COL_ID,
            WorksheetContentProviderContract.WORKSHEET_PROJECTIONS.COL_WORKSHEET_NAME },
        null,
        0);
    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  /**
   * Populate new items in the list.
   */
  @Override public View getView(int position, View convertView, ViewGroup parent) {
    View view;

    if (convertView == null) {
      view = inflater.inflate(R.layout.worksheet_grid_view_item, parent, false);
    } else {
      view = convertView;
    }

    Cursor item = (Cursor)super.getItem(position);
    String worksheetId = item.getString(1);
    String worksheetName = item.getString(2);
    //((ImageView)view.findViewById(R.id.icon)).setImageDrawable(item.getIcon());
    ((TextView)view.findViewById(R.id.label_worksheet_name)).setText(worksheetName);

    return view;
  }
}
