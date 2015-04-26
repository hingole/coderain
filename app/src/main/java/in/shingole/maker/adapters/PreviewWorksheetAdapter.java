package in.shingole.maker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.shingole.R;
import in.shingole.maker.data.model.Question;
import in.shingole.maker.data.model.Worksheet;

/**
 * Created by shingole on 4/25/15.
 */
public class PreviewWorksheetAdapter extends BaseAdapter {

  // The different type of views rendered in the list view.
  private enum ViewType {
    QUESTION_VIEW,
    WORKSHEET_HEADER_VIEW,
    NUM_VIEW_TYPES, // Always keep this last.
  }

  private final Worksheet sheet;
  private final LayoutInflater inflator;

  public PreviewWorksheetAdapter(Context context, Worksheet sheet) {
    super();
    inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.sheet = sheet;
  }
  @Override
  public int getCount() {
    return sheet.getQuestionList().size() + 1;
  }

  @Override
  public int getViewTypeCount() {
    return ViewType.NUM_VIEW_TYPES.ordinal();
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return ViewType.WORKSHEET_HEADER_VIEW.ordinal();
    }
    return ViewType.QUESTION_VIEW.ordinal();
  }

  @Override
  public Object getItem(int position) {
    if (position == 0) {
      return sheet;
    } else {
      return sheet.getQuestionList().get(position - 1);
    }
  }

  @Override
  public long getItemId(int position) {
    if (position == 0) {
      return Long.valueOf(sheet.getId());
    } else {
      return Long.valueOf(sheet.getQuestionList().get(position - 1).getId());
    }
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (ViewType.WORKSHEET_HEADER_VIEW.ordinal() == getItemViewType(position)) {
      LinearLayout headerView =
          (LinearLayout)inflator.inflate(R.layout.preview_worsheet_header, parent);
      if (sheet.getName() != null) {
        EditText worksheetNameEditText = (EditText)headerView.findViewById(R.id.worksheetName);
        worksheetNameEditText.setText(sheet.getName());
      }
      if (sheet.getDescription() != null) {
        EditText worksheetDescriptionEditText =
            (EditText)headerView.findViewById(R.id.worksheetDescription);
        worksheetDescriptionEditText.setText(sheet.getDescription());
      }
      return headerView;
    }
    if (convertView == null) {
      convertView = inflator.inflate(R.layout.fragment_question, parent);
    }
    Question question = (Question)getItem(position);
    TextView questionShortDescription =
        (TextView)convertView.findViewById(R.id.question_short_description);
    questionShortDescription.setText(question.getShortDescription());

    return convertView;
  }
}
