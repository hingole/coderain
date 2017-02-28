package in.shingole.whereis.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class WhereIsFirebaseInstanceIdService extends FirebaseInstanceIdService {

  private static final String TAG = WhereIsFirebaseInstanceIdService.class.getName();

  public WhereIsFirebaseInstanceIdService() {
  }

  @Override
  public void onTokenRefresh() {
    // Get updated InstanceID token.
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.d(TAG, "Refreshed token: " + refreshedToken);

    // If you want to send messages to this application instance or
    // manage this apps subscriptions on the server side, send the
    // Instance ID token to your app server.
    //sendRegistrationToServer(refreshedToken);
  }
}
