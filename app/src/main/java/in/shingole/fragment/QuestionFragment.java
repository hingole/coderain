package in.shingole.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.InjectView;
import in.shingole.R;
import in.shingole.common.BaseFragment;
import in.shingole.data.model.Question;

/**
 * Fragment to render a Question
 */
public class QuestionFragment extends BaseFragment {
  private static final String ARG_QUESTION = "question";
  private Question question;
  @InjectView(R.id.question_short_description) TextView questionLabel;
  @InjectView(R.id.question_detail_container) LinearLayout questionDetailContainer;

  private OnFragmentInteractionListener mListener;

  public QuestionFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      question = (Question)getArguments().get(ARG_QUESTION);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View questionView = inflater.inflate(R.layout.fragment_question, container, false);
    if (question != null) {
      TextView shortDescription =
          (TextView)questionView.findViewById(R.id.question_short_description);
      shortDescription.setText(question.getShortDescription());
    }
    return questionView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (OnFragmentInteractionListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    public void onFragmentInteraction(Uri uri);
  }

}
