package in.shingole.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Worksheet class
 */
public class Worksheet extends BaseModel implements Parcelable {
  private String id;
  private String name;
  private String description;
  private String category;
  List<Question> questionList;

  public Worksheet() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public List<Question> getQuestionList() {
    return questionList;
  }

  public void setQuestionList(List<Question> questionList) {
    this.questionList = questionList;
  }

  protected Worksheet(Parcel in) {
    id = in.readString();
    name = in.readString();
    description = in.readString();
    long tmpDateCreated = in.readLong();
    category = in.readString();
    if (in.readByte() == 0x01) {
      questionList = new ArrayList<Question>();
      in.readList(questionList, Question.class.getClassLoader());
    } else {
      questionList = null;
    }
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(id);
    dest.writeString(name);
    dest.writeString(description);
    dest.writeString(category);
    if (questionList == null) {
      dest.writeByte((byte) (0x00));
    } else {
      dest.writeByte((byte) (0x01));
      dest.writeList(questionList);
    }
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Worksheet> CREATOR = new Parcelable.Creator<Worksheet>() {
    @Override
    public Worksheet createFromParcel(Parcel in) {
      return new Worksheet(in);
    }

    @Override
    public Worksheet[] newArray(int size) {
      return new Worksheet[size];
    }
  };
}
