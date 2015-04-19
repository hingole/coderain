package in.shingole.maker.activity;

import dagger.Module;
import in.shingole.maker.app.SheetMakerAppScopeModule;
import in.shingole.maker.fragment.CreateCountingWorksheetFragment;
import in.shingole.maker.fragment.DashboardFragment;
import in.shingole.maker.fragment.NavigationDrawerFragment;
import in.shingole.maker.fragment.SheetDesignerFragment;
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
        SheetDesignerFragment.class,
        DashboardFragment.class,
        NavigationDrawerFragment.class,
        ViewWorksheetFragment.class,
        SplashActivity.class,
        AbstractNavigationDrawerActivity.class,
        DashboardActivity.class,
        SheetDesignerActivity.class,
        ViewWorksheetActivity.class,
        CreateCountingWorksheetFragment.class
    }
)
public class SheetMakerActivityScopeModule {
}
