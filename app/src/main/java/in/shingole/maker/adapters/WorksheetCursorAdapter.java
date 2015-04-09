package in.shingole.maker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import in.shingole.R;
import in.shingole.maker.data.provider.WorksheetContentProviderContract;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.data.query.WorksheetListQuery;

/**
 * Cursor adapter for worksheet.
 */
public class WorksheetCursorAdapter extends SimpleCursorAdapter {
  private final LayoutInflater inflater;

  public WorksheetCursorAdapter(Context context) {
    super(context,
        0,
        null,
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

    WorksheetListQuery.WorksheetCursorWrapper item =
        (WorksheetListQuery.WorksheetCursorWrapper)super.getItem(position);
    Worksheet sheet = item.getWorksheet();
    if (sheet != null) {
      //((ImageView)view.findViewById(R.id.icon)).setImageDrawable(item.getIcon());
      ((TextView) view.findViewById(R.id.label_worksheet_name)).setText(sheet.getName());
    }

    return view;
  }
}
