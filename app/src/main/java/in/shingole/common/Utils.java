package in.shingole.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.google.common.base.Preconditions;

/**
 * Created by shingole on 3/8/15.
 */
public class Utils {

  public static String getLogTag(Class<?> clazz) {
    return "rugbyadmin-" + clazz.getSimpleName();
  }

  /**
   * Gets a fragment and creates it if it doesn't exists when searching by tag.
   */
  @SuppressWarnings("unchecked") // guaranteed by the generic type
  public static <T extends Fragment> T getFragment(FragmentActivity fragmentActivity, String tag,
                                                   Class<T> clazz, Bundle fragmentArguments) {
    Preconditions.checkNotNull(fragmentActivity);
    Preconditions.checkNotNull(clazz);
    Preconditions.checkNotNull(tag);

    Fragment fragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(tag);
    if (fragment == null) {
      fragment = Fragment.instantiate(fragmentActivity, clazz.getName(), fragmentArguments);
    }
    return (T) fragment;
  }
}