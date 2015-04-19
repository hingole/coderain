package in.shingole.maker.data.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import in.shingole.maker.data.model.AnswerType;
import in.shingole.maker.data.model.ProblemType;
import in.shingole.maker.data.model.Question;
import in.shingole.maker.data.model.Worksheet;

/**
 * Created by shingole on 19/4/15.
 */
public class WorksheetUtil {

  /**
   * Creates a new worksheet and adds random math counting problems to it.
   * @param countUpto The maximum number of objects to count in any given problem
   * @param numProblems The number of problems to add
   */
  public Worksheet createDraftMathCountingWorksheet(int countUpto, int numProblems) {
    Worksheet sheet = new Worksheet();
    assert(countUpto > 2);
    assert(numProblems > 2);
    List<Integer> allSolutions = new ArrayList<>();

    // We want counting problems to start from 2 and onwards
    List<Integer> solutions = new ArrayList();
    for (int i = 2; i <= countUpto; i++) {
      solutions.add(i);
    }

    while(allSolutions.size() < numProblems) {
      Collections.shuffle(solutions);
      if (allSolutions.size() == 0) {
        allSolutions.addAll(solutions);
      } else if (allSolutions.get(allSolutions.size() - 1) != solutions.get(0)) {
        // Ensure that no two consecutive solutions are same
        allSolutions.addAll(solutions);
      }
    }

    List<Integer> worksheetSolutions = allSolutions.subList(0, numProblems);

    List<Question> questions = new ArrayList<>(numProblems);
    for (int i = 0; i < numProblems; i++) {
      Question question = new Question();
      question.setType(ProblemType.COUNTING_OBJECTS);
      question.setNumAttempts(3);
      question.setAnswerType(AnswerType.MULTIPLE_CHOICE);
      question.setShortAnswer(worksheetSolutions.get(i).toString());
      List<String> multipleChoices = new ArrayList<>(3);
      multipleChoices.add(worksheetSolutions.get(i).toString()); // Right answer
      if (i == 0) {
        multipleChoices.add(worksheetSolutions.get(i + 1).toString());
        multipleChoices.add(worksheetSolutions.get(i + 2).toString());
      } else if (i == numProblems - 1) {
        multipleChoices.add(worksheetSolutions.get(i - 1).toString());
        multipleChoices.add(worksheetSolutions.get(i - 2).toString());
      } else {
        multipleChoices.add(worksheetSolutions.get(i - 1).toString());
        multipleChoices.add(worksheetSolutions.get(i + 1).toString());
      }

      question.setMultipleChoiceOptions(multipleChoices);
      questions.add(question);
    }
    sheet.setQuestionList(questions);
    sheet.setDateCreated(new Date());
    return sheet;
  }
}
