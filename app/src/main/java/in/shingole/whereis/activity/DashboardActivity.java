package in.shingole.whereis.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import in.shingole.R;
import in.shingole.whereis.fragment.DashboardFragment;
import in.shingole.whereis.widget.LeftNavigationWidget;

public class DashboardActivity extends AbstractNavigationDrawerActivity {
  private static final int DESIGN_SHEET_REQUEST_CODE = 909;
  private static final int VIEW_WORKSHEET_REQUEST_CODE = 809;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addFragment(DashboardFragment.FRAGMENT_TAG, DashboardFragment.class, savedInstanceState);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
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

}
