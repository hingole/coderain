package in.shingole.maker.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import in.shingole.R;
import in.shingole.maker.activity.AsyncUserQueryHandler;
import in.shingole.maker.common.Utils;
import in.shingole.maker.data.model.User;
import in.shingole.maker.data.query.UserQuery;
import in.shingole.maker.events.Events;

import static android.app.Activity.RESULT_OK;


/**
 * Fragment for logging users in.
 */
public class LoginFragment extends BaseFragment implements
    LoaderManager.LoaderCallbacks<Cursor>,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
  public static final int USER_LOADER = 101;

  private static final String PARAM_USER_ID = "userId";
  private static final String PARAM_LOGIN_PROVIDER = "loginProvider";
  private static final String GOOGLE_LOGIN_PROVIDER = "google";

  @Inject
  UserQuery userQuery;

  @Inject
  AsyncUserQueryHandler userQueryHandler;

  private static final int STATE_DEFAULT = 0;
  private static final int STATE_SIGN_IN = 1;
  private static final int STATE_IN_PROGRESS = 2;

  private static final int RC_SIGN_IN = 0;

  public static final String FRAGMENT_TAG = "LOGIN_FRAGMENT";
  private static final String TAG = Utils.getLogTag(LoginFragment.class);

  private boolean signInClicked;
  private static final String SAVED_PROGRESS = "sign_in_progress";

  // Client ID for a web server that will receive the auth code and exchange it for a
  // refresh token if offline access is requested.
  private static final String WEB_CLIENT_ID = "WEB_CLIENT_ID";

  private GoogleApiClient googleApiClient;

  private Person currentLoggedInUser;
  private int userInsertToken;

  @InjectView(R.id.login_status)
  TextView mStatus;
  // We use mSignInProgress to track whether user has clicked sign in.
  // mSignInProgress can be one of three values:
  //
  //       STATE_DEFAULT: The default state of the application before the user
  //                      has clicked 'sign in', or after they have clicked
  //                      'sign out'.  In this state we will not attempt to
  //                      resolve sign in errors and so will display our
  //                      Activity in a signed out state.
  //       STATE_SIGN_IN: This state indicates that the user has clicked 'sign
  //                      in', so resolve successive errors preventing sign in
  //                      until the user has successfully authorized an account
  //                      for our app.
  //   STATE_IN_PROGRESS: This state indicates that we have started an intent to
  //                      resolve an error, and so we should not start further
  //                      intents until the current intent completes.
  private int mSignInProgress;

  // Used to store the PendingIntent most recently returned by Google Play
  // services until the user clicks 'sign in'.
  private PendingIntent mSignInIntent;

  // Used to store the error code most recently returned by Google Play services
  // until the user clicks 'sign in'.
  private int mSignInError;

  public LoginFragment() {
    super(R.layout.fragment_login);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    googleApiClient = buildGoogleApiClient();
  }

  @OnClick(R.id.sign_in_button)
  void handleLoginButtonClicked(View v) {
    if (!googleApiClient.isConnecting()) {
      // We only process button clicks when GoogleApiClient is not transitioning
      // between connected and not connected.
      switch (v.getId()) {
        case R.id.sign_in_button:
          mStatus.setText(R.string.status_signing_in);
          mSignInProgress = STATE_SIGN_IN;
          googleApiClient.connect();
          break;
//        case R.id.sign_out_button:
//          // We clear the default account on sign out so that Google Play
//          // services will not return an onConnected callback without user
//          // interaction.
//          if (mGoogleApiClient.isConnected()) {
//            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//            mGoogleApiClient.disconnect();
//          }
//          onSignedOut();
//          break;
//        case R.id.revoke_access_button:
//          // After we revoke permissions for the user with a GoogleApiClient
//          // instance, we must discard it and create a new one.
//          Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//          // Our sample has caches no user data from Google+, however we
//          // would normally register a callback on revokeAccessAndDisconnect
//          // to delete user data so that we comply with Google developer
//          // policies.
//          Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
//          mGoogleApiClient = buildGoogleApiClient();
//          mGoogleApiClient.connect();
//          break;
      }
    }
  }
  /* onConnected is called when our Activity successfully connects to Google
      * Play services.  onConnected indicates that an account was selected on the
      * device, that the selected account has granted any requested permissions to
      * our app and that we were able to establish a service connection to Google
      * Play services.
      */
  @Override
  public void onConnected(Bundle connectionHint) {
    // Reaching onConnected means we consider the user signed in.
    Log.i(TAG, "onConnected");

    // Retrieve some profile information to personalize our app for the user.
    Person currentUser = Plus.PeopleApi.getCurrentPerson(googleApiClient);

    if (currentUser != null) {
      currentLoggedInUser = currentUser;

      mStatus.setText(String.format(
          getResources().getString(R.string.signed_in_as),
          currentUser.getDisplayName()));

      Bundle bundle = new Bundle();
      bundle.putString(PARAM_USER_ID, currentUser.getId());
      bundle.putString(PARAM_LOGIN_PROVIDER, GOOGLE_LOGIN_PROVIDER);
      getActivity().getSupportLoaderManager().initLoader(USER_LOADER, bundle, this);
    }
    // Indicate that the sign in process is complete.
    mSignInProgress = STATE_DEFAULT;
  }

  /**
   * This method is invoked when a user logs in and we try to find that user by provider and id from
   * our database. If the user exists we fire a logged in user event. If not then we create a new
   * user.
   */
  private void loggedInUserModel(User user) {
    if (currentLoggedInUser == null) {
      return;
    }
    if (user != null && user.getUserId().equals(currentLoggedInUser.getId())) {
      bus.post(new Events.UserLoggedInEvent(user));
      return;
    }
    User newuser = new User();
    newuser.setUserId(currentLoggedInUser.getId());
    newuser.setLoginProvider(GOOGLE_LOGIN_PROVIDER);
    newuser.setUserName(currentLoggedInUser.getDisplayName());
    userInsertToken = userQueryHandler.insertUser(newuser);
  }

  /**
   * Called when a new user is inserted in our db on first login.
   */
  @Subscribe
  public void onCreateUser(Events.InsertOperationCompleteEvent event) {
    if (event.getToken() != userInsertToken) {
      return;
    }
    if (currentLoggedInUser == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString(PARAM_USER_ID, currentLoggedInUser.getId());
    bundle.putString(PARAM_LOGIN_PROVIDER, GOOGLE_LOGIN_PROVIDER);
    getActivity().getSupportLoaderManager().restartLoader(USER_LOADER, bundle, this);
  }

  /* onConnectionFailed is called when our Activity could not connect to Google
   * Play services.  onConnectionFailed indicates that the user needs to select
   * an account, grant permissions or resolve an error in order to sign in.
   */
  @Override
  public void onConnectionFailed(ConnectionResult result) {
    // Refer to the javadoc for ConnectionResult to see what error codes might
    // be returned in onConnectionFailed.
    Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
        + result.getErrorCode());

    if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
      // An API requested for GoogleApiClient is not available. The device's current
      // configuration might not be supported with the requested API or a required component
      // may not be installed, such as the Android Wear application. You may need to use a
      // second GoogleApiClient to manage the application's optional APIs.
      Log.w(TAG, "API Unavailable.");
    } else if (mSignInProgress != STATE_IN_PROGRESS) {
      // We do not have an intent in progress so we should store the latest
      // error resolution intent for use when the sign in button is clicked.
      mSignInIntent = result.getResolution();
      mSignInError = result.getErrorCode();

      if (mSignInProgress == STATE_SIGN_IN) {
        // STATE_SIGN_IN indicates the user already clicked the sign in button
        // so we should continue processing errors until the user is signed in
        // or they click cancel.
        resolveSignInError();
      }
    }

    // In this sample we consider the user signed out whenever they do not have
    // a connection to Google Play services.
    onSignedOut();
  }

  private void onSignedOut() {
    // Update the UI to reflect that the user is signed out.
    mStatus.setText(R.string.status_signed_out);
  }

  @Override
  public void onConnectionSuspended(int cause) {
    // The connection to Google Play services was lost for some reason.
    // We call connect() to attempt to re-establish the connection or get a
    // ConnectionResult that we can attempt to resolve.
    googleApiClient.connect();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode,
                               Intent data) {
    switch (requestCode) {
      case RC_SIGN_IN:
        if (resultCode == RESULT_OK) {
          // If the error resolution was successful we should continue
          // processing errors.
          mSignInProgress = STATE_SIGN_IN;
        } else {
          // If the error resolution was not successful or the user canceled,
          // we should stop processing errors.
          mSignInProgress = STATE_DEFAULT;
        }

        if (!googleApiClient.isConnecting()) {
          // If Google Play services resolved the issue with a dialog then
          // onStart is not called so we need to re-attempt connection here.
          googleApiClient.connect();
        }
        break;
    }
  }

  /* Starts an appropriate intent or dialog for user interaction to resolve
   * the current error preventing the user from being signed in.  This could
   * be a dialog allowing the user to select an account, an activity allowing
   * the user to consent to the permissions being requested by your app, a
   * setting to enable device networking, etc.
   */
  private void resolveSignInError() {
    if (mSignInIntent != null) {
      // We have an intent which will allow our user to sign in or
      // resolve an error.  For example if the user needs to
      // select an account to sign in with, or if they need to consent
      // to the permissions your app is requesting.

      try {
        // Send the pending intent that we stored on the most recent
        // OnConnectionFailed callback.  This will allow the user to
        // resolve the error currently preventing our connection to
        // Google Play services.
        mSignInProgress = STATE_IN_PROGRESS;
        getActivity().startIntentSenderForResult(mSignInIntent.getIntentSender(),
            RC_SIGN_IN, null, 0, 0, 0);
      } catch (IntentSender.SendIntentException e) {
        Log.i(TAG, "Sign in intent could not be sent: "
            + e.getLocalizedMessage());
        // The intent was canceled before it was sent.  Attempt to connect to
        // get an updated ConnectionResult.
        mSignInProgress = STATE_SIGN_IN;
        googleApiClient.connect();
      }
    } else {
      // Google Play services wasn't able to provide an intent for some
      // error types, so we show the default Google Play services error
      // dialog which may still start an intent on our behalf if the
      // user can resolve the issue.
      createErrorDialog().show();
    }
  }
  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
    if (googleApiClient.isConnected()) {
      googleApiClient.disconnect();
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(SAVED_PROGRESS, mSignInProgress);
  }

  private Dialog createErrorDialog() {
    if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
      return GooglePlayServicesUtil.getErrorDialog(
          mSignInError,
          getActivity(),
          RC_SIGN_IN,
          new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
              Log.e(TAG, "Google Play services resolution cancelled");
              mSignInProgress = STATE_DEFAULT;
              mStatus.setText(R.string.status_signed_out);
            }
          });
    } else {
      return new AlertDialog.Builder(getActivity())
          .setMessage(R.string.play_services_error)
          .setPositiveButton(R.string.close,
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Log.e(TAG, "Google Play services error could not be "
                      + "resolved: " + mSignInError);
                  mSignInProgress = STATE_DEFAULT;
                  mStatus.setText(R.string.status_signed_out);
                }
              }).create();
    }
  }

  private GoogleApiClient buildGoogleApiClient() {
    // When we build the GoogleApiClient we specify where connected and
    // connection failed callbacks should be returned, which Google APIs our
    // app uses and which OAuth 2.0 scopes our app requests.
    GoogleApiClient.Builder builder = new GoogleApiClient.Builder(getActivity())
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API, Plus.PlusOptions.builder().build())
        .addScope(Plus.SCOPE_PLUS_LOGIN);
    return builder.build();
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    if (id != USER_LOADER) {
      return null;
    }
    return userQuery.createUserLoader(
        args.getString(PARAM_USER_ID), args.getString(PARAM_LOGIN_PROVIDER));
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    if (data != null && data.getCount() > 0) {
      UserQuery.UserCursorWrapper userCursor = new UserQuery.UserCursorWrapper(data);
      userCursor.moveToFirst();
      User user = userCursor.getUser();
      loggedInUserModel(user);
    } else {
      loggedInUserModel(null);
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

  }
}
