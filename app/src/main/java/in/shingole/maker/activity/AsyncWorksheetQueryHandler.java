package in.shingole.maker.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.squareup.otto.Bus;

import java.util.Random;

import javax.inject.Inject;

import in.shingole.maker.data.provider.MakerContentProvider;
import in.shingole.maker.data.provider.MakerContentProviderContract;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.events.Events;

/**
 * TODO: Handle errors due to DB operation.
 */
public class AsyncWorksheetQueryHandler extends AsyncQueryHandler {

  private final Random random;
  private final Bus bus;

  @Inject
  public AsyncWorksheetQueryHandler(ContentResolver cr, Bus bus) {
    super(cr);
    this.bus = bus;
    random = new Random();
  }

  /**
   * Inserts the given worksheet into the database.
   * @param sheet
   * @return
   */
  public int insertWorksheet(Worksheet sheet) {
    int token = random.nextInt();
    startInsert(token, null, MakerContentProviderContract.Worksheet.CONTENT_URI,
        getContentValuesForWorksheet(sheet));
    return token;
  }

  @Override
  protected void onInsertComplete(int token, Object cookie, Uri uri) {
    bus.post(new Events.InsertOperationCompleteEvent(token, uri));
  }

  @Override
  protected void onUpdateComplete(int token, Object cookie, int result) {
    bus.post(new Events.AsyncOperationCompleteEvent(token, result));
  }

  @Override
  protected void onDeleteComplete(int token, Object cookie, int result) {
    bus.post(new Events.AsyncOperationCompleteEvent(token, result));
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
