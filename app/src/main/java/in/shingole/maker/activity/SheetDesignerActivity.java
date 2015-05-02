package in.shingole.maker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import in.shingole.R;
import in.shingole.maker.common.Utils;
import in.shingole.maker.events.Events;
import in.shingole.maker.fragment.CountWorksheetPreviewFragment;
import in.shingole.maker.fragment.CreateCountingWorksheetFragment;

public class SheetDesignerActivity extends BaseActivity {

  enum WorkflowStep {
    STEP_1,
    STEP_2,
  }

  int currentDBOperationToken = 0;

  @Inject
  AsyncWorksheetQueryHandler asyncQueryHandler;

  private WorkflowStep currentStep;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    currentStep = WorkflowStep.STEP_1;
    addFragment(
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



  @Subscribe
  public void handleInsertOperationCompleted(Events.InsertOperationCompleteEvent event) {
    if (event.getToken() == currentDBOperationToken) {
      Intent data = new Intent();
      data.setData(event.getResourceUri());
      setResult(RESULT_OK, data);
      finish();
    }
  }

  @Subscribe
  public void handleDraftWorksheetCreated(Events.CreateWorksheetTappedEvent event) {
    if (currentStep == WorkflowStep.STEP_2) {
      currentDBOperationToken = asyncQueryHandler.insertWorksheet(event.getDraftWorksheet());
    } else {
      CountWorksheetPreviewFragment fragment = Utils.getFragment(
          this,
          CountWorksheetPreviewFragment.FRAGMENT_TAG,
          CountWorksheetPreviewFragment.class,
          null);
      Bundle bundle = new Bundle();
      bundle.putParcelable(CountWorksheetPreviewFragment.ARG_WORKSHEET, event.getDraftWorksheet());
      fragment.setArguments(bundle);
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(getContentFrameId(), fragment, CountWorksheetPreviewFragment.FRAGMENT_TAG);
      transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
      transaction.addToBackStack(null);
      transaction.commit();
      currentStep = WorkflowStep.STEP_2;
    }
  }

}
