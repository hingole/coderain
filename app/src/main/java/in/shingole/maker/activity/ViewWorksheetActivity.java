package in.shingole.maker.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.shingole.R;
import in.shingole.maker.adapters.QuestionCursorPagerAdapter;
import in.shingole.maker.common.Constants;
import in.shingole.maker.data.query.WorksheetQuestionListQuery;
import in.shingole.maker.events.Events;
import in.shingole.maker.fragment.QuestionFragment;

public class ViewWorksheetActivity extends BaseActivity
    implements LoaderManager.LoaderCallbacks<Cursor> {

  private static final int QUESTION_LOADER = 1;
  private String worksheetId;
  private QuestionCursorPagerAdapter questionCursorPagerAdapter;

  @Inject
  WorksheetQuestionListQuery worksheetQuestionListQuery;

  @InjectView(R.id.pager)
  ViewPager pager;

  public ViewWorksheetActivity() {
    super(R.layout.worksheet_slide_view);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.inject(this);
    questionCursorPagerAdapter =
        new QuestionCursorPagerAdapter(getSupportFragmentManager(), QuestionFragment.class, null);

    if (getIntent().getExtras() != null) {
      worksheetId = getIntent().getExtras().getString(Constants.PARAM_WORKSHEET_ID);
    }
    if (savedInstanceState != null) {
      worksheetId = savedInstanceState.getString(Constants.PARAM_WORKSHEET_ID);
    }

    pager.setAdapter(questionCursorPagerAdapter);
    getSupportLoaderManager().initLoader(Constants.WORKSHEET_QUESTION_LOADER_ID, null, this);

  }

  @Subscribe
  public void handleEvent(Events.CorrectAnswerEvent event) {
    //TODO(shingole): Track this event into a log so that user can view his history.
    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(Constants.PARAM_WORKSHEET_ID, worksheetId);
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
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    if (id != Constants.WORKSHEET_QUESTION_LOADER_ID) {
      return null;
    }
    return worksheetQuestionListQuery.createQuestionListLoader(worksheetId);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    if (loader.getId() == Constants.WORKSHEET_QUESTION_LOADER_ID) {
      questionCursorPagerAdapter.swapCursor(
          new WorksheetQuestionListQuery.QuestionCursorWrapper(data));
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    if (loader.getId() == Constants.WORKSHEET_QUESTION_LOADER_ID) {
      questionCursorPagerAdapter.swapCursor(null);
    }
  }
}
