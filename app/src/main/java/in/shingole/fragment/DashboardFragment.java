package in.shingole.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import in.shingole.R;
import in.shingole.activity.DashboardActivity;
import in.shingole.activity.ViewWorksheetActivity;
import in.shingole.common.BaseFragment;
import in.shingole.data.WorksheetContentProviderContract;
import in.shingole.data.adapters.WorksheetCursorAdapter;
import in.shingole.data.adapters.WorksheetListAdapter;
import in.shingole.data.model.Worksheet;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends BaseFragment {

  public static final String FRAGMENT_TAG = "DASHBOARD_FRAGMENT";
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;
  private WorksheetCursorAdapter worksheetListAdapter;
  private ListAdapter listAdapter;

  public DashboardFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment BlankFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static DashboardFragment newInstance(String param1, String param2) {
    DashboardFragment fragment = new DashboardFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View fragmentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
    final GridView gridview = (GridView) fragmentView.findViewById(R.id.dashboard_grid);
    Uri uri = WorksheetContentProviderContract.CONTENT_URI;
    Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

    this.worksheetListAdapter = new WorksheetCursorAdapter(getActivity(), cursor);
    gridview.setAdapter(worksheetListAdapter);
    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Worksheet item = (Worksheet)worksheetListAdapter.getItem(position);
        openWorksheet(item);
      }
    });
    return fragmentView;
  }

  private void openWorksheet(Worksheet sheet) {
    mListener.openWorksheet(sheet);
  }
  @Override
  public void onResume() {
    super.onResume();
    mListener.loadData();
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

//  public WorksheetListAdapter getWorksheetListAdapter() {
//    return worksheetListAdapter;
//  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    void loadData();

    void openWorksheet(Worksheet worksheet);
  }

}
