package in.shingole.maker.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.common.collect.ImmutableList;

import java.util.List;

import butterknife.InjectView;
import in.shingole.R;
import in.shingole.maker.data.model.WorksheetTemplate;
/**
 * Fragment to render sheet designer
 */
public class SheetDesignerFragment extends BaseFragment {

  public static final String FRAGMENT_TAG = "SHEET_DESIGNER_FRAGMENT";

  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  @InjectView(R.id.worksheetTemplatePicker) GridView worksheetTemplatePicker;

  static class TemplateMenuAdapter extends ArrayAdapter<WorksheetTemplate> {

    public TemplateMenuAdapter(Context context, List<WorksheetTemplate> menuItems) {
      super(context, R.layout.worksheet_grid_view_item, R.id.label_worksheet_name, menuItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = super.getView(position, convertView, parent);
      // TODO: Set the appropriate icon
      return view;
    }
  }

  private OnFragmentInteractionListener mListener;
  private static final List<WorksheetTemplate> templates = ImmutableList.of(
      new WorksheetTemplate("Math Addition", "Addition problems", null),
      new WorksheetTemplate("Math Counting", "Counting problems", null),
      new WorksheetTemplate("English Sight words", "Sight words", null),
      new WorksheetTemplate("Sentence completion", "Simple sentence completion", null));

  public SheetDesignerFragment() {
    super(R.layout.fragment_sheet_designer);
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
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    TemplateMenuAdapter menuAdapter = new TemplateMenuAdapter(getActivity(), templates);

    worksheetTemplatePicker.setAdapter(menuAdapter);
    worksheetTemplatePicker.setNumColumns(3);
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
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
    public void onFragmentInteraction(Uri uri);
  }

}
