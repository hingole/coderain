package in.shingole.activity;

import android.app.Activity;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import in.shingole.app.SheetMakerAppScopeModule;
import in.shingole.common.BaseActivity;
import in.shingole.common.ForActivity;
import in.shingole.fragment.DashboardFragment;
import in.shingole.fragment.NavigationDrawerFragment;
import in.shingole.fragment.SheetDesignerFragment;
import in.shingole.fragment.SplashFragment;
import in.shingole.fragment.ViewWorksheetFragment;

/**
 * Here it provides the dependencies those have same lifetime of one activity in your MyCoolApp
 */
@Module(
    complete = true,    // Here we enable object graph validation
    library = true,
    addsTo = SheetMakerAppScopeModule.class, // Important for object graph validation at compile time
    injects = {
        SplashFragment.class,
        SheetDesignerFragment.class,
        DashboardFragment.class,
        NavigationDrawerFragment.class,
        ViewWorksheetFragment.class,
        SplashActivity.class,
        AbstractNavigationDrawerActivity.class,
        DashboardActivity.class,
        SheetDesignerActivity.class,
        ViewWorksheetActivity.class,
    }
)
public class ActivityScopeModule {

  private final BaseActivity mActivity;

  public ActivityScopeModule(BaseActivity activity) {
    mActivity = activity;
  }

  @Provides
  @Singleton
  @ForActivity
  Context providesActivityContext() {
    return mActivity;
  }

  @Provides
  @Singleton
  Activity providesActivity() {
    return mActivity;
  }
}