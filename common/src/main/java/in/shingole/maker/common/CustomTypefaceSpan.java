package in.shingole.maker.common;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * Custom typeface that can be applied to Spannable text.
 */
public class CustomTypefaceSpan extends MetricAffectingSpan {
  private Typeface customTypeface;
  private static LruCache<String, Typeface> typefaceCache =
      new LruCache<>(4);

  public CustomTypefaceSpan(Context context, String fontFileName) {
    customTypeface = typefaceCache.get(fontFileName);
    if (customTypeface == null) {
      customTypeface = Typeface.createFromAsset(context.getAssets(),
          String.format("fonts/%s", fontFileName));
      typefaceCache.put(fontFileName, customTypeface);
    }
  }

  @Override
  public void updateMeasureState(TextPaint p) {
    p.setTypeface(customTypeface);
    //p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
  }

  @Override
  public void updateDrawState(TextPaint tp) {
    tp.setTypeface(customTypeface);
    tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
  }
}
