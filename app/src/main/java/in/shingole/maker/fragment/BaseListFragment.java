package in.shingole.maker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.shingole.maker.activity.BaseActivity;

/**
 * Created by shingole on 1/5/15.
 */
public abstract class BaseListFragment extends ListFragment {

  @Inject
  Bus bus;

  public BaseListFragment() {
    super();
  }

  private BaseFragment.ViewState viewState;

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
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
