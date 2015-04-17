package in.shingole.maker.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import in.shingole.R;
import in.shingole.maker.activity.BaseActivity;

/**
 * Base fragment which performs injection using the activity-scoped object graph
 */
public abstract class BaseFragment extends Fragment {
  int fragmentViewId;

  public enum ViewState {
    LOADING,
    RENDERED,
    HIDDEN,
  }

  BaseFragment(int fragmentViewId) {
    this.fragmentViewId = fragmentViewId;
  }

  private ViewState viewState;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ((BaseActivity) getActivity()).inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    // Inflate the layout for this fragment
    return inflater.inflate(fragmentViewId, container, false);
  }

  public void onViewCreated(View view, Bundle savedInstanceState) {
    ButterKnife.inject(this, view);
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