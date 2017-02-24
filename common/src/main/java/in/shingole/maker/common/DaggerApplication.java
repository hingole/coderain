package in.shingole.maker.common;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Base class of a dagger enabled application
 */
public abstract class DaggerApplication extends Application implements Injector {

  private ObjectGraph mObjectGraph;

  @Override
  public void onCreate() {
    super.onCreate();

    AndroidAppModule sharedAppModule = new AndroidAppModule();

    // bootstrap. So that it allows no-arg constructor in AndroidAppModule
    sharedAppModule.applicationContext = this.getApplicationContext();

    List<Object> modules = new ArrayList<>();
    modules.add(sharedAppModule);

    modules.addAll(getAppModules());

    mObjectGraph = ObjectGraph.create(modules.toArray());

    mObjectGraph.inject(this);
  }

  protected abstract List<Object> getAppModules();

  @Override
  public void inject(Object object) {
    mObjectGraph.inject(object);
  }

  @Override
  public ObjectGraph getObjectGraph() {
    return mObjectGraph;
  }
}