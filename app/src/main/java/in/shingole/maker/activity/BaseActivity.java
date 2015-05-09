package in.shingole.maker.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import dagger.ObjectGraph;
import in.shingole.R;
import in.shingole.maker.common.ActivityScopeModule;
import in.shingole.maker.common.DaggerApplication;
import in.shingole.maker.common.Injector;
import in.shingole.maker.common.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends ActionBarActivity implements Injector {

  private final int baseLayoutId;
  protected ActionBar actionBar;
  private ObjectGraph mActivityGraph;

  @Inject
  protected Bus bus;

  public BaseActivity(int baseLayoutId){
    this.baseLayoutId = baseLayoutId;
  }

  public BaseActivity() {
    this(R.layout.base_layout);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // Create the activity graph by .plus-ing our modules onto the application graph.
    // This needs to be done before calling super.onCreate
    DaggerApplication application = (DaggerApplication) getApplication();
    mActivityGraph = application.getObjectGraph().plus(getActivityModules());
    super.onCreate(savedInstanceState);

    // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
    mActivityGraph.inject(this);

    setContentView(baseLayoutId);
    initActionBar();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  /**
   * Returns the id of the main container view. This is where fragments will be added.
   */
  protected int getContentFrameId() {
    return R.id.content_frame;
  }

  protected <T extends Fragment> T addFragment(
      String fragmentTag, Class<T> fragmentClass, Bundle savedInstanceState) {
    T fragment = Utils.getFragment(
        this,
        fragmentTag,
        fragmentClass,
        savedInstanceState);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().replace(getContentFrameId(),
          fragment, fragmentTag).commit();
    }
    return fragment;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  private void initActionBar() {
    actionBar = this.getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeButtonEnabled(true);
      actionBar.setHomeAsUpIndicator(R.drawable.quantum_ic_arrow_back_white_24);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    bus.unregister(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    bus.register(this);
  }

  @Override
  protected void onDestroy() {
    // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
    // soon as possible.
    mActivityGraph = null;
    super.onDestroy();
  }

  /**
   * Inject the supplied {@code object} using the activity-specific graph.
   */
  @Override
  public void inject(Object object) {
    mActivityGraph.inject(object);
  }

  public ObjectGraph getObjectGraph() {
    return mActivityGraph;
  }

  protected Object[] getActivityModules() {
    return new Object[]{
        new ActivityScopeModule(this), new SheetMakerActivityScopeModule()
    };
  }
}
