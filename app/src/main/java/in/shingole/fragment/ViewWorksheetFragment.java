package in.shingole.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.shingole.R;
import in.shingole.common.BaseFragment;
import in.shingole.common.Constants;
import in.shingole.common.LoadDataCallback;
import in.shingole.data.model.Worksheet;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewWorksheetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ViewWorksheetFragment extends BaseFragment {

  public static final String FRAGMENT_TAG = "ViewWorksheetFragment";
  private Worksheet worksheet;

  @InjectView(R.id.label_worksheet_name) TextView worksheetName;
  @InjectView(R.id.label_worksheet_description) TextView worksheetDescription;

  private OnFragmentInteractionListener mListener;

  public ViewWorksheetFragment() {
    // Required empty public constructor
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
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
    if (getArguments() != null && getArguments().getString(Constants.PARAM_WORKSHEET_ID) != null) {
      mListener.loadWorksheet(getArguments().getString(Constants.PARAM_WORKSHEET_ID),
          new LoadDataCallback<Worksheet>() {
            @Override
            public void onSuccess(Worksheet worksheet) {
              renderWorksheet(worksheet);
            }

            @Override
            public void onFailure(Exception ex, Object request) {
              Toast toast = Toast.makeText(getActivity(),
                  "An error occurred while loading the worksheet",
                  Toast.LENGTH_SHORT);
              toast.show();
            }
          });
    }
  }

  public void renderWorksheet(Worksheet worksheet) {
    this.worksheet = worksheet;
    if (worksheet != null) {
      worksheetName.setText(worksheet.getName());
      worksheetDescription.setText(worksheet.getDescription());
    } else {
      worksheetName.setText("Invalid worksheet");
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

  /**
   * Fragment interface.
   */
  public interface OnFragmentInteractionListener {
    public void loadWorksheet(String worksheetId, LoadDataCallback<Worksheet> sheet);
  }

}
