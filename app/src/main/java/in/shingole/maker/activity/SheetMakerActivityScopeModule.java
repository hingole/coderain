package in.shingole.maker.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import in.shingole.maker.app.SheetMakerAppScopeModule;
import in.shingole.maker.common.ActivityScopeModule;
import in.shingole.maker.common.Annotations;
import in.shingole.maker.fragment.DashboardFragment;
import in.shingole.maker.fragment.LoginFragment;
import in.shingole.maker.fragment.NavigationDrawerFragment;
import in.shingole.maker.fragment.SplashFragment;

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
