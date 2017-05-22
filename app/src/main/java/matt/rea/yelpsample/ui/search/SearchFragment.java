package matt.rea.yelpsample.ui.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;

import matt.rea.yelpsample.App;
import matt.rea.yelpsample.R;
import matt.rea.yelpsample.databinding.FragmentSearchBinding;
import matt.rea.yelpsample.network.AutoCompleteResponse;
import matt.rea.yelpsample.ui.BaseFragment;
import matt.rea.yelpsample.ui.main.MainFragment;
import matt.rea.yelpsample.ui.settings.SettingsFragment;
import matt.rea.yelpsample.util.KeyboardUtil;

/**
 * Created by Matt on 5/19/17.
 */

public class SearchFragment extends BaseFragment implements SearchContract.View {

    SearchContract.Presenter mPresenter;
    private static final String LOG_TAG = "MainFragment";

    FragmentSearchBinding mBinding;
    SearchStore mSearches;
    ArrayList<String> mSearchList;

    private PreviousSearchesAdapter mAdapter;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchPresenter(App.get(getContext()).getStorage());
        mSearches = new SearchStore(App.get(getContext()).getStorage());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        mBinding.setPresenter(mPresenter);

        mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.onSearchClicked(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO -- finish implementation
//                mPresenter.onSearchTextChanged(newText);
                return false;
            }
        });
        mBinding.searchView.requestFocus();
        showKeyBoard();

        initRecyclerView();
        return mBinding.getRoot();
    }

    private void initRecyclerView(){
        mSearchList = new ArrayList<>(mSearches.getPreviousSearches());
        mAdapter = new PreviousSearchesAdapter(mSearchList, (term) -> mPresenter.onSearchClicked(term));

        mBinding.previousSearches.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.previousSearches.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attach(this);
    }

    @Override
    public void onSuggestionsReturned(AutoCompleteResponse body) {
        //TODO -- finish implementation
    }

    @Override
    public void returnWithSearchTerm(String term) {
        mFragmentNavigation.pushFragment(MainFragment.newInstanceWithSearchTerm(term));
    }

    @Override
    public void showDeleteDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage(R.string.delete_search_confirm)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    dialog.dismiss();
                    mSearches.clearSearches();
                    initRecyclerView();
                })
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> {
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public void navigateToMain() {
        mFragmentNavigation.popFragment();
    }

    @Override
    public void navigateToSettings() {
        mFragmentNavigation.pushFragment(SettingsFragment.newInstance());
    }

    @Override
    public void hideKeyBoardIfOpen() {
        KeyboardUtil.hideSoftKeyboard(mBinding.getRoot(), getActivity());
    }

    @Override
    public void showKeyBoard() {
        KeyboardUtil.showSoftKeyboard((AppCompatActivity) getActivity(), mBinding.searchView);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detach();
        super.onDestroyView();
    }
}
