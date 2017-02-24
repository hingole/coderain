package in.shingole.whereis.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.GridView;

import butterknife.InjectView;
import butterknife.OnItemClick;
import in.shingole.R;
import in.shingole.whereis.common.Constants;

/**
 * Fragment for rendering the dashboard view.
 */
public class DashboardFragment extends BaseFragment
    implements LoaderManager.LoaderCallbacks<Cursor> {

  public static final String FRAGMENT_TAG = "DASHBOARD_FRAGMENT";

  @InjectView(R.id.dashboard_grid) GridView gridview;

  public DashboardFragment() {
    super(R.layout.fragment_dashboard);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //gridview.setAdapter(worksheetCursorAdapter);
  }

  @OnItemClick (R.id.dashboard_grid)
  public void onItemClickEvent(int position) {
//    WorksheetListQuery.WorksheetCursorWrapper item =
//        (WorksheetListQuery.WorksheetCursorWrapper) worksheetCursorAdapter.getItem(position);
//    bus.post(new Events.WorksheetIconTappedEvent(item.getWorksheet().getId()));
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(Constants.WORKSHEET_LOADER_ID, null, this);
  }

  @Override
  public void onStart() {
    super.onStart();
    setViewState(ViewState.LOADING);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//    if (id != Constants.WORKSHEET_LOADER_ID) {
//      return null;
//    }
//    return worksheetListQuery.createWorksheetListLoader();
    return null;
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//    if (loader.getId() == Constants.WORKSHEET_LOADER_ID) {
//      if (data != null) {
//        data.setNotificationUri(getActivity().getContentResolver(),
//            ContentProviderContract.Worksheet.CONTENT_URI);
//        setViewState(ViewState.RENDERED);
//        worksheetCursorAdapter.swapCursor(new WorksheetListQuery.WorksheetCursorWrapper(data));
//      }
//    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
//    if (loader.getId() == Constants.WORKSHEET_LOADER_ID) {
//      worksheetCursorAdapter.swapCursor(null);
//    }
  }
}
