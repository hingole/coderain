package in.shingole.maker.events;

import android.net.Uri;

import in.shingole.maker.data.model.User;

/**
* Events used in the Maker apps.
*/
public class Events {

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


  // Event fired by SplashFragment when the button is tapped.
  public static class LoginToWorksheetTappedEvent {}

  // Event fired by DashboardFragment when user taps on worksheet name.
  public static class WorksheetIconTappedEvent {

    public WorksheetIconTappedEvent(String worksheetId) {

    }

  }
}
