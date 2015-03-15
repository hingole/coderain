package in.shingole.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
              Toast.makeText(getActivity(), "An error occured while loading the worksheet",
                  Toast.LENGTH_SHORT);
            }
          });
    }
  }

  public void renderWorksheet(Worksheet worksheet) {
    this.worksheet = worksheet;
    worksheetName.setText(worksheet.getName());
    worksheetDescription.setText(worksheet.getDescription());
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
    public void loadWorksheet(String worksheetId, LoadDataCallback<Worksheet> sheet);
  }

}
