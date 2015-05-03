package in.shingole.maker.data.query;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import in.shingole.maker.common.Annotations;
import in.shingole.maker.data.model.AnswerType;
import in.shingole.maker.data.model.Question;
import in.shingole.maker.data.model.Worksheet;
import in.shingole.maker.data.provider.MakerContentProviderContract;
import in.shingole.maker.data.sqlite.Tables;

/**
 * Created by shingole on 3/5/15.
 */
public class WorksheetQuestionListQuery {

  private final Context context;

  public static class QuestionCursorWrapper extends CursorWrapper {
    public QuestionCursorWrapper(Cursor cursor) {
      super(cursor);
    }

    public Question getQuestion() {
      String id = getString(getColumnIndex(MakerContentProviderContract.Question.COL_ID));
      Question question = new Question();
      question.setId(id);
      question.setShortDescription(getString(
          getColumnIndex(MakerContentProviderContract.Question.COL_SHORT_DESC)));
      question.setLongDescription(getString(
          getColumnIndex(MakerContentProviderContract.Question.COL_LONG_DESC)));
      question.setAnswerType(AnswerType.fromString(getString(
          getColumnIndex(MakerContentProviderContract.Question.COL_ANSWER_TYPE))));

      String multiChoice = getString(
          getColumnIndex(MakerContentProviderContract.Question.COL_MULTI_CHOICE_OPTIONS));
      if (multiChoice != null) {
        List<String> multiChoiceOptions = ImmutableList.of(TextUtils.split(multiChoice, "::"));
        question.setMultipleChoiceOptions(multiChoiceOptions);
      }
      question.setShortAnswer(
          getString(getColumnIndex(MakerContentProviderContract.Question.COL_SHORT_ANSWER)));
      question.setLongAnswer(
          getString(getColumnIndex(MakerContentProviderContract.Question.COL_LONG_ANSWER)));
      return question;
    }
  }

  @Inject
  public WorksheetQuestionListQuery(@Annotations.ForActivity Context context) {
    this.context = context;
  }

  public Loader<Cursor> createQuestionListLoader(String worksheetId) {
    return new CursorLoader(context,
        MakerContentProviderContract.Worksheet.getContentUriForQuestions(worksheetId),
        PROJECTION,
        null, null,
        MakerContentProviderContract.Worksheet.COL_DATE_CREATED + " DESC");
  }

  public static final String[] PROJECTION = {
      Tables.QuestionTable.TABLE_NAME + "." + MakerContentProviderContract.Question.COL_ID,
      Tables.QuestionTable.TABLE_NAME + "." + MakerContentProviderContract.Question.COL_SHORT_DESC,
      Tables.QuestionTable.TABLE_NAME + "." + MakerContentProviderContract.Question.COL_LONG_DESC,
      Tables.QuestionTable.TABLE_NAME + "." + MakerContentProviderContract.Question.COL_ANSWER_TYPE,
      Tables.QuestionTable.TABLE_NAME + "."
          + MakerContentProviderContract.Question.COL_MULTI_CHOICE_OPTIONS,
      Tables.QuestionTable.TABLE_NAME + "."
          + MakerContentProviderContract.Question.COL_SHORT_ANSWER,
      Tables.QuestionTable.TABLE_NAME + "."
          + MakerContentProviderContract.Question.COL_LONG_ANSWER,
  };
}
