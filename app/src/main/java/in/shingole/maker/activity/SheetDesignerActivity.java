package in.shingole.maker.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import in.shingole.R;
import in.shingole.maker.common.Utils;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.fragment.BaseFragment;
import in.shingole.maker.fragment.CountWorksheetPreviewFragment;
import in.shingole.maker.fragment.CreateCountingWorksheetFragment;
import in.shingole.maker.fragment.SheetDesignerFragment;

public class SheetDesignerActivity extends BaseActivity
    implements SheetDesignerFragment.OnFragmentInteractionListener,
    CreateCountingWorksheetFragment.OnFragmentInteractionListener,
    CountWorksheetPreviewFragment.OnFragmentInteractionListener {

  BaseFragment sheetDesignerFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sheetDesignerFragment = addFragment(
        CreateCountingWorksheetFragment.FRAGMENT_TAG,
        CreateCountingWorksheetFragment.class,
        savedInstanceState);
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
    if (id == android.R.id.home) {
      FragmentManager fm = getSupportFragmentManager();
      if (fm.getBackStackEntryCount() > 0) {
        fm.popBackStack();
        return true;
      }
    }
    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void handleDraftWorksheetCreated(Worksheet sheet) {
    CountWorksheetPreviewFragment fragment = Utils.getFragment(
        this,
        CountWorksheetPreviewFragment.FRAGMENT_TAG,
        CountWorksheetPreviewFragment.class,
        null);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(getContentFrameId(), fragment, CountWorksheetPreviewFragment.FRAGMENT_TAG);
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    transaction.addToBackStack(null);
    transaction.commit();
  }

  @Override
  public void finish() {
    super.finish();
  }

  @Override
  public void onFragmentInteraction(Uri uri) {
    // Nothing as of now.
  }

  @Override
  public void onFragmentInteraction(String id) {

  }
}
