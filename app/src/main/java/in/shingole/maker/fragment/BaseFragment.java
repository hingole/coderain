package in.shingole.maker.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import in.shingole.maker.activity.BaseActivity;

/**
 * Base fragment which performs injection using the activity-scoped object graph
 */
public abstract class BaseFragment extends Fragment {
  public enum ViewState {
    LOADING,
    RENDERED,
    HIDDEN,
  }

  private ViewState viewState;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Assume that it lives within a BaseActivity host
    ((BaseActivity) getActivity()).inject(this);
  }

  public void setViewState(ViewState viewState) {
    // Derived classes should override this.
    // Appropriate view should be rendered based on the state.
    this.viewState = viewState;
  }

  public ViewState getViewState() {
    return viewState;
  }
}