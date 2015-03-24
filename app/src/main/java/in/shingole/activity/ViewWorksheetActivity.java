package in.shingole.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import in.shingole.R;
import in.shingole.common.BaseActivity;
import in.shingole.common.LoadDataCallback;
import in.shingole.data.WorksheetService;
import in.shingole.data.model.Worksheet;
import in.shingole.fragment.ViewWorksheetFragment;

public class ViewWorksheetActivity extends BaseActivity
    implements ViewWorksheetFragment.OnFragmentInteractionListener {
  ViewWorksheetFragment viewWorksheetFragment;

  @Inject
  WorksheetService worksheetService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewWorksheetFragment = addFragment(
        ViewWorksheetFragment.FRAGMENT_TAG,
        ViewWorksheetFragment.class,
        savedInstanceState);
    viewWorksheetFragment.setArguments(getIntent().getExtras());
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
    getMenuInflater().inflate(R.menu.menu_view_worksheet, menu);
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
  public void loadWorksheet(String worksheetId, LoadDataCallback<Worksheet> loadDataCallback) {
    Worksheet worksheet = worksheetService.getWorksheet(worksheetId);
    loadDataCallback.onSuccess(worksheet);
  }
}
