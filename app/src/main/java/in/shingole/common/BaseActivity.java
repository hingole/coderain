package in.shingole.common;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import dagger.ObjectGraph;
import in.shingole.R;

public abstract class BaseActivity
    extends ActionBarActivity
    implements Injector {

  private final int baseLayoutId;
  protected ActionBar actionBar;
  private ObjectGraph mActivityGraph;

  public BaseActivity(int baseLayoutId) {
    this.baseLayoutId = baseLayoutId;
  }

  public BaseActivity() {
    this(R.layout.base_layout);
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Create the activity graph by .plus-ing our modules onto the application graph.
    DaggerApplication application = (DaggerApplication) getApplication();
    mActivityGraph = application.getObjectGraph().plus(geActivitytModules());

    // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
    mActivityGraph.inject(this);

    setContentView(baseLayoutId);
    initActionBar();
  }

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

  private void initActionBar() {
    actionBar = this.getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeButtonEnabled(true);
      actionBar.setHomeAsUpIndicator(R.drawable.quantum_ic_arrow_back_white_24);
    }
  }

  @Override
  protected void onDestroy() {
    // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
    // soon as possible.
    mActivityGraph = null;

    super.onDestroy();
  }

  protected <T> T getView(int id) {
    return (T) findViewById(id);
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

  protected abstract Object[] geActivitytModules();
}
