package in.shingole.maker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.shingole.R;
import in.shingole.maker.common.Annotations;
import in.shingole.maker.common.TextChangeHandler;
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

  @Inject LayoutInflater inflater;
  @Inject @Annotations.ForActivity Context context;
  @Inject TextStyleUtil textStyleUtil;

  private Worksheet sheet;

  @Inject
  public PreviewWorksheetAdapter(@Annotations.ForActivity Context context) {
    super();
    this.context = context;
  }

  @Override
  public int getCount() {
    if (sheet == null) {
      return 0;
    }
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

  private View getHeaderView(int position, View convertView, ViewGroup parent) {
    View headerView = convertView;
    if (convertView == null) {
      headerView = inflater.inflate(R.layout.preview_worsheet_header, parent, false);
    }

    EditText worksheetNameEditText = ButterKnife.findById(headerView, R.id.worksheetName);
    EditText worksheetDescriptionEditText = ButterKnife.findById(headerView,
        R.id.worksheetDescription);
    if (sheet != null) {
      if (sheet.getName() != null) {
        worksheetNameEditText.setText(sheet.getName());
      }
      if (sheet.getDescription() != null) {
        worksheetDescriptionEditText.setText(sheet.getDescription());
      }

      if (convertView == null) {
        worksheetNameEditText.addTextChangedListener(new TextChangeHandler() {
          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            sheet.setName(s.toString());
          }
        });
        worksheetDescriptionEditText.addTextChangedListener(new TextChangeHandler() {
          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            sheet.setDescription(s.toString());
          }
        });
      }
    }

    return headerView;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (ViewType.WORKSHEET_HEADER_VIEW.ordinal() == getItemViewType(position)) {
      return getHeaderView(position, convertView, parent);
    }

    if (convertView == null) {
      convertView = inflater.inflate(R.layout.fragment_question, parent, false);
    }

    if (sheet == null) {
      return convertView;
    }

    Question question = (Question)getItem(position);
    TextView questionShortDescription =
        (TextView)convertView.findViewById(R.id.fragment_question_short_description);

    questionShortDescription.setText(
        textStyleUtil.formatShortDescription(
            question.getShortDescription(),
            context.getResources().getColor(R.color.primary_dark_material_dark),
            context.getResources().getColor(R.color.secondary_text_default_material_dark)));

    TextView longDescView = (TextView)convertView.findViewById(
        R.id.fragment_question_long_description);
    longDescView.setText(
        textStyleUtil.formatCountingProblemLongDesc(
            question.getLongDescription(),
            context.getResources().getColor(R.color.material_orange_A200)));

    List<String> options = question.getMultipleChoiceOptions();
    if (options != null && options.size() > 0) {
      Button option1 = (Button)convertView.findViewById(R.id.fragment_question_multi_choice_one);
      Button option2 = (Button)convertView.findViewById(R.id.fragment_question_multi_choice_two);
      Button option3 = (Button)convertView.findViewById(R.id.fragment_question_multi_choice_three);

      option1.setText(options.get(0));
      option2.setText(options.get(1));
      option3.setText(options.get(2));
    }
    return convertView;
  }

  public void setSheet(Worksheet sheet) {
    this.sheet = sheet;
  }

  public Worksheet getSheet() {
    return sheet;
  }
}
