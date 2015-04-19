package in.shingole.maker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import in.shingole.R;
import in.shingole.maker.common.Constants;
import in.shingole.maker.fragment.DashboardFragment;
import in.shingole.maker.widget.LeftNavigationWidget;

public class DashboardActivity extends AbstractNavigationDrawerActivity
    implements DashboardFragment.OnFragmentInteractionListener {
  private DashboardFragment dashboardFragment;
  private static final int DESIGN_SHEET_REQUEST_CODE = 909;
  private static final int VIEW_WORKSHEET_REQUEST_CODE = 809;

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
      View buttonView = findViewById(R.id.action_add);
      PopupMenu templatePickerMenu = new PopupMenu(this, buttonView);
      templatePickerMenu.getMenuInflater().inflate(R.menu.menu_worksheet_template,
          templatePickerMenu.getMenu());
      templatePickerMenu.show();
      templatePickerMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
          Intent intent = new Intent();
          intent.setClass(DashboardActivity.this, SheetDesignerActivity.class);
          startActivityForResult(intent, DESIGN_SHEET_REQUEST_CODE);
          return true;
        }
      });
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
  public void openWorksheet(String worksheetId) {
    Intent intent = new Intent();
    intent.setClass(this, ViewWorksheetActivity.class);
    intent.putExtra(Constants.PARAM_WORKSHEET_ID, worksheetId);
    startActivityForResult(intent, VIEW_WORKSHEET_REQUEST_CODE);
  }

}
