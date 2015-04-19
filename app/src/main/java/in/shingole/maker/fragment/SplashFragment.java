package in.shingole.maker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.shingole.R;

/**
 * Splash screen fragment
 */
public class SplashFragment extends BaseFragment {

  public static final String FRAGMENT_TAG = "SplashFragment";

  private SplashEventsListener mListener;
  @InjectView(R.id.app_subtext) TextView introTextView;
  @InjectView(R.id.createNewWorksheet) Button createNewWorksheet;

  public SplashFragment() {
    super(R.layout.fragment_splash);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
    String appSubText = "Sheets made <b>easy</b>";
    introTextView.setMovementMethod(LinkMovementMethod.getInstance());
    introTextView.setText(Html.fromHtml(appSubText));
    createNewWorksheet.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mListener != null) {
          mListener.onCreateNewWorksheetTapped();
        }
      }
    });
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

  public interface SplashEventsListener {
    public void onCreateNewWorksheetTapped();
  }
}
