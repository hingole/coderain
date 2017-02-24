package in.shingole.whereis.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import in.shingole.whereis.app.SheetMakerAppScopeModule;
import in.shingole.whereis.common.ActivityScopeModule;
import in.shingole.whereis.common.Annotations;
import in.shingole.whereis.fragment.DashboardFragment;
import in.shingole.whereis.fragment.LoginFragment;
import in.shingole.whereis.fragment.NavigationDrawerFragment;
import in.shingole.whereis.fragment.SplashFragment;

@Module(
    library = true,
    includes = { ActivityScopeModule.class },
    addsTo = SheetMakerAppScopeModule.class, // Important for object graph validation at compile time
    injects = {
        SplashFragment.class,
        DashboardFragment.class,
        LoginFragment.class,
        NavigationDrawerFragment.class,
        SplashActivity.class,
        AbstractNavigationDrawerActivity.class,
        DashboardActivity.class,
        AsyncQueryHandler.class,
    }
)
class SheetMakerActivityScopeModule {

  @Provides
  ContentResolver provideContentResolver(@Annotations.ForActivity Context context) {
    return context.getContentResolver();
  }
}
