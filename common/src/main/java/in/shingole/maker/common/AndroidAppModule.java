package in.shingole.maker.common;


import android.content.Context;
import android.location.LocationManager;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    complete = false,
    library = true,
    injects = {
    }
)
public class AndroidAppModule {

  /* package */ static Context applicationContext = null;

  /**
   * Allow the application context to be injected but require that it be annotated with
   * {@link in.shingole.maker.common.Annotations.ForApplication @Annotation} to explicitly
   * differentiate it from an activity context.
   */
  @Provides
  @Singleton
  @Annotations.ForApplication
  Context provideApplicationContext() {
    return applicationContext;
  }

  @Provides
  @Singleton
  LocationManager provideLocationManager() {
    return (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
  }

  @Provides @Singleton
  Bus provideBus() {
    return new Bus();
  }
}