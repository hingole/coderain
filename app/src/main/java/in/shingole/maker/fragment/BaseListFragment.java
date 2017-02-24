package in.shingole.maker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.shingole.maker.activity.BaseActivity;

public abstract class BaseListFragment extends ListFragment {

  @Inject
  Bus bus;

  public BaseListFragment() {
    super();
  }

  private BaseFragment.ViewState viewState;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    ((BaseActivity) getActivity()).inject(this);
  }

  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
  }

  public void setViewState(BaseFragment.ViewState viewState) {
    // Derived classes should override this.
    // Appropriate view should be rendered based on the state.
    this.viewState = viewState;
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

  public BaseFragment.ViewState getViewState() {
    return viewState;
  }
}
