package in.shingole.maker.common;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * A no-op {TextWatcher} to reduce verboseness in case you don't want to override all
 * of the methods in it.
 */
public class TextChangeHandler implements TextWatcher {
  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
  }

  @Override
  public void afterTextChanged(Editable s) {
  }
}
