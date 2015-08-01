package in.shingole.maker.events;

import android.net.Uri;

import in.shingole.maker.data.model.Question;
import in.shingole.maker.data.model.User;
import in.shingole.maker.data.model.Worksheet;

/**
* Events used in the Maker apps.
*/
public class Events {

  /**
   * Event fired when the user selects the correct answer for the question.
   */
  public static class CorrectAnswerEvent {
    private final Question question;

    public CorrectAnswerEvent(Question question) {
      this.question = question;
    }

    public Question getQuestion() {
      return question;
    }
  }

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

  public static class UserLoggedInEvent {
    private final User user;

    public UserLoggedInEvent(User user) {
      this.user = user;
    }

    public User getUser() {
      return user;
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
