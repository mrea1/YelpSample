package matt.rea.yelpsample.ui.settings;

import android.os.Handler;
import android.os.Looper;

public class SettingsPresenter implements SettingsContract.Presenter {

    private static final String LOG_TAG = "SettingsPresenter";

    private SettingsContract.View mView;

    private Settings mSettings;

    public SettingsPresenter(Settings settings) {
        mSettings = settings;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void onSave() {
        mView.saveSettings();
        mView.goBack();
    }

    @Override
    public void onSearchClicked() {
        mView.navigateToSearch();
    }

    @Override
    public void onSettingsClicked() {
        mView.navigateToSearch();
    }

    @Override
    public void attach(SettingsContract.View view) {
        mView = view;
    }

    private void showError(Throwable error) {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(() -> mView.showErrorDialog());
    }
}
