package in.shingole.maker.fragment;

import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import in.shingole.R;
import in.shingole.maker.events.Events;

/**
 * Splash screen fragment
 */
public class SplashFragment extends BaseFragment {

  public static final String FRAGMENT_TAG = "SplashFragment";

  public SplashFragment() {
    super(R.layout.fragment_splash);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @OnClick(R.id.createNewWorksheet)
  void onCreateNewWorksheetTapped() {
    bus.post(new Events.LoginToWorksheetTappedEvent());
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

}
