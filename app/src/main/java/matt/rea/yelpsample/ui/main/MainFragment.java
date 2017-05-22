package matt.rea.yelpsample.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import matt.rea.yelpsample.App;
import matt.rea.yelpsample.R;
import matt.rea.yelpsample.data.business.Business;
import matt.rea.yelpsample.databinding.FragmentMainBinding;
import matt.rea.yelpsample.network.SearchResponse;
import matt.rea.yelpsample.ui.BaseFragment;
import matt.rea.yelpsample.ui.search.SearchFragment;
import matt.rea.yelpsample.ui.settings.SettingsFragment;
import matt.rea.yelpsample.util.KeyboardUtil;

/**
 * Created by Matt on 5/19/17.
 */

public class MainFragment extends BaseFragment implements MainContract.View {

    MainContract.Presenter mPresenter;
    private BusinessAdapter mAdapter;
    private static final String LOG_TAG = "MainFragment";
    private static final int NUM_COLUMNS = 2;
    private String mSearchTerm;

    private List<Business> mBusinessList;

    FragmentMainBinding mBinding;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public static MainFragment newInstanceWithSearchTerm(String searchTerm) {
        MainFragment fragment = new MainFragment();
        fragment.mSearchTerm = searchTerm;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(App.get(getContext()).getStorage());
        if (!TextUtils.isEmpty(mSearchTerm)) {
            mPresenter.setSearchTerm(mSearchTerm);
        }
        mBusinessList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attach(this);
    }

    @Override
    public void onBusinessesReturned(SearchResponse response) {
        mBusinessList = response.businesses;
        hideKeyBoardIfOpen();
        mBinding.setIsEmpty(response.businesses == null || response.businesses.isEmpty());
        mBinding.setPresenter(mPresenter);
        initRecyclerView(response.businesses);
    }

    @Override
    public void navigateToSearch() {
        mFragmentNavigation.pushFragment(SearchFragment.newInstance());
    }

    @Override
    public void navigateToSettings() {
        mFragmentNavigation.pushFragment(SettingsFragment.newInstance());
    }

    @Override
    public void onMoreBusinessesReturned(SearchResponse body) {
        mBusinessList.addAll(body.businesses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideKeyBoardIfOpen() {
        KeyboardUtil.hideSoftKeyboard(mBinding.getRoot(), getActivity());
    }

    private void initRecyclerView(List<Business> data) {
        mAdapter = new BusinessAdapter(data, offset1 -> mPresenter.loadMore(offset1));
        mBinding.recyclerView.setLayoutManager( new GridLayoutManager(getContext(), NUM_COLUMNS));
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.executePendingBindings();
    }

    @Override
    public void onDestroyView() {
        mPresenter.detach();
        super.onDestroyView();
    }
}
