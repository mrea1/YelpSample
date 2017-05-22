package matt.rea.yelpsample.ui.settings;

/**
 * Created by Matt on 5/19/17.
 */

public interface SettingsContract {
    interface View {

        void hideKeyBoardIfOpen();

        void showProgress();

        void hideProgress();

        void showErrorDialog();

        void navigateToSearch();

        void goBack();

        void saveSettings();
    }

    interface Presenter {
        void attach(View view);

        void detach();

        void onSave();

        void onSearchClicked();

        void onSettingsClicked();
    }
}
