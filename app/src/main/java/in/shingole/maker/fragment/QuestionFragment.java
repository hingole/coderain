package in.shingole.maker.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.InjectView;
import in.shingole.R;
import in.shingole.maker.common.Constants;
import in.shingole.maker.data.model.Question;

/**
 * Fragment to render a Question
 */
public class QuestionFragment extends BaseFragment {
  private Question question;
  @InjectView(R.id.question_short_description) TextView questionLabel;

  public QuestionFragment() {
    super(R.layout.fragment_question);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      question = (Question)getArguments().get(Constants.PARAM_QUESTION);
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (question != null) {
      questionLabel.setText(question.getShortDescription());
    }
  }

}
