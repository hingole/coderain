package in.shingole.maker.data.query;

import android.content.Context;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import in.shingole.maker.common.Annotations;
import in.shingole.maker.common.ActivityScopeModule;

/**
 * Created by shingole on 4/8/15.
 */
@Module(
    library = true,
    includes = ActivityScopeModule.class,
    complete = true
)
public class WorksheetQueryModule {

  @Inject
  public WorksheetQueryModule() {
  }

  @Provides
  WorksheetDetailQuery provideWorksheetDetailQuery(@Annotations.ForActivity Context context) {
    return new WorksheetDetailQuery(context);
  }

  @Provides
  WorksheetListQuery provideWorksheetListQuery(@Annotations.ForActivity Context context) {
    return new WorksheetListQuery(context);
  }

}
