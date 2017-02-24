package in.shingole.whereis.common;

import android.app.Activity;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import in.shingole.whereis.common.Annotations;

/**
 * Base activity scope module.
 */
@Module(
    complete = true,    // Here we enable object graph validation
    library = true
)
public class ActivityScopeModule {

  private final Activity activity;

  public ActivityScopeModule(Activity activity) {
    this.activity = activity;
  }

  @Provides
  @Singleton
  @Annotations.ForActivity
  Context providesActivityContext() {
    return activity;
  }

  @Provides
  @Singleton
  Activity providesActivity() {
    return activity;
  }
}
