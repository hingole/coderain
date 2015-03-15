package in.shingole.common;

/**
 * Callback when receiving data.
 */
public interface LoadDataCallback<T> {
  void onSuccess(T object);

  void onFailure(Exception ex, Object request);
}
