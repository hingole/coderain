package in.shingole.maker.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.otto.Bus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import in.shingole.maker.common.Utils;
import in.shingole.maker.data.model.Question;
import in.shingole.maker.data.provider.MakerContentProvider;
import in.shingole.maker.data.provider.MakerContentProviderContract;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.events.Events;

/**
 * Handler that will perform asynchronous operations on the content provider.
 */
public class AsyncWorksheetQueryHandler extends AsyncQueryHandler {

  private static final int EVENT_ARG_BULK_INSERT = 101;

  private final Random random;
  private final Bus bus;
  private WeakReference<ContentResolver> contentResolver;
  private Handler mWorkerThreadHandler;


  @Inject
  public AsyncWorksheetQueryHandler(ContentResolver cr, Bus bus) {
    super(cr);
    this.bus = bus;
    random = new Random();
    contentResolver = new WeakReference<ContentResolver>(cr);
  }

  protected static final class BulkWorkerArgs {
    public Uri uri;
    public Handler handler;
    public String[] projection;
    public String selection;
    public String[] selectionArgs;
    public String orderBy;
    public Object result;
    public Object cookie;
    public ContentValues[] values;
  }

  /**
   * Override the createHandler method to return a custom handler that can handle bulk create.
   */
  protected Handler createHandler(Looper looper) {
    mWorkerThreadHandler = new WorkerHandler(looper) {

      @Override
      public void handleMessage(Message msg) {
        final ContentResolver resolver = contentResolver.get();
        if (resolver == null) return;
        int token = msg.what;
        int event = msg.arg1;
        if (event == EVENT_ARG_BULK_INSERT) {
          BulkWorkerArgs args = (BulkWorkerArgs) msg.obj;
          args.result = resolver.bulkInsert(args.uri, args.values);

          // passing the original token value back to the caller
          // on top of the event values in arg1.
          Message reply = args.handler.obtainMessage(token);
          reply.obj = args;
          reply.arg1 = msg.arg1;
          reply.sendToTarget();
          return;
        }
        super.handleMessage(msg);
      }
    };
    return mWorkerThreadHandler;
  }

  /**
   * This method begins an asynchronous bulk insert. When the bulk insert operation is
   * done {@link #onBulkInsertComplete} is called.
   *
   * @param token A token passed into {@link #onBulkInsertComplete} to identify
   *  the insert operation.
   * @param cookie An object that gets passed into {@link #onBulkInsertComplete}
   * @param uri the Uri passed to the insert operation.
   * @param contentValues an array of ContentValues parameter passed to the bulk insert operation.
   */
  public final void startBulkInsert(
      int token, Object cookie, Uri uri, ContentValues[] contentValues) {
    // Use the token as what so cancelOperations works properly
    Message msg = mWorkerThreadHandler.obtainMessage(token);
    msg.arg1 = EVENT_ARG_BULK_INSERT;

    BulkWorkerArgs args = new BulkWorkerArgs();
    args.handler = this;
    args.uri = uri;
    args.cookie = cookie;
    args.values = contentValues;
    msg.obj = args;

    mWorkerThreadHandler.sendMessage(msg);
  }

  /**
   * Inserts the given worksheet into the database.
   * @param sheet
   * @return
   */
  public int insertWorksheet(Worksheet sheet) {
    int token = random.nextInt();
    startInsert(token, sheet, MakerContentProviderContract.Worksheet.CONTENT_URI,
        getContentValuesForWorksheet(sheet));
    return token;
  }

  @Override
  public void handleMessage(Message msg) {
    if (msg.obj instanceof BulkWorkerArgs) {
      BulkWorkerArgs args = (BulkWorkerArgs) msg.obj;

      int token = msg.what;
      int event = msg.arg1;
      if (event == EVENT_ARG_BULK_INSERT) {
        onBulkInsertComplete(token, args.cookie, (int) args.result);
      }
    } else {
      super.handleMessage(msg);
    }
  }

  protected void onBulkInsertComplete(int token, Object cookie, int numRowsInserted) {
    bus.post(new Events.InsertOperationCompleteEvent(token, null));
  }

  @Override
  protected void onInsertComplete(int token, Object cookie, Uri uri) {
    if (cookie instanceof Worksheet) {
      if (uri == null) {
        return;
      }
      Uri questionUri =
          uri.buildUpon().appendPath(MakerContentProviderContract.PATH_QUESTION).build();

      ArrayList<ContentValues> ops =
          new ArrayList<ContentValues>();
      Worksheet sheet = (Worksheet) cookie;
      for (Question question : sheet.getQuestionList()) {
        ops.add(getContentValuesForQuestion(question));
      }
      startBulkInsert(token, cookie, questionUri, ops.toArray(new ContentValues[ops.size()]));
    } else {
      bus.post(new Events.InsertOperationCompleteEvent(token, uri));
    }
  }

  @Override
  protected void onUpdateComplete(int token, Object cookie, int result) {
    bus.post(new Events.AsyncOperationCompleteEvent(token, result));
  }

  @Override
  protected void onDeleteComplete(int token, Object cookie, int result) {
    bus.post(new Events.AsyncOperationCompleteEvent(token, result));
  }

  ContentValues getContentValuesForQuestion(Question question) {
    ContentValues values = new ContentValues();
    values.put(MakerContentProviderContract.Question.COL_SHORT_DESC,
        question.getShortDescription());
    values.put(MakerContentProviderContract.Question.COL_LONG_DESC,
        question.getLongDescription());
    values.put(MakerContentProviderContract.Question.COL_ANSWER_TYPE,
        question.getAnswerType().toString());
    values.put(MakerContentProviderContract.Question.COL_DATE_CREATED,
        question.getDateCreated().toString());
    values.put(MakerContentProviderContract.Question.COL_MULTI_CHOICE_OPTIONS,
        TextUtils.join("::", question.getMultipleChoiceOptions()));
    values.put(MakerContentProviderContract.Question.COL_SHORT_ANSWER, question.getShortAnswer());
    values.put(MakerContentProviderContract.Question.COL_LONG_ANSWER, question.getLongAnswer());
    values.put(MakerContentProviderContract.Question.COL_CATEGORY, question.getCategory());
    values.put(MakerContentProviderContract.Question.COL_TYPE, question.getType().toString());
    return values;
  }

  ContentValues getContentValuesForWorksheet(Worksheet sheet) {
    ContentValues values = new ContentValues();
    values.put(MakerContentProviderContract.Worksheet.COL_WORKSHEET_NAME,
        sheet.getName());
    values.put(MakerContentProviderContract.Worksheet.COL_WORKSHEET_DESCRIPTION,
        sheet.getDescription());
    values.put(MakerContentProviderContract.Worksheet.COL_DATE_CREATED,
        sheet.getDateCreated().toString());
    return values;
  }
}
