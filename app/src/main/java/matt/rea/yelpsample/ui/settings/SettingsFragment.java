package matt.rea.yelpsample.ui.settings;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import matt.rea.yelpsample.App;
import matt.rea.yelpsample.R;
import matt.rea.yelpsample.databinding.FragmentSettingsBinding;
import matt.rea.yelpsample.ui.BaseFragment;
import matt.rea.yelpsample.ui.main.BusinessAdapter;
import matt.rea.yelpsample.ui.search.SearchFragment;
import matt.rea.yelpsample.util.KeyboardUtil;

/**
 * Created by Matt on 5/19/17.
 */

public class SettingsFragment extends BaseFragment implements SettingsContract.View {

    SettingsContract.Presenter mPresenter;
    private BusinessAdapter mAdapter;
    private static final String LOG_TAG = "SettingsFragment";
    private static final int NUM_COLUMNS = 2;
    private Settings mSettings;

    FragmentSettingsBinding mBinding;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = new Settings(App.get(getContext()).getStorage());
        mPresenter = new SettingsPresenter(mSettings);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.settings_title));

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        mBinding.city.setText(App.get(getContext()).getStorage().getSettingCity());
        mBinding.city.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                hideKeyBoardIfOpen();
                return true;
            }
            return false;
        });

        mBinding.setPresenter(mPresenter);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attach(this);
    }

    @Override
    public void navigateToSearch() {
        mFragmentNavigation.pushFragment(SearchFragment.newInstance());
    }

    @Override
    public void saveSettings() {
        mSettings.setCity(mBinding.city.getText().toString().trim());
    }

    @Override
    public void hideKeyBoardIfOpen() {
        KeyboardUtil.hideSoftKeyboard(mBinding.getRoot(), getActivity());
    }

    @Override
    public void onDestroyView() {
        mPresenter.detach();
        super.onDestroyView();
    }
}
