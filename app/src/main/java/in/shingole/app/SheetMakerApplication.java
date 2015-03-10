package in.shingole.app;

import java.util.Collections;
import java.util.List;

import in.shingole.common.DaggerApplication;

/**
 * Created by shingole on 3/7/15.
 */
public class SheetMakerApplication extends DaggerApplication {

  @Override
  protected List<Object> getAppModules() {
    return Collections.<Object>singletonList(new SheetMakerAppScopeModule());
  }
}
