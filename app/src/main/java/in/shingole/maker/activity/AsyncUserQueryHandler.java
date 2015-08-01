package in.shingole.maker.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.squareup.otto.Bus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import in.shingole.maker.data.model.Question;
import in.shingole.maker.data.model.User;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.data.provider.MakerContentProvider;
import in.shingole.maker.data.provider.MakerContentProviderContract;
import in.shingole.maker.events.Events;

/**
 * Created by shingole on 25/5/15.
 */
public class AsyncUserQueryHandler extends AsyncQueryHandler {
  private final Bus bus;
  private final Random random;
  private final WeakReference<ContentResolver> contentResolver;

  @Inject
  public AsyncUserQueryHandler(ContentResolver cr, Bus bus) {
    super(cr);
    this.bus = bus;
    random = new Random();
    contentResolver = new WeakReference<ContentResolver>(cr);
  }

  /**
   * Inserts the given user into the database.
   */
  public int insertUser(User user) {
    int token = random.nextInt();
    startInsert(token, user, MakerContentProviderContract.User.CONTENT_URI,
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

  ContentValues getContentValuesForUser(User user) {
    ContentValues values = new ContentValues();
    values.put(MakerContentProviderContract.User.COL_USER_ID,
        user.getUserId());
    values.put(MakerContentProviderContract.User.COL_LOGIN_PROVIDER,
        user.getLoginProvider());
    values.put(MakerContentProviderContract.User.COL_USER_NAME,
        user.getUserName());
    values.put(MakerContentProviderContract.User.COL_DATE_CREATED,
        user.getDateCreated().toString());
    return values;
  }
}
