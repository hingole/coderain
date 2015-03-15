package in.shingole.data;

import java.util.List;

import javax.inject.Inject;

import in.shingole.data.model.TestData;
import in.shingole.data.model.Worksheet;

/**
 * Provides worksheet service
 */
public class WorksheetService {

  @Inject
  public WorksheetService() {}

  public List<Worksheet> getWorksheetsForUser() {
    return TestData.sampleWorksheet();
  }

  public Worksheet getWorksheet(String worksheetId) {
    List<Worksheet> worksheets = TestData.sampleWorksheet();
    for (Worksheet sheet : worksheets) {
      if (sheet.getId().equals(worksheetId)) {
        return sheet;
      }
    }
    return null;
  }

}
