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

  enum WorkflowStep {
    STEP_1,
    STEP_2,
  }

  private WorkflowStep currentStep;

  BaseFragment sheetDesignerFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    currentStep = WorkflowStep.STEP_1;
    sheetDesignerFragment = addFragment(
        CreateCountingWorksheetFragment.FRAGMENT_TAG,
        CreateCountingWorksheetFragment.class,
        savedInstanceState);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_sheet_designer, menu);
    MenuItem addButton = menu.findItem(R.id.action_add);
    if (currentStep == WorkflowStep.STEP_1) {
      addButton.setTitle(super.getString(R.string.menu_next));
    } else {
      addButton.setTitle(super.getString(R.string.menu_create));
    }
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
        currentStep = WorkflowStep.STEP_1;
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
    Bundle bundle = new Bundle();
    bundle.putParcelable(CountWorksheetPreviewFragment.ARG_WORKSHEET, sheet);
    fragment.setArguments(bundle);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(getContentFrameId(), fragment, CountWorksheetPreviewFragment.FRAGMENT_TAG);
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    transaction.addToBackStack(null);
    transaction.commit();
    currentStep = WorkflowStep.STEP_2;
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
