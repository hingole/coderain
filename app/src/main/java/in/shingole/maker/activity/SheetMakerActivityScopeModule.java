package in.shingole.maker.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import in.shingole.maker.app.SheetMakerAppScopeModule;
import in.shingole.maker.common.Annotations;
import in.shingole.maker.fragment.CountWorksheetPreviewFragment;
import in.shingole.maker.fragment.CreateCountingWorksheetFragment;
import in.shingole.maker.fragment.DashboardFragment;
import in.shingole.maker.fragment.NavigationDrawerFragment;
import in.shingole.maker.fragment.SplashFragment;
import in.shingole.maker.fragment.ViewWorksheetFragment;
import in.shingole.maker.common.ActivityScopeModule;
import in.shingole.maker.data.query.WorksheetQueryModule;

/**
 * Created by shingole on 4/9/15.
 */
@Module(
    complete = true,    // Here we enable object graph validation
    library = true,
    includes = { ActivityScopeModule.class, WorksheetQueryModule.class },
    addsTo = SheetMakerAppScopeModule.class, // Important for object graph validation at compile time
    injects = {
        SplashFragment.class,
        DashboardFragment.class,
        NavigationDrawerFragment.class,
        ViewWorksheetFragment.class,
        SplashActivity.class,
        AbstractNavigationDrawerActivity.class,
        DashboardActivity.class,
        SheetDesignerActivity.class,
        ViewWorksheetActivity.class,
        CreateCountingWorksheetFragment.class,
        CountWorksheetPreviewFragment.class,
        AsyncQueryHandler.class,
    }
)
public class SheetMakerActivityScopeModule {

  @Provides
  ContentResolver provideContentResolver(@Annotations.ForActivity Context context) {
    return context.getContentResolver();
  }
}
