package in.shingole.whereis.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.squareup.otto.Bus;

import java.lang.ref.WeakReference;
import java.util.Random;

import javax.inject.Inject;

import in.shingole.whereis.data.model.User;
import in.shingole.whereis.data.provider.ContentProviderContract;
import in.shingole.whereis.events.Events;

public class AsyncUserQueryHandler extends AsyncQueryHandler {
  private final Bus bus;
  private final Random random;
  private final WeakReference<ContentResolver> contentResolver;

  @Inject
  public AsyncUserQueryHandler(ContentResolver cr, Bus bus) {
    super(cr);
    this.bus = bus;
    random = new Random();
    contentResolver = new WeakReference<>(cr);
  }

  /**
   * Inserts the given user into the database.
   */
  public int insertUser(User user) {
    int token = random.nextInt();
    startInsert(token, user, ContentProviderContract.User.CONTENT_URI,
        getContentValuesForUser(user));
    return token;
  }

  @Override
  protected void onUpdateComplete(int token, Object cookie, int result) {
    bus.post(new Events.AsyncOperationCompleteEvent(token, result));
  }

  @Override
  protected void onDeleteComplete(int token, Object cookie, int result) {
    bus.post(new Events.AsyncOperationCompleteEvent(token, result));
  }

  @Override
  protected void onInsertComplete(int token, Object cookie, Uri uri) {
    if (cookie instanceof User) {
      if (uri == null) {
        return;
      }
      bus.post(new Events.InsertOperationCompleteEvent(token, uri));
    }
  }

  private ContentValues getContentValuesForUser(User user) {
    ContentValues values = new ContentValues();
    values.put(ContentProviderContract.User.COL_USER_ID,
        user.getUserId());
    values.put(ContentProviderContract.User.COL_LOGIN_PROVIDER,
        user.getLoginProvider());
    values.put(ContentProviderContract.User.COL_USER_NAME,
        user.getUserName());
    values.put(ContentProviderContract.User.COL_DATE_CREATED,
        user.getDateCreated().toString());
    return values;
  }
}
