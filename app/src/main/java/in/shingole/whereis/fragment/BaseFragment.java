package in.shingole.whereis.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.shingole.R;
import in.shingole.whereis.activity.BaseActivity;

/**
 * Base fragment which performs injection using the activity-scoped object graph
 */
public abstract class BaseFragment extends Fragment {

  @Inject
  Bus bus;

  int fragmentViewId;

  public enum ViewState {
    LOADING,
    RENDERED,
    HIDDEN,
  }

  public BaseFragment(int fragmentViewId) {
    this.fragmentViewId = fragmentViewId;
  }

  private ViewState viewState;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
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
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
  }

  @Override
  public void onPause() {
    super.onPause();
    bus.unregister(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    bus.register(this);
  }

  public void setViewState(ViewState viewState) {
    // Derived classes should override this.
    // Appropriate view should be rendered based on the state.
    if (getView() == null) {
      return;
    }
    ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);
    this.viewState = viewState;
    if (this.viewState == ViewState.LOADING) {
      if (progressBar == null && getView() instanceof ViewGroup) {
        ((ViewGroup) getView()).addView(createNewProgressBar());
      }
    } else {
      if (progressBar != null) {
        progressBar.setVisibility(View.GONE);
      }
    }
  }

  protected ViewGroup.LayoutParams getLayoutParamsForProgressBar() {
    return new FrameLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
  }

  protected ProgressBar createNewProgressBar() {
    ProgressBar progressBar =
        new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
    progressBar.setId(R.id.progressBar);
    progressBar.setLayoutParams(getLayoutParamsForProgressBar());
    progressBar.setVisibility(View.VISIBLE);
    return progressBar;
  }

  public ViewState getViewState() {
    return viewState;
  }
}