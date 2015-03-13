package in.shingole.data.model;

import java.util.Date;
import java.util.List;

/**
 * A class for representing a problem.
 */
public class Question {
  private String id;
  private String category;
  private ProblemType type;
  private String shortDescription;
  private String longDescription;
  private Date dateCreated;
  private Date dateSolved;
  private DifficultyLevel difficultyLevel;
  private int numAttempts;
  private int maxAttempts;
  private String shortAnswer;
  private String longAnswer;
  private AnswerType answerType;
  private String hint;
  private List<String> multipleChoiceOptions;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
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
}
