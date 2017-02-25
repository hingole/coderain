package in.shingole.whereis.common;


import android.content.Context;
import android.location.LocationManager;
import android.view.LayoutInflater;

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

  /* package */ Context applicationContext = null;


  @Provides @Singleton
  LayoutInflater provideLayoutInflator() {
    return (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  /**
   * Allow the application context to be injected but require that it be annotated with
   * {@link in.shingole.whereis.common.Annotations.ForApplication @Annotation} to explicitly
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