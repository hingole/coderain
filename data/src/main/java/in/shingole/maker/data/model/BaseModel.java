package in.shingole.maker.data.model;

import android.os.Parcel;

import java.util.Date;

/**
 * Base class for all model objects.
 */
public class BaseModel {
  private String id;
  private Date dateCreated = new Date();
  private Date lastUpdated;
  private boolean isMarkedForDeletion;

  public BaseModel() {
  }

  protected BaseModel(Parcel in) {
    id = in.readString();
    long tmpDateCreated = in.readLong();
    if (tmpDateCreated >= 0) {
      dateCreated = new Date(tmpDateCreated);
    }
    tmpDateCreated = in.readLong();
    if (tmpDateCreated >= 0) {
      lastUpdated = new Date(tmpDateCreated);
    }
    byte markedForDeletion = in.readByte();
    isMarkedForDeletion = (markedForDeletion == (byte)1);
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Date getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public boolean isMarkedForDeletion() {
    return isMarkedForDeletion;
  }

  public void setMarkedForDeletion(boolean isMarkedForDeletion) {
    this.isMarkedForDeletion = isMarkedForDeletion;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeLong(dateCreated != null ? dateCreated.getTime() : -1L);
    dest.writeLong(lastUpdated != null ? lastUpdated.getTime() : -1L);
    dest.writeByte((byte)(isMarkedForDeletion() ? 1 : 0));
  }
}
