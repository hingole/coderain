package in.shingole.app;

import java.util.Collections;
import java.util.List;

import in.shingole.common.DaggerApplication;

/**
 * Application class
 */
public class SheetMakerApplication extends DaggerApplication {

  @Override
  protected List<Object> getAppModules() {
    return Collections.<Object>singletonList(new SheetMakerAppScopeModule());
  }
}
