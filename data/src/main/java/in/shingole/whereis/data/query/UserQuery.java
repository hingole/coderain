package in.shingole.whereis.data.query;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import javax.inject.Inject;

import in.shingole.whereis.common.Annotations;
import in.shingole.whereis.data.model.User;
import in.shingole.whereis.data.provider.ContentProviderContract;

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
      String id = getString(getColumnIndex(ContentProviderContract.User.COL_ID));
      User user = new User();
      user.setId(id);
      user.setUserId(getString(
          getColumnIndex(ContentProviderContract.User.COL_USER_ID)));
      user.setLoginProvider(getString(
          getColumnIndex(ContentProviderContract.User.COL_LOGIN_PROVIDER)));

      return user;
    }
  }

  @Inject
  public UserQuery(@Annotations.ForActivity Context context) {
    this.context = context;
  }

  public Loader<Cursor> createUserLoader(String userId, String provider) {
    return new CursorLoader(context,
        ContentProviderContract.User.CONTENT_URI,
        PROJECTION,
        ContentProviderContract.User.COL_USER_ID
            + " = ? and "
            + ContentProviderContract.User.COL_LOGIN_PROVIDER + " = ? ",
        new String[]{ userId, provider },
        null);
  }

  private static final String[] PROJECTION = {
      ContentProviderContract.User.COL_ID,
      ContentProviderContract.User.COL_USER_ID,
      ContentProviderContract.User.COL_USER_EMAIL,
      ContentProviderContract.User.COL_USER_NAME,
      ContentProviderContract.User.COL_LOGIN_PROVIDER,
  };
}
