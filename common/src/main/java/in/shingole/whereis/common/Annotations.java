package in.shingole.whereis.common;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class Annotations {
  @Qualifier
  @Retention(RUNTIME)
  public @interface ForActivity {
  }

  @Qualifier
  @Retention(RUNTIME)
  @interface ForApplication {
  }

  @Qualifier
  @Retention(RUNTIME)
  public @interface IconTypeface {
  }

}
