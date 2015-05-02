package in.shingole.maker.app;

import android.content.pm.ApplicationInfo;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.Collections;
import java.util.List;

import dagger.ObjectGraph;
import in.shingole.maker.common.DaggerApplication;

/**
 * Application class
 */
public class SheetMakerApplication extends DaggerApplication {

  private ObjectGraph mObjectGraph;

  @Override
  public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics());
    boolean isDebuggable = (0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE));
    if (isDebuggable) {
      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
          .detectDiskReads()
          .detectDiskWrites()
          .detectNetwork()   // or .detectAll() for all detectable problems
          .penaltyLog()
          .build());
      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
          .detectLeakedSqlLiteObjects()
          .detectLeakedClosableObjects()
          .penaltyLog()
          .penaltyDeath()
          .build());
    }
  }

  @Override
  protected List<Object> getAppModules() {
    return Collections.<Object>singletonList(new SheetMakerAppScopeModule());
  }
}
