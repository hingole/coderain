package in.shingole.data.adapters;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import in.shingole.data.model.TestData;
import in.shingole.data.model.Worksheet;

/**
 * Loads worksheets
 */
public class WorksheetLoader extends AsyncTaskLoader<List<Worksheet>> {

  private List<Worksheet> sheets;

  public WorksheetLoader(Context context) {
    super(context);
  }

  @Override public void deliverResult(List<Worksheet> worksheets) {
    if (isReset()) {
      // An async query came in while the loader is stopped.  We
      // don't need the result.
      if (worksheets != null) {
        onReleaseResources(worksheets);
      }
    }
    List<Worksheet> oldSheets = sheets;
    sheets = worksheets;

    if (isStarted()) {
      // If the Loader is currently started, we can immediately
      // deliver its results.
      super.deliverResult(worksheets);
    }

    // At this point we can release the resources associated with
    // 'oldApps' if needed; now that the new result is delivered we
    // know that it is no longer in use.
    if (oldSheets != null) {
      onReleaseResources(oldSheets);
    }
  }

  /**
   * Handles a request to completely reset the Loader.
   */
  @Override protected void onReset() {
    super.onReset();

    // Ensure the loader is stopped
    onStopLoading();

    // At this point we can release the resources associated with 'apps'
    // if needed.
    if (sheets != null) {
      onReleaseResources(sheets);
      sheets = null;
    }
  }

  /**
   * Handles a request to start the Loader.
   */
  @Override protected void onStartLoading() {
    if (sheets != null) {
      // If we currently have a result available, deliver it
      // immediately.
      deliverResult(sheets);
    }
  }

  /**
   * Handles a request to stop the Loader.
   */
  @Override protected void onStopLoading() {
    // Attempt to cancel the current load task if possible.
    cancelLoad();
  }

  /**
   * Handles a request to cancel a load.
   */
  @Override public void onCanceled(List<Worksheet> sheets) {
    super.onCanceled(sheets);

    // At this point we can release the resources associated with 'apps'
    // if needed.
    onReleaseResources(sheets);
  }

  @Override
  public List<Worksheet> loadInBackground() {
    // Unused but could be used to pass auth data
    //final Context context = getContext();
    try {
      // Simulating network delay.
      Thread.currentThread().wait(2000);
    } catch (InterruptedException ex) {

    }
    return TestData.sampleWorksheet();
  }

  /**
   * Helper function to take care of releasing resources associated
   * with an actively loaded data set.
   */
  protected void onReleaseResources(List<Worksheet> sheets) {
    // For a simple List<> there is nothing to do.  For something
    // like a Cursor, we would close it here.
  }
}
