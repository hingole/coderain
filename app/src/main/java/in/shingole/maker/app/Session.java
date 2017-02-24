package in.shingole.maker.app;

import javax.inject.Inject;
import javax.inject.Singleton;

import in.shingole.maker.data.model.User;

@Singleton
public class Session {
  private boolean isLoggedIn;
  private User currentUser;

  @Inject
  public Session() {}

  public synchronized void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public boolean isLoggedIn() {
    return this.isLoggedIn;
  }

  public synchronized void logout() {
    this.currentUser = null;
    isLoggedIn = false;
  }

  public void login(User user) {
    this.currentUser = user;
    if (user != null && user.getId() != null) {
      isLoggedIn = true;
    }
  }
}
