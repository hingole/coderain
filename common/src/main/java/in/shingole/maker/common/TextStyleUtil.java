package in.shingole.maker.common;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import javax.inject.Inject;

/**
 * Utilities for text style.
 */
public class TextStyleUtil {

  private final Context context;
  private CustomTypefaceSpan iconTypefaceSpan;

  @Inject
  public TextStyleUtil(
      @Annotations.ForApplication Context context,
      @Annotations.IconTypeface CustomTypefaceSpan iconTypefaceSpan) {
    this.context = context;
    this.iconTypefaceSpan = iconTypefaceSpan;
  }

  /**
   * Formats the given string by applying icon font and the given color
   * @param longDescription The string to format
   * @param color Optional color to apply. Used only if > 0.
   */
  public SpannableStringBuilder formatCountingProblemLongDesc(String longDescription, int color) {
    if (TextUtils.isEmpty(longDescription)) {
      return new SpannableStringBuilder();
    }

    SpannableStringBuilder longDesc = new SpannableStringBuilder(longDescription);
    longDesc.setSpan(iconTypefaceSpan, 0, longDesc.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    if (color != 0) {
      longDesc.setSpan(new ForegroundColorSpan(
              color),
          0, longDesc.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    return longDesc;
  }

  /**
   * Formats the short description as below. Assumes that the string is in the format
   * "2) Count the number of birds"
   * <span style="color:questionNoColor">1</span>)<span style="color:primaryColor">desc</span>
   * @param shortDescription The string to format
   * @param primaryColor The primary color for the question
   * @param questionNoColor The color to apply to question number at the start of the string.
   */
  public SpannableStringBuilder formatShortDescription(
      String shortDescription, int primaryColor, int questionNoColor) {
    if (TextUtils.isEmpty(shortDescription)) {
      return new SpannableStringBuilder();
    }
    SpannableStringBuilder shortDesc = new SpannableStringBuilder(shortDescription);
    int questionNoIndex = shortDescription.indexOf(")");
    if (questionNoIndex > 0 && questionNoColor != 0) {
      shortDesc.setSpan(
          new ForegroundColorSpan(questionNoColor),
          0, questionNoIndex, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);
    }
    if (primaryColor != 0) {
      if (questionNoIndex < 0) {
        questionNoIndex = 0;
      }
      shortDesc.setSpan(new ForegroundColorSpan(
              context.getResources().getColor(R.color.primary_dark_material_dark)),
          questionNoIndex, shortDescription.length(),
          SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    return shortDesc;
  }

  /**
   * Returns a CharSequence that applies boldface to the concatenation
   * of the specified CharSequence objects.
   */
  public static CharSequence bold(CharSequence... content) {
    return apply(content, new StyleSpan(Typeface.BOLD));
  }

  /**
   * Returns a CharSequence that applies italics to the concatenation
   * of the specified CharSequence objects.
   */
  public static CharSequence italic(CharSequence... content) {
    return apply(content, new StyleSpan(Typeface.ITALIC));
  }

  /**
   * Returns a CharSequence that applies a foreground color to the
   * concatenation of the specified CharSequence objects.
   */
  public static CharSequence color(int color, CharSequence... content) {
    return apply(content, new ForegroundColorSpan(color));
  }

  /**
   * Returns a CharSequence that concatenates the specified array of CharSequence
   * objects and then applies a list of zero or more tags to the entire range.
   *
   * @param content an array of character sequences to apply a style to
   * @param tags    the styled span objects to apply to the content
   *                such as android.text.style.StyleSpan
   */
  private static CharSequence apply(CharSequence[] content, Object... tags) {
    SpannableStringBuilder text = new SpannableStringBuilder();
    openTags(text, tags);
    for (CharSequence item : content) {
      text.append(item);
    }
    closeTags(text, tags);
    return text;
  }

  /**
   * Iterates over an array of tags and applies them to the beginning of the specified
   * Spannable object so that future text appended to the text will have the styling
   * applied to it. Do not call this method directly.
   */
  private static void openTags(Spannable text, Object[] tags) {
    for (Object tag : tags) {
      text.setSpan(tag, 0, 0, Spannable.SPAN_MARK_MARK);
    }
  }

  /**
   * "Closes" the specified tags on a Spannable by updating the spans to be
   * endpoint-exclusive so that future text appended to the end will not take
   * on the same styling. Do not call this method directly.
   */
  private static void closeTags(Spannable text, Object[] tags) {
    int len = text.length();
    for (Object tag : tags) {
      if (len > 0) {
        text.setSpan(tag, 0, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      } else {
        text.removeSpan(tag);
      }
    }
  }
}