package in.shingole.maker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import in.shingole.R;
import in.shingole.maker.adapters.PreviewWorksheetAdapter;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.fragment.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CountWorksheetPreviewFragment extends ListFragment {

  public static final String FRAGMENT_TAG = "PREVIEW_COUNTING_WORKSHEET_FRAGMENT";
  public static final String ARG_WORKSHEET = "ARG_WORKSHEET";

  private Worksheet worksheet;

  private OnFragmentInteractionListener mListener;
  private PreviewWorksheetAdapter listAdapter;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public CountWorksheetPreviewFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    if (savedInstanceState != null) {
      if (savedInstanceState.containsKey(ARG_WORKSHEET)) {
        worksheet = savedInstanceState.getParcelable(ARG_WORKSHEET);
      }
    }
  }

  @Override
  public void setArguments(Bundle args) {
    super.setArguments(args);
    if (args.containsKey(ARG_WORKSHEET)) {
      worksheet = args.getParcelable(ARG_WORKSHEET);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(ARG_WORKSHEET, worksheet);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_add) {
      // Create the worksheet.
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);
    listAdapter =  new PreviewWorksheetAdapter(getActivity(), worksheet);
    setListAdapter(listAdapter);
    return view;
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
  public void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);

    if (null != mListener) {
      // Notify the active callbacks interface (the activity, if the
      // fragment is attached to one) that an item has been selected.
      mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
    }
  }

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
    // TODO: Update argument type and name
    public void onFragmentInteraction(String id);
  }

}
