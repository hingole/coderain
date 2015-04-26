package in.shingole.maker.fragment;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;
import in.shingole.R;
import in.shingole.maker.common.CustomTypefaceSpan;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.data.util.WorksheetUtil;

/**
 * Fragment for creating counting worksheets.
 */
public class CreateCountingWorksheetFragment extends BaseFragment
    implements View.OnClickListener {

  private OnFragmentInteractionListener mListener;
  public static final String FRAGMENT_TAG = "CREATE_COUNTING_WORKSHEET_FRAGMENT";
  private static final String COUNT_UPTO_KEY = FRAGMENT_TAG + ".countUpto";

  @Inject
  WorksheetUtil worksheetUtil;

  private int countUpto = 5;
  @InjectView(R.id.pick_character_grid_container) GridLayout characterPickerGridView;
  @InjectView(R.id.counting_worksheet_levels) RadioGroup worksheetLevels;
  @InjectView(R.id.counting_worksheet_level1) RadioButton level1;
  @InjectView(R.id.counting_worksheet_level2) RadioButton level2;
  @InjectView(R.id.counting_worksheet_level3) RadioButton level3;
  @InjectView(R.id.create_counting_worksheet_button) Button createCountingWorksheetButton;

  public CreateCountingWorksheetFragment() {
    super(R.layout.fragment_create_counting_worksheet);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_create_counting_worksheet, container, false);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(COUNT_UPTO_KEY, countUpto);
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (savedInstanceState != null && savedInstanceState.containsKey(COUNT_UPTO_KEY)) {
      countUpto = savedInstanceState.getInt(COUNT_UPTO_KEY);
    }
  }

  private void initView() {
    level1.setChecked(countUpto == 5);
    level2.setChecked(countUpto == 10);
    level3.setChecked(countUpto == 15);
  }

  Map<String, String> getIconMap() {
     return ImmutableMap.<String, String>builder()
         .put(getString(R.string.maker_icon_smart_phone), getString(R.string.maker_icon_smart_phone_label))
         .put(getString(R.string.maker_icon_book), getString(R.string.maker_icon_book_label))
         .put(getString(R.string.maker_icon_cart), getString(R.string.maker_icon_cart_label))
         .put(getString(R.string.maker_icon_lifebuoy), getString(R.string.maker_icon_lifebuoy_label))
         .put(getString(R.string.maker_icon_binoculars), getString(R.string.maker_icon_binoculars_label))
         .put(getString(R.string.maker_icon_bug), getString(R.string.maker_icon_bug_label))
         .put(getString(R.string.maker_icon_trophy), getString(R.string.maker_icon_trophy_label))
         .put(getString(R.string.maker_icon_gift), getString(R.string.maker_icon_gift_label))
         .put(getString(R.string.maker_icon_mug), getString(R.string.maker_icon_mug_label))
         .put(getString(R.string.maker_icon_leaf), getString(R.string.maker_icon_leaf_label))
         .put(getString(R.string.maker_icon_airplane), getString(R.string.maker_icon_airplane_label))
         .put(getString(R.string.maker_icon_truck), getString(R.string.maker_icon_truck_label))
         .put(getString(R.string.maker_icon_man), getString(R.string.maker_icon_man_label))
         .put(getString(R.string.maker_icon_woman), getString(R.string.maker_icon_woman_label))
         .put(getString(R.string.maker_icon_scissors), getString(R.string.maker_icon_scissors_label))
         .build();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    worksheetLevels.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId) {
          case R.id.counting_worksheet_level1:
            countUpto = 5;
            break;
          case R.id.counting_worksheet_level2:
            countUpto = 10;
            break;
          case R.id.counting_worksheet_level3:
            countUpto = 15;
            break;

        }
      }
    });
    createCountingWorksheetButton.setOnClickListener(this);
//    String icons = "";
//    for (String icon : getIconMap().keySet()) {
//      icons += icon + " " ;
//    }
//    SpannableStringBuilder longDesc = new SpannableStringBuilder(icons);
//    CustomTypefaceSpan iconTypefaceSpan = new CustomTypefaceSpan(getActivity(), "maker.ttf");
//
//    longDesc.setSpan(iconTypefaceSpan, 0, longDesc.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//    //for (String icon : getIconMap().keySet()) {
//      TextView character = (TextView) LayoutInflater.from(
//          getActivity()).inflate(R.layout.character_grid_view_item,
//          characterPickerGridView, false);
//      character.setText(lon);
//      characterPickerGridView.addView(character);
//    //}
//    //final Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/maker.ttf");
//    ArrayAdapter charactersArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, characters) {
//      @Override
//      public View getView(int position, View convertView, ViewGroup parent) {
//        TextView view;
//        if (convertView == null) {
//            view = (TextView) LayoutInflater.from(
//                getContext()).inflate(R.layout.character_grid_view_item, parent, false);
//        } else {
//            view = (TextView) convertView;
//        }
//        view.setText((String) getItem(position));
//        return view;
//      }
//    };
//    characterPickerGridView.setAdapter(charactersArrayAdapter);
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
  public void onClick(View v) {
   switch(v.getId()) {
     case R.id.create_counting_worksheet_button:
       Worksheet sheet = worksheetUtil.createDraftMathCountingWorksheet(countUpto, 10, getIconMap());
       mListener.handleDraftWorksheetCreated(sheet);
       break;
     default:
   }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_add) {
      Worksheet sheet = worksheetUtil.createDraftMathCountingWorksheet(countUpto, 10, getIconMap());
      mListener.handleDraftWorksheetCreated(sheet);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Interface that the container activity should implement.
   */
  public interface OnFragmentInteractionListener {

    // Event fired after creating a worksheet based on user input.
    public void handleDraftWorksheetCreated(Worksheet worksheet);
  }

}
