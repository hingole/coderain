package in.shingole.maker.events;

import android.net.Uri;

import in.shingole.maker.data.model.Worksheet;

/**
* Created by shingole on 1/5/15.
*/
public class Events {

  /**
   * Event fired whenever a data modification operation successfully completes
   */
  public static class DataOperationCompletedEvent {}

  /**
   * Event fired whenever async db operation is completed.
   */
  public static class AsyncOperationCompleteEvent {
    private final int token;
    private final int result;
    public AsyncOperationCompleteEvent(int token, int result) {
      this.token = token;
      this.result = result;
    }

    public int getResult() {
      return result;
    }

    public int getToken() {
      return token;
    }
  }

  public static class InsertOperationCompleteEvent extends AsyncOperationCompleteEvent {
    private final Uri resourceUri;

    public InsertOperationCompleteEvent(int token, Uri resourceUri) {
      super(token, resourceUri != null ? 0 : -1);
      this.resourceUri = resourceUri;
    }

    public Uri getResourceUri() {
      return resourceUri;
    }
  }

  /**
   * Event fired when the user taps on either 'Next' or 'Create' button in the sheet designer
   * activity.
   */
  public static class CreateWorksheetTappedEvent {
    private final Worksheet draftWorksheet;

    public CreateWorksheetTappedEvent(Worksheet draftWorksheet) {
      this.draftWorksheet = draftWorksheet;
    }

    public Worksheet getDraftWorksheet() {
      return draftWorksheet;
    }
  }

  // Event fired by SplashFragment when the button is tapped.
  public static class LoginToWorksheetTappedEvent {};

  // Event fired by DashboardFragment when user taps on worksheet name.
  public static class WorksheetIconTappedEvent {
    private final String worksheetId;

    public WorksheetIconTappedEvent(String worksheetId) {
      this.worksheetId = worksheetId;
    }

    public String getWorksheetId() {
      return worksheetId;
    }
  }
}
