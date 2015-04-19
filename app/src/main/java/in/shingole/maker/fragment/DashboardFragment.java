package in.shingole.maker.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import javax.inject.Inject;

import butterknife.InjectView;
import in.shingole.R;
import in.shingole.maker.adapters.WorksheetCursorAdapter;
import in.shingole.maker.common.Constants;
import in.shingole.maker.data.query.WorksheetListQuery;

/**
 * Fragment for rendering the dashboard view.
 */
public class DashboardFragment extends BaseFragment
    implements LoaderManager.LoaderCallbacks<Cursor> {

  public static final String FRAGMENT_TAG = "DASHBOARD_FRAGMENT";

  private OnFragmentInteractionListener mListener;
  private WorksheetCursorAdapter worksheetCursorAdapter;

  @Inject WorksheetListQuery worksheetListQuery;
  @InjectView(R.id.dashboard_grid) GridView gridview;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.worksheetCursorAdapter = new WorksheetCursorAdapter(getActivity());
    gridview.setAdapter(worksheetCursorAdapter);
    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        WorksheetListQuery.WorksheetCursorWrapper item =
            (WorksheetListQuery.WorksheetCursorWrapper) worksheetCursorAdapter.getItem(position);
        openWorksheet(item.getWorksheet().getId());
      }
    });
  }

  public DashboardFragment() {
    super(R.layout.fragment_dashboard);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(Constants.WORKSHEET_LOADER_ID, null, this);
  }

  private void openWorksheet(String sheetId) {
    mListener.openWorksheet(sheetId);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (OnFragmentInteractionListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    if (id != Constants.WORKSHEET_LOADER_ID) {
      return null;
    }
    return worksheetListQuery.createWorksheetListLoader();
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    worksheetCursorAdapter.swapCursor(new WorksheetListQuery.WorksheetCursorWrapper(data));
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    worksheetCursorAdapter.swapCursor(null);
  }
  /**
   * Fragment interface.
   */
  public interface OnFragmentInteractionListener {
    void openWorksheet(String sheetId);
  }
}
