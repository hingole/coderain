package in.shingole.maker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import in.shingole.R;
import in.shingole.maker.adapters.PreviewWorksheetAdapter;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.events.Events;

/**
 * A fragment representing a list of Items.
 */
public class CountWorksheetPreviewFragment extends BaseListFragment {

  public static final String FRAGMENT_TAG = "PREVIEW_COUNTING_WORKSHEET_FRAGMENT";
  public static final String ARG_WORKSHEET = "ARG_WORKSHEET";

  private Worksheet worksheet;

  private PreviewWorksheetAdapter listAdapter;

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
      bus.post(new Events.CreateWorksheetTappedEvent(listAdapter.getSheet()));
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    listAdapter =  new PreviewWorksheetAdapter(getActivity(), worksheet);
    setListAdapter(listAdapter);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
  }
}
