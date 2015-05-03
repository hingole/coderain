package in.shingole.maker.adapters;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.shingole.maker.common.Constants;
import in.shingole.maker.data.query.WorksheetQuestionListQuery;

public class QuestionCursorPagerAdapter<F extends Fragment> extends FragmentStatePagerAdapter {
  private final Class<F> fragmentClass;
  private Cursor cursor;

  public QuestionCursorPagerAdapter(FragmentManager fm, Class<F> fragmentClass, Cursor cursor) {
    super(fm);
    this.fragmentClass = fragmentClass;
    this.cursor = cursor;
  }

  @Override
  public F getItem(int position) {
    if (cursor == null) // shouldn't happen
      return null;

    cursor.moveToPosition(position);
    F frag;
    try {
      frag = fragmentClass.newInstance();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
    Bundle args = new Bundle();
    WorksheetQuestionListQuery.QuestionCursorWrapper questionCursorWrapper =
        (WorksheetQuestionListQuery.QuestionCursorWrapper) cursor;
    args.putParcelable(Constants.PARAM_QUESTION, questionCursorWrapper.getQuestion());
    frag.setArguments(args);
    return frag;
  }

  @Override
  public int getCount() {
    if (cursor == null)
      return 0;
    else
      return cursor.getCount();
  }

  public void swapCursor(Cursor c) {
    if (cursor == c)
      return;

    this.cursor = c;
    notifyDataSetChanged();
  }

  public Cursor getCursor() {
    return cursor;
  }
}
