package in.shingole.maker.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import in.shingole.R;
import in.shingole.maker.common.CustomTypefaceSpan;
import in.shingole.maker.common.TextStyleUtil;
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
  private final CustomTypefaceSpan iconTypefaceSpan;
  private final Context context;

  public PreviewWorksheetAdapter(Context context, Worksheet sheet) {
    super();
    this.context = context;
    inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    iconTypefaceSpan = new CustomTypefaceSpan(context, "maker.ttf");
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
      if (sheet.getId() != null) {
        return Long.valueOf(sheet.getId());
      }
    } else {
      Question question = sheet.getQuestionList().get(position - 1);
      if (question.getId() != null) {
        return Long.valueOf(sheet.getQuestionList().get(position - 1).getId());
      }
    }
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (ViewType.WORKSHEET_HEADER_VIEW.ordinal() == getItemViewType(position)) {
      LinearLayout headerView =
          (LinearLayout)inflator.inflate(R.layout.preview_worsheet_header, parent, false);
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
      convertView = inflator.inflate(R.layout.fragment_question, parent, false);
    }
    Question question = (Question)getItem(position);
    TextView questionShortDescription =
        (TextView)convertView.findViewById(R.id.question_short_description);
    SpannableStringBuilder shortDesc = new SpannableStringBuilder(question.getShortDescription());
    int questionNoIndex = question.getShortDescription().indexOf(")");
    shortDesc.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.teal)),
        0, questionNoIndex, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);
    shortDesc.setSpan(new ForegroundColorSpan(
            context.getResources().getColor(R.color.primary_dark_material_dark)),
        questionNoIndex, question.getShortDescription().length(),
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

    questionShortDescription.setText(shortDesc);

    SpannableStringBuilder longDesc = new SpannableStringBuilder(question.getLongDescription());
    longDesc.setSpan(iconTypefaceSpan, 0, longDesc.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    longDesc.setSpan(new ForegroundColorSpan(
            context.getResources().getColor(R.color.material_orange_A200)),
        0, longDesc.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    TextView longDescView = (TextView)convertView.findViewById(R.id.question_long_description);
    longDescView.setText(longDesc);

    List<String> options = question.getMultipleChoiceOptions();
    if (options != null && options.size() > 0) {
      Button option1 = (Button)convertView.findViewById(R.id.multi_choice_one);
      Button option2 = (Button)convertView.findViewById(R.id.multi_choice_two);
      Button option3 = (Button)convertView.findViewById(R.id.multi_choice_three);

      option1.setText(options.get(0));
      option2.setText(options.get(1));
      option3.setText(options.get(2));

    }
    return convertView;
  }


}
