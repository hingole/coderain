package in.shingole.maker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import in.shingole.R;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.data.util.WorksheetUtil;
import in.shingole.maker.events.Events;

/**
 * Fragment for creating counting worksheets.
 */
public class CreateCountingWorksheetFragment extends BaseFragment {

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

  @Override
  public void onResume() {
    super.onResume();
    initView();
  }

  private void initView() {
    level1.setChecked(countUpto == 5);
    level2.setChecked(countUpto == 10);
    level3.setChecked(countUpto == 15);
  }

  Map<String, String> getIconMap() {
     return ImmutableMap.<String, String>builder()
         .put(getString(R.string.maker_icon_smart_phone),
             getString(R.string.maker_icon_smart_phone_label))
         .put(getString(R.string.maker_icon_book),
             getString(R.string.maker_icon_book_label))
         .put(getString(R.string.maker_icon_cart),
             getString(R.string.maker_icon_cart_label))
         .put(getString(R.string.maker_icon_lifebuoy),
             getString(R.string.maker_icon_lifebuoy_label))
         .put(getString(R.string.maker_icon_binoculars),
             getString(R.string.maker_icon_binoculars_label))
         .put(getString(R.string.maker_icon_bug),
             getString(R.string.maker_icon_bug_label))
         .put(getString(R.string.maker_icon_trophy),
             getString(R.string.maker_icon_trophy_label))
         .put(getString(R.string.maker_icon_gift),
             getString(R.string.maker_icon_gift_label))
         .put(getString(R.string.maker_icon_mug),
             getString(R.string.maker_icon_mug_label))
         .put(getString(R.string.maker_icon_leaf),
             getString(R.string.maker_icon_leaf_label))
         .put(getString(R.string.maker_icon_airplane),
             getString(R.string.maker_icon_airplane_label))
         .put(getString(R.string.maker_icon_truck),
             getString(R.string.maker_icon_truck_label))
         .put(getString(R.string.maker_icon_man),
             getString(R.string.maker_icon_man_label))
         .put(getString(R.string.maker_icon_woman),
             getString(R.string.maker_icon_woman_label))
         .put(getString(R.string.maker_icon_scissors),
             getString(R.string.maker_icon_scissors_label))
         .build();
  }

  @OnClick ({
      R.id.counting_worksheet_level1,
      R.id.counting_worksheet_level2,
      R.id.counting_worksheet_level3})
  void handleWorksheetLevelRadioButtonTapped(View checkBox) {
    switch(checkBox.getId()) {
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

  @OnClick(R.id.create_counting_worksheet_button)
  public void onCreateCountingWorksheetButtonClicked() {
     Worksheet sheet = worksheetUtil.createDraftMathCountingWorksheet(countUpto, 10, getIconMap());
     bus.post(new Events.CreateWorksheetTappedEvent(sheet));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_add) {
      onCreateCountingWorksheetButtonClicked();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
