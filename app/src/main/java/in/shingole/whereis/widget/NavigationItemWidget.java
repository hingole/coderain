package in.shingole.whereis.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.shingole.R;

/**
 * A template widget for navigation list item.
 */
public class NavigationItemWidget extends LinearLayout {
  private LeftNavigationWidget.NavigationType navigationType = null;
  private TextView textView;

  public NavigationItemWidget(Context context) {
    this(context, null);
  }

  public NavigationItemWidget(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public NavigationItemWidget(Context context, AttributeSet attrs, int defaultStyle) {
    super(context, attrs, defaultStyle);
    View v = View.inflate(context, R.layout.navigation_list_item, this);
    textView = (TextView) v.findViewById(R.id.navigation_list_item_textview);
  }

  /**
   * Initialize the navigation list item with the text and icon from @item
   */
  public void initView(LeftNavigationWidget.NavigationType item) {
    this.navigationType = item;
    setActive(false);
    textView.setText(item.getTextId());
  }

  public void setActive(boolean active) {
    if (active) {
      int activeIconId = navigationType.getActiveIconId();
      textView.setTextColor(getContext().getResources().getColor(R.color.teal));
      textView.setTag(activeIconId);
      textView.setCompoundDrawablesWithIntrinsicBounds(activeIconId, 0, 0, 0);
    } else {
      int iconId = navigationType.getIconId();
      textView.setTextColor(getContext().getResources().getColor(R.color.black));
      textView.setTag(iconId);
      textView.setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0);
    }
  }

  public LeftNavigationWidget.NavigationType getNavigationType() {
    return navigationType;
  }
}