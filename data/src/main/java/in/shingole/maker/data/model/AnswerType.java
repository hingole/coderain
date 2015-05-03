package in.shingole.maker.data.model;

/**
 * The type of answer to render.
 */
public enum AnswerType {
  BLANK("BLANK"),
  MULTIPLE_CHOICE("MULTIPLE_CHOICE"),
  MATCH_CORRECT_ONE("MATCH_CORRECT_ONE");

  private String value;

  private AnswerType(String strValue) {
    this.value = strValue;
  }
  public static AnswerType fromString(String answerType) {
    if (MATCH_CORRECT_ONE.value.equals(answerType)) {
      return MATCH_CORRECT_ONE;
    } else if (MULTIPLE_CHOICE.value.equals(answerType)) {
      return MULTIPLE_CHOICE;
    } else {
      return BLANK;
    }
  }

  public String toString() {
    return this.value;
  }

}
