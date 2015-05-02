package in.shingole.maker.activity;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.otto.Subscribe;

import in.shingole.maker.events.Events;
import in.shingole.maker.fragment.SplashFragment;

/**
 * The initial screen that shows up when the application launches.
 */
public class SplashActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    actionBar.hide();
    addFragment(SplashFragment.FRAGMENT_TAG, SplashFragment.class, savedInstanceState);
  }

  @Subscribe
  public void handleCreateNewWorksheetTappedEvent(Events.LoginToWorksheetTappedEvent event) {
    Intent intent = new Intent();
    intent.setClass(this, DashboardActivity.class);
    startActivity(intent);
    finish();
  }
}
