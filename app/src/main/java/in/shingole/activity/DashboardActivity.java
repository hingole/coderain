package in.shingole.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import in.shingole.R;
import in.shingole.fragment.DashboardFragment;
import in.shingole.widget.LeftNavigationWidget;

public class DashboardActivity extends AbstractNavigationDrawerActivity
    implements DashboardFragment.OnFragmentInteractionListener {
  private DashboardFragment dashboardFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dashboardFragment = addFragment(
        DashboardFragment.DASHBOARD_FRAGMENT_TAG,
        DashboardFragment.class,
        savedInstanceState);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_dashboard, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (id == R.id.action_add) {

    }
    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected LeftNavigationWidget.NavigationType getNavigationType() {
    return LeftNavigationWidget.NavigationType.SHEETS;
  }

  @Override
  public void onFragmentInteraction(Uri uri) {
    // Do nothing for now.
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_filelist, container, false);
      return rootView;
    }
  }

}
