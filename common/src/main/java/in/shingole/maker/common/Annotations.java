package in.shingole.maker.common;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by shingole on 4/8/15.
 */
public class Annotations {
  @Qualifier
  @Retention(RUNTIME)
  public static @interface ForActivity {
  }

  @Qualifier
  @Retention(RUNTIME)
  public static @interface ForApplication {
  }
}
