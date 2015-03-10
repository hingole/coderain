package in.shingole.app;

import dagger.Module;
import in.shingole.common.AndroidAppModule;

/**
 * Here it provides the dependencies those are used in the whole scope of your MyCoolApp
 */
@Module(
    complete = true,    // Here it enables object graph validation
    library = true,
    addsTo = AndroidAppModule.class, // Important for object graph validation at compile time
    injects = {
        SheetMakerApplication.class,
    }
)
public class SheetMakerAppScopeModule {


}
