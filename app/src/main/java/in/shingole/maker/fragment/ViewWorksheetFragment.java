package in.shingole.maker.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.shingole.R;
import in.shingole.maker.common.Constants;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.data.query.WorksheetDetailQuery;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewWorksheetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ViewWorksheetFragment extends BaseFragment
    implements LoaderManager.LoaderCallbacks<Cursor> {

  public static final String FRAGMENT_TAG = "ViewWorksheetFragment";
  private Worksheet worksheet;

  @Inject
  WorksheetDetailQuery worksheetDetailQuery;

  @InjectView(R.id.label_worksheet_name) TextView worksheetName;
  @InjectView(R.id.label_worksheet_description) TextView worksheetDescription;

  private OnFragmentInteractionListener mListener;

  public ViewWorksheetFragment() {
    super();
  }

  public void setWorksheet(Worksheet worksheet) {
    this.worksheet = worksheet;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_view_worksheet, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(Constants.WORKSHEET_DETAIL_LOADER_ID, getArguments(), this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
  }

  public void renderWorksheet() {
    if (worksheet != null) {
      worksheetName.setText(worksheet.getName());
      worksheetDescription.setText(worksheet.getDescription());
    } else {
      Toast toast = Toast.makeText(getActivity(),
          "An error occurred while loading the worksheet",
          Toast.LENGTH_SHORT);
      toast.show();
    }
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
    if (id != Constants.WORKSHEET_DETAIL_LOADER_ID) {
      return null;
    }
    String worksheetId = args == null ? null : args.getString(Constants.PARAM_WORKSHEET_ID);
    return worksheetDetailQuery.createWorksheetDetailLoader(worksheetId);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    if (loader.getId() == Constants.WORKSHEET_DETAIL_LOADER_ID) {
      WorksheetDetailQuery.WorksheetCursorWrapper cursor =
          new WorksheetDetailQuery.WorksheetCursorWrapper(data);
      worksheet = cursor.getWorksheet();
      renderWorksheet();
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    this.worksheet = null;
  }

  /**
   * Fragment interface.
   */
  public interface OnFragmentInteractionListener {
  }
}
