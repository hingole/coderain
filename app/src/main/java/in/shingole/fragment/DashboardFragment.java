package in.shingole.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import in.shingole.R;
import in.shingole.common.BaseFragment;
import in.shingole.data.WorksheetContentProviderContract;
import in.shingole.data.adapters.WorksheetCursorAdapter;

/**
 * Fragment for rendering the dashboard view.
 */
public class DashboardFragment extends BaseFragment {

  public static final String FRAGMENT_TAG = "DASHBOARD_FRAGMENT";

  private OnFragmentInteractionListener mListener;
  private WorksheetCursorAdapter worksheetCursorAdapter;

  public DashboardFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View fragmentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
    final GridView gridview = (GridView) fragmentView.findViewById(R.id.dashboard_grid);
    Uri uri = WorksheetContentProviderContract.CONTENT_URI;
    Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

    this.worksheetCursorAdapter = new WorksheetCursorAdapter(getActivity(), cursor);
    gridview.setAdapter(worksheetCursorAdapter);
    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Cursor item = (Cursor) worksheetCursorAdapter.getItem(position);
        openWorksheet(item.getString(0));
      }
    });
    return fragmentView;
  }

  private void openWorksheet(String sheetId) {
    mListener.openWorksheet(sheetId);
  }
  @Override
  public void onResume() {
    super.onResume();
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

  /**
   * Fragment interface.
   */
  public interface OnFragmentInteractionListener {
    void openWorksheet(String sheetId);
  }

}
