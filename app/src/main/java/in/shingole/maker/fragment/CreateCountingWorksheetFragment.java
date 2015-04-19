package in.shingole.maker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.InjectView;
import in.shingole.R;
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

  private int countUpto = 5;
  //@InjectView(R.id.pick_character_grid_container) GridLayout characterPickerGridView;
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
      countUpto = Integer.valueOf(COUNT_UPTO_KEY);
    }
  }

  private void initView() {
    level1.setChecked(countUpto == 5);
    level2.setChecked(countUpto == 10);
    level3.setChecked(countUpto == 15);
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
//    final List<String> characters = ImmutableList.<String>builder()
//      .add(getString(R.string.maker_icon_smart_phone))
//      .add(getString(R.string.maker_icon_book))
//      .add(getString(R.string.maker_icon_cart))
//      .add(getString(R.string.maker_icon_lifebuoy))
//      .add(getString(R.string.maker_icon_binoculars))
//      .add(getString(R.string.maker_icon_bug))
//      .add(getString(R.string.maker_icon_trophy))
//      .add(getString(R.string.maker_icon_gift))
//      .add(getString(R.string.maker_icon_mug))
//      .add(getString(R.string.maker_icon_leaf))
//      .add(getString(R.string.maker_icon_airplane))
//      .add(getString(R.string.maker_icon_truck))
//      .add(getString(R.string.maker_icon_man))
//      .add(getString(R.string.maker_icon_woman))
//      .add(getString(R.string.maker_icon_scissors))
//      .build();
//
//    for (String icon : characters) {
//      TextView character = (TextView) LayoutInflater.from(
//          getActivity()).inflate(R.layout.character_grid_view_item,
//          characterPickerGridView, false);
//      character.setText(icon);
//      characterPickerGridView.addView(character);
//    }
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
       WorksheetUtil util = new WorksheetUtil();
       Worksheet sheet = util.createDraftMathCountingWorksheet(countUpto, 10);
       mListener.handleDraftWorksheetCreated(sheet);
       break;
     default:
   }
  }

  /**
   * Interface that the container activity should implement.
   */
  public interface OnFragmentInteractionListener {

    // Event fired after creating a worksheet based on user input.
    public void handleDraftWorksheetCreated(Worksheet worksheet);
  }

}
