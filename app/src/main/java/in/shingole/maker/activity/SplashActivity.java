package in.shingole.maker.activity;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import in.shingole.R;
import in.shingole.maker.app.Session;
import in.shingole.maker.events.Events;
import in.shingole.maker.fragment.LoginFragment;
import in.shingole.maker.fragment.SplashFragment;

/**
 * The initial screen that shows up when the application launches.
 */
public class SplashActivity extends BaseActivity {

  public SplashActivity() {
    super(R.layout.activity_splash);
  }
  private LoginFragment loginFragment;

  @Inject Session session;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    actionBar.hide();
    addFragment(SplashFragment.FRAGMENT_TAG, SplashFragment.class, savedInstanceState);
    loginFragment =
        addFragment(LoginFragment.FRAGMENT_TAG, LoginFragment.class, savedInstanceState);
  }

  @Override
  protected int getContentFrameId() {
    return R.id.splash_fragment_container;
  }

  @Subscribe
  public void handleCreateNewWorksheetTappedEvent(Events.LoginToWorksheetTappedEvent event) {
    Intent intent = new Intent();
    intent.setClass(this, DashboardActivity.class);
    startActivity(intent);
    finish();
  }

  @Subscribe
  public void handleLoginSuccessfulEvent(Events.UserLoggedInEvent event) {
    session.logout();
    session.login(event.getUser());
    showDashboard();
  }

  private void showDashboard() {
    Intent intent = new Intent();
    intent.setClass(this, DashboardActivity.class);
    startActivity(intent);
    finish();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    loginFragment.onActivityResult(requestCode, resultCode, data);
  }
}
