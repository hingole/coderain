package in.shingole.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.shingole.R;

/**
 * Left navigation menu.
 */
public class LeftNavigationWidget extends LinearLayout {

  private static List<NavigationType> navigationTypes = Arrays.asList(NavigationType.SHEETS,
      NavigationType.SETTINGS);
  private NavigationListener listener;
  private NavigationType active;
  private Map<NavigationType, NavigationItemWidget> navigationItems = new HashMap<>();
  private View settingsDivider;

  public LeftNavigationWidget(Context context) {
    this(context, null);
  }

  public LeftNavigationWidget(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LeftNavigationWidget(Context context, AttributeSet attrs, int defaultStyle) {
    super(context, attrs, defaultStyle);

    for (NavigationType navType : navigationTypes) {
      NavigationItemWidget widget = createNavigationItemView(context, navType);
      navigationItems.put(navType, widget);
      this.addView(widget);
      if (navType == NavigationType.SHEETS) {
        View divider =
            new View(getContext());
        ViewGroup.LayoutParams layoutParams =
            new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.divider_thickness));
        divider.setLayoutParams(layoutParams);
        divider.setBackgroundColor(getResources().getColor(R.color.gray_divider));

        this.addView(divider);
      }
    }

    setActiveNavigation(NavigationType.SHEETS);
  }

  private NavigationItemWidget createNavigationItemView(Context context, NavigationType navType) {

    final NavigationItemWidget itemView = new NavigationItemWidget(context);

    itemView.initView(navType);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectItem(itemView);
      }
    });

    return itemView;
  }

  private void selectItem(NavigationItemWidget listItemWidget) {
    if (listener != null) {
      listener.intentsSelected(listItemWidget.getNavigationType());
    }
  }

  public void setNavigationListListener(NavigationListener listener) {
    this.listener = listener;
  }

  public void setActiveNavigation(NavigationType navType) {
    NavigationItemWidget navItem = navigationItems.get(active);
    if (active != null && navItem != null) {
      navItem.setActive(false);
    }

    navItem = navigationItems.get(navType);
    if (navType != null && navItem != null) {
      active = navType;
      navItem.setActive(true);
    }
  }

  /**
   * Enumeration for icons and labels for the navigation menu.
   */
  public enum NavigationType {
    SHEETS(R.string.menu_sheets),
    SETTINGS(R.string.menu_settings);

    private final int activeIconId;
    private final int iconId;
    private final int textId;

    /**
     * @param textId navigation option name/text.
     */
    NavigationType(int textId) {
      this.activeIconId = R.drawable.settings;
      this.iconId = R.drawable.settings;
      this.textId = textId;
    }

    /**
     * @return iconId for navigation option when it is the selected/active navigation.
     */
    public int getActiveIconId() {
      return activeIconId;
    }

    /**
     * @return iconId for navigation option.
     */
    public int getIconId() {
      return iconId;
    }

    /**
     * @return textId for navigation option name.
     */
    public int getTextId() {
      return textId;
    }
  }

  /**
   * A callback listener for menu choices.
   */
  public interface NavigationListener {

    void intentsSelected(NavigationType navType);
  }
}