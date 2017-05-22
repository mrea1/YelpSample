package matt.rea.yelpsample.ui.search;

import matt.rea.yelpsample.network.AutoCompleteResponse;

/**
 * Created by Matt on 5/19/17.
 */

public interface SearchContract {
    interface View {

        void navigateToMain();

        void navigateToSettings();

        void hideKeyBoardIfOpen();

        void showProgress();

        void hideProgress();

        void showKeyBoard();

        void showErrorDialog();

        void onSuggestionsReturned(AutoCompleteResponse body);

        void returnWithSearchTerm(String term);

        void showDeleteDialog();
    }

    interface Presenter {

        void onSearchClicked(String searchTerm);

        void onSettingsClicked();

        void onDeleteClicked();

        void attach(View view);

        void detach();

        void onSearchTextChanged(String text);
    }
}
