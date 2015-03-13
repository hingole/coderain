package in.shingole.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import in.shingole.R;
import in.shingole.data.model.Worksheet;

/**
 * Array adapter for list of worksheets
 */
public class WorksheetListAdapter extends ArrayAdapter<Worksheet> {
  private final LayoutInflater inflater;

  public WorksheetListAdapter(Context context, int resource) {
    super(context, resource);
    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return super.getCount();
  }

  public void setData(List<Worksheet> data) {
    clear();
    if (data != null) {
      addAll(data);
    }
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

    Worksheet item = getItem(position);
    //((ImageView)view.findViewById(R.id.icon)).setImageDrawable(item.getIcon());
    ((TextView)view.findViewById(R.id.label_worksheet_name)).setText(item.getName());

    return view;
  }

}
