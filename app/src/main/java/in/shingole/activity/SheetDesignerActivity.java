package in.shingole.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import in.shingole.R;
import in.shingole.common.BaseActivity;
import in.shingole.fragment.SheetDesignerFragment;

public class SheetDesignerActivity extends BaseActivity
    implements SheetDesignerFragment.OnFragmentInteractionListener {

  SheetDesignerFragment sheetDesignerFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sheetDesignerFragment = addFragment(
        SheetDesignerFragment.FRAGMENT_TAG,
        SheetDesignerFragment.class,
        savedInstanceState);
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
    getMenuInflater().inflate(R.menu.menu_sheet_designer, menu);
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
  public void onFragmentInteraction(Uri uri) {
    // Nothing as of now.
  }
}
