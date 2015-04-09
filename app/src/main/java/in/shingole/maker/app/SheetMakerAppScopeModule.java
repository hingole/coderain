package in.shingole.maker.app;

import dagger.Module;
import in.shingole.maker.common.AndroidAppModule;

/**
 * Provide the dependencies that are used in the whole scope of the application
 */
@Module(
    complete = true,    // Here it enables object graph validation
    library = true,
    includes = AndroidAppModule.class, // Important for object graph validation at compile time
    injects = {
        SheetMakerApplication.class,
    }
)
public class SheetMakerAppScopeModule {

}
