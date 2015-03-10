package in.shingole.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import in.shingole.R;
import in.shingole.common.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SplashFragment.SplashEventsListener} interface
 * to handle interaction events.
 * Use the {@link SplashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashFragment extends BaseFragment {

  private SplashEventsListener mListener;

  public SplashFragment() {
    super();
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment SplashFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static SplashFragment newInstance() {
    SplashFragment fragment = new SplashFragment();
    return fragment;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    String appSubText = "Brought to you by <a href=\"https://twitter.com/hingole\">@hingole</a>";
    TextView introTextView = getView(R.id.app_subtext);
    introTextView.setMovementMethod(LinkMovementMethod.getInstance());
    introTextView.setText(Html.fromHtml(appSubText));
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_splash, container, false);
    Button createNewWorksheet = (Button) view.findViewById(R.id.createNewWorksheet);

    createNewWorksheet.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mListener != null) {
          mListener.onCreateNewWorksheetTapped();
        }
      }
    });
    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (SplashEventsListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
          + " must implement SplashEventsListener");
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
  public interface SplashEventsListener {
    public void onCreateNewWorksheetTapped();
  }

}
