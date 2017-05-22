package matt.rea.yelpsample.ui.main;

import matt.rea.yelpsample.network.SearchResponse;

/**
 * Created by Matt on 5/19/17.
 */

public interface MainContract {
    interface View {

        void hideKeyBoardIfOpen();

        void showProgress();

        void hideProgress();

        void showErrorDialog();

        void onBusinessesReturned(SearchResponse response);

        void navigateToSearch();

        void navigateToSettings();

        void onMoreBusinessesReturned(SearchResponse body);
    }

    interface Presenter {
        void attach(View view);

        void detach();

        void onSearchClicked();

        void onSettingsClicked();

        void getBusinesses();

        void setSearchTerm(String term);

        void loadMore(int offset);
    }
}
