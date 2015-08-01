package in.shingole.maker.data.query;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import javax.inject.Inject;

import in.shingole.maker.common.Annotations;
import in.shingole.maker.data.model.User;
import in.shingole.maker.data.provider.MakerContentProviderContract;

/**
 * Query object for User.
 */
public class UserQuery {

  private final Context context;

  public static class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
      super(cursor);
    }

    public User getUser() {
      String id = getString(getColumnIndex(MakerContentProviderContract.User.COL_ID));
      User user = new User();
      user.setId(id);
      user.setUserId(getString(
          getColumnIndex(MakerContentProviderContract.User.COL_USER_ID)));
      user.setLoginProvider(getString(
          getColumnIndex(MakerContentProviderContract.User.COL_LOGIN_PROVIDER)));

      return user;
    }
  }

  @Inject
  public UserQuery(@Annotations.ForActivity Context context) {
    this.context = context;
  }

  public Loader<Cursor> createUserLoader(String userId, String provider) {
    return new CursorLoader(context,
        MakerContentProviderContract.User.CONTENT_URI,
        PROJECTION,
        MakerContentProviderContract.User.COL_USER_ID
            + " = ? and "
            + MakerContentProviderContract.User.COL_LOGIN_PROVIDER + " = ? ",
        new String[]{ userId, provider },
        null);
  }

  private static final String[] PROJECTION = {
      MakerContentProviderContract.User.COL_ID,
      MakerContentProviderContract.User.COL_USER_ID,
      MakerContentProviderContract.User.COL_USER_EMAIL,
      MakerContentProviderContract.User.COL_USER_NAME,
      MakerContentProviderContract.User.COL_LOGIN_PROVIDER,
  };
}
