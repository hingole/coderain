package in.shingole.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import in.shingole.R;
import in.shingole.data.model.TestData;
import in.shingole.fragment.DashboardFragment;
import in.shingole.widget.LeftNavigationWidget;

public class DashboardActivity extends AbstractNavigationDrawerActivity
    implements DashboardFragment.OnFragmentInteractionListener {
  private DashboardFragment dashboardFragment;
  private static final int DESIGN_SHEET_REQUEST_CODE = 909;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dashboardFragment = addFragment(
        DashboardFragment.FRAGMENT_TAG,
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
      Intent intent = new Intent();
      intent.setClass(this, SheetDesignerActivity.class);
      startActivityForResult(intent, DESIGN_SHEET_REQUEST_CODE);
      return true;
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
  public void loadData() {
    // Do nothing for now.
    dashboardFragment.getWorksheetListAdapter().setData(TestData.sampleWorksheet());
  }

}