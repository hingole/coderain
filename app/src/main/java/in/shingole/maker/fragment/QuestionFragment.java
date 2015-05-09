package in.shingole.maker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.shingole.R;
import in.shingole.maker.common.Annotations;
import in.shingole.maker.common.Constants;
import in.shingole.maker.common.CustomTypefaceSpan;
import in.shingole.maker.common.TextStyleUtil;
import in.shingole.maker.data.model.Question;
import in.shingole.maker.events.Events;

/**
 * Fragment to render a Question
 */
public class QuestionFragment extends BaseFragment implements View.OnClickListener {
  private Question question;

  @Inject TextStyleUtil textStyleUtil;
  @Inject @Annotations.IconTypeface CustomTypefaceSpan iconTypefaceSpan;
  @InjectView(R.id.fragment_question_short_description) TextView questionShortDescription;
  @InjectView(R.id.fragment_question_long_description) TextView questionLongDescription;

  public QuestionFragment() {
    super(R.layout.fragment_question_scroll_container);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      question = (Question)getArguments().get(Constants.PARAM_QUESTION);
    }
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.fragment_question_multi_choice_one
        || view.getId() == R.id.fragment_question_multi_choice_two
        || view.getId() == R.id.fragment_question_multi_choice_three) {
      String answer = question.getShortAnswer();
      Button option = (Button) view;
      if (option.getText().equals(answer)) {
        bus.post(new Events.CorrectAnswerEvent(question));
      } else {
        Toast.makeText(getActivity(), "Incorrect answer. Please try again.", Toast.LENGTH_SHORT)
            .show();
      }
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (question != null) {
      int primaryColor = getActivity().getResources().getColor(R.color.primary_dark_material_dark);
      int secondaryColor =
          getActivity().getResources().getColor(R.color.teal);

      questionShortDescription.setText(
          textStyleUtil.formatShortDescription(question.getShortDescription(),
              primaryColor,
              secondaryColor));

      questionLongDescription.setText(
          textStyleUtil.formatCountingProblemLongDesc(
              question.getLongDescription(),
              getActivity().getResources().getColor(R.color.material_orange_A200)));

      List<String> options = question.getMultipleChoiceOptions();
      if (options != null && options.size() > 0) {
        Button option1 = ButterKnife.findById(view, R.id.fragment_question_multi_choice_one);
        Button option2 = ButterKnife.findById(view, R.id.fragment_question_multi_choice_two);
        Button option3 = ButterKnife.findById(view, R.id.fragment_question_multi_choice_three);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);

        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));
      }
    }
  }
}
