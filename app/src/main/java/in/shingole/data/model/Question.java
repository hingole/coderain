package in.shingole.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A class for representing a problem.
 */
public class Question extends BaseModel implements Parcelable {
  private String category;
  private ProblemType type;
  private String shortDescription;
  private String longDescription;
  private Date dateSolved;
  private DifficultyLevel difficultyLevel;
  private int numAttempts;
  private int maxAttempts;
  private String shortAnswer;
  private String longAnswer;
  private AnswerType answerType;
  private String hint;
  private List<String> multipleChoiceOptions;

  public Question() {
    super();
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public ProblemType getType() {
    return type;
  }

  public void setType(ProblemType type) {
    this.type = type;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getLongDescription() {
    return longDescription;
  }

  public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }

  public Date getDateSolved() {
    return dateSolved;
  }

  public void setDateSolved(Date dateSolved) {
    this.dateSolved = dateSolved;
  }

  public DifficultyLevel getDifficultyLevel() {
    return difficultyLevel;
  }

  public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
    this.difficultyLevel = difficultyLevel;
  }

  public int getNumAttempts() {
    return numAttempts;
  }

  public void setNumAttempts(int numAttempts) {
    this.numAttempts = numAttempts;
  }

  public int getMaxAttempts() {
    return maxAttempts;
  }

  public void setMaxAttempts(int maxAttempts) {
    this.maxAttempts = maxAttempts;
  }

  public String getShortAnswer() {
    return shortAnswer;
  }

  public void setShortAnswer(String shortAnswer) {
    this.shortAnswer = shortAnswer;
  }

  public String getLongAnswer() {
    return longAnswer;
  }

  public void setLongAnswer(String longAnswer) {
    this.longAnswer = longAnswer;
  }

  public AnswerType getAnswerType() {
    return answerType;
  }

  public void setAnswerType(AnswerType answerType) {
    this.answerType = answerType;
  }

  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
  }

  public List<String> getMultipleChoiceOptions() {
    return multipleChoiceOptions;
  }

  public void setMultipleChoiceOptions(List<String> multipleChoiceOptions) {
    this.multipleChoiceOptions = multipleChoiceOptions;
  }

  protected Question(Parcel in) {
    super(in);
    category = in.readString();
    type = (ProblemType) in.readValue(ProblemType.class.getClassLoader());
    shortDescription = in.readString();
    longDescription = in.readString();
    long tmpDateSolved = in.readLong();
    dateSolved = tmpDateSolved != -1 ? new Date(tmpDateSolved) : null;
    difficultyLevel = (DifficultyLevel) in.readValue(DifficultyLevel.class.getClassLoader());
    numAttempts = in.readInt();
    maxAttempts = in.readInt();
    shortAnswer = in.readString();
    longAnswer = in.readString();
    answerType = (AnswerType) in.readValue(AnswerType.class.getClassLoader());
    hint = in.readString();
    if (in.readByte() == 0x01) {
      multipleChoiceOptions = new ArrayList<>();
      in.readList(multipleChoiceOptions, String.class.getClassLoader());
    } else {
      multipleChoiceOptions = null;
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(category);
    dest.writeValue(type);
    dest.writeString(shortDescription);
    dest.writeString(longDescription);
    dest.writeLong(dateSolved != null ? dateSolved.getTime() : -1L);
    dest.writeValue(difficultyLevel);
    dest.writeInt(numAttempts);
    dest.writeInt(maxAttempts);
    dest.writeString(shortAnswer);
    dest.writeString(longAnswer);
    dest.writeValue(answerType);
    dest.writeString(hint);
    if (multipleChoiceOptions == null) {
      dest.writeByte((byte) (0x00));
    } else {
      dest.writeByte((byte) (0x01));
      dest.writeList(multipleChoiceOptions);
    }
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
    @Override
    public Question createFromParcel(Parcel in) {
      return new Question(in);
    }

    @Override
    public Question[] newArray(int size) {
      return new Question[size];
    }
  };
}
