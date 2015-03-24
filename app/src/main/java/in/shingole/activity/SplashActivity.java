package in.shingole.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import in.shingole.R;
import in.shingole.common.BaseActivity;
import in.shingole.fragment.SplashFragment;


public class SplashActivity extends BaseActivity implements SplashFragment.SplashEventsListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    actionBar.hide();
    super.addFragment(SplashFragment.FRAGMENT_TAG, SplashFragment.class, savedInstanceState);
  }

  @Override
  protected Object[] getActivityModules() {
    return new Object[]{
        new ActivityScopeModule(this),
    };

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_welcome, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onCreateNewWorksheetTapped() {
    Intent intent = new Intent();
    intent.setClass(this, DashboardActivity.class);
    startActivity(intent);
    finish();
  }
}
