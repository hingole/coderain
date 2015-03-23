package in.shingole.data.model;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Test Data
 */
public class TestData {

  public static List<Worksheet> sampleWorksheet() {
    ArrayList<Worksheet> sheets = new ArrayList<Worksheet>();
    for (int i = 0; i < 40; i++) {
      Worksheet worksheet1 = new Worksheet();
      worksheet1.setId("W" + i);
      worksheet1.setCategory("LKG MATHS");
      worksheet1.setDateCreated(new Date());
      worksheet1.setName("Worksheet " + i);
      worksheet1.setDescription("LKG Worksheet " + i);
      worksheet1.setQuestionList(sampleQuestions());
      sheets.add(worksheet1);
    }
    return ImmutableList.copyOf(sheets);
  }

  public static List<Question> sampleQuestions() {

    Question question1 = new Question();
    question1.setId("M1");
    question1.setAnswerType(AnswerType.BLANK);
    question1.setCategory("MATHS");
    question1.setDateCreated(new Date());
    question1.setType(ProblemType.COUNTING_OBJECTS);
    question1.setDifficultyLevel(DifficultyLevel.BEGINNER);
    question1.setHint("Just count the number of items");
    question1.setShortDescription("Count the number of objects");
    question1.setMaxAttempts(4);
    question1.setShortAnswer("3");

    Question question2 = new Question();
    question2.setId("M2");
    question2.setAnswerType(AnswerType.BLANK);
    question2.setCategory("MATHS");
    question2.setDateCreated(new Date());
    question2.setType(ProblemType.COUNTING_OBJECTS);
    question2.setDifficultyLevel(DifficultyLevel.BEGINNER);
    question2.setHint("Just count the number of items");
    question2.setShortDescription("Count the number of objects");
    question2.setMaxAttempts(4);
    question2.setShortAnswer("2");

    return ImmutableList.of(question1, question2);
  }
}
