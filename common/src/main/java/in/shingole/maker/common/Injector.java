package in.shingole.maker.common;

import dagger.ObjectGraph;

/**
 * An instance which is capable of injecting dependencies.
 */
public interface Injector {
  /**
   * Inject to <code>object</code>
   */
  void inject(Object object);

  /**
   * Returns the object graph. Can be used for lazy injection.
   */
  ObjectGraph getObjectGraph();
}