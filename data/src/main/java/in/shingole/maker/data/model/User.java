package in.shingole.maker.data.model;

/**
 * User object
 */
public class User extends BaseModel {
  String loginProvider;
  String userId;
  String userName;

  public String getLoginProvider() {
    return loginProvider;
  }

  public void setLoginProvider(String loginProvider) {
    this.loginProvider = loginProvider;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
