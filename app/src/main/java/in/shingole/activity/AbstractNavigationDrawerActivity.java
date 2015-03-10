package in.shingole.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import in.shingole.R;
import in.shingole.common.BaseActivity;
import in.shingole.fragment.NavigationDrawerFragment;
import in.shingole.widget.LeftNavigationWidget;

public abstract class AbstractNavigationDrawerActivity extends BaseActivity
    implements NavigationDrawerFragment.NavigationDrawerCallbacks {

  protected LeftNavigationWidget navigation;
  /**
   * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
   */
  private NavigationDrawerFragment mNavigationDrawerFragment;

  /**
   * Used to store the last screen title. For use in {@link #restoreActionBar()}.
   */
  private CharSequence mTitle;
  private DrawerLayout drawerLayout;

  public AbstractNavigationDrawerActivity() {
    super(R.layout.activity_base_navigation_drawer);
  }

  public AbstractNavigationDrawerActivity(int resId) {
    super(resId);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
    mNavigationDrawerFragment = (NavigationDrawerFragment)
        getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    mTitle = getTitle();
    mNavigationDrawerFragment.setUp(
        R.id.navigation_drawer,
        (DrawerLayout) findViewById(R.id.drawer_layout));
  }

  public void onSectionAttached(int number) {
    switch (number) {
      case 1:
        mTitle = getString(R.string.title_section1);
        break;
      case 2:
        mTitle = getString(R.string.title_section2);
        break;
      case 3:
        mTitle = getString(R.string.title_section3);
        break;
    }
  }

  public void restoreActionBar() {
    if (actionBar != null) {
      actionBar.setDisplayShowTitleEnabled(true);
      actionBar.setTitle(mTitle);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (!mNavigationDrawerFragment.isDrawerOpen()) {
      // Only show items in the action bar relevant to this screen
      // if the drawer is not showing. Otherwise, let the drawer
      // decide what to show in the action bar.
      getMenuInflater().inflate(R.menu.base_navigation_drawer, menu);
      restoreActionBar();
      return true;
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (mNavigationDrawerFragment.onOptionsItemSelected(item)) {
      return true;
    }
    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public LeftNavigationWidget.NavigationType getActiveNavigationType() {
    return LeftNavigationWidget.NavigationType.SHEETS;
  }

  @Override
  public void onNavigationDrawerItemSelected(LeftNavigationWidget.NavigationType navigationType) {
    startSelectedActivity(navigationType);
  }

  // This should be overridden by concrete classes.
  protected abstract LeftNavigationWidget.NavigationType getNavigationType();

  private void initNavigationListeners() {
    navigation.setNavigationListListener(new LeftNavigationWidget.NavigationListener() {
      @Override
      public void intentsSelected(LeftNavigationWidget.NavigationType navType) {
        drawerLayout.closeDrawers();
        startSelectedActivity(navType);
      }
    });
  }

  private void startSelectedActivity(LeftNavigationWidget.NavigationType navType) {
    // TODO(shingole): Fill this up.
    switch (navType) {
      case SHEETS:
        break;
      case SETTINGS:
        break;
      default:
        break;
    }
  }

  @Override
  protected Object[] geActivitytModules() {
    return new Object[]{
        new ActivityScopeModule(this),
    };

  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_base_navigation_drawer, container, false);
      return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
      super.onAttach(activity);
      ((AbstractNavigationDrawerActivity) activity).onSectionAttached(
          getArguments().getInt(ARG_SECTION_NUMBER));
    }
  }

}
