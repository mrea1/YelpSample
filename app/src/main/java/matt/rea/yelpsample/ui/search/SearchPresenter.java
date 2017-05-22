package matt.rea.yelpsample.ui.search;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import matt.rea.yelpsample.Storage;
import matt.rea.yelpsample.network.AutoCompleteResponse;
import matt.rea.yelpsample.network.YelpService;
import matt.rea.yelpsample.network.auth.AuthService;
import matt.rea.yelpsample.network.auth.GetTokenResponse;
import matt.rea.yelpsample.network.auth.Session;
import matt.rea.yelpsample.ui.settings.Settings;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {

    private static final String LOG_TAG = "MainPresenter";

    private YelpService yelpService;
    private AuthService authService;

    private SearchContract.View mView;
    private Session mSession;
    private Settings mSettings;
    private SearchStore mSearches;

    public SearchPresenter(Storage storage) {
        mSession = new Session(storage);
        mSettings = new Settings(storage);
        mSearches = new SearchStore(storage);
        if (!mSession.isLoggedIn()) {
            authService = new AuthService();
            getToken();
        } else {
            initYelpService();
        }
    }

    @Override
    public void detach() {
        mView = null;
    }

    //TODO -- this should be handled by dagger as a singleton
    private void initYelpService() {
        yelpService = new YelpService(mSession);
    }

    @Override
    public void onSearchClicked(String searchTerm) {
        mSearches.addSearch(searchTerm);
        mView.returnWithSearchTerm(searchTerm);
    }

    @Override
    public void onSettingsClicked() {
        mView.navigateToSettings();
    }

    @Override
    public void onDeleteClicked() {
        mSearches.clearSearches();
        showDeleteDialog();
    }

    private void showDeleteDialog() {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(() -> mView.showDeleteDialog());
    }

    @Override
    public void attach(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void onSearchTextChanged(String text) {
        if (!TextUtils.isEmpty(text)) {
            getSuggestions(text);
        }
    }

    //TODO -- finish implementing
    private void getSuggestions(String text) {
        mView.showProgress();

        Observable<Response<AutoCompleteResponse>> response = yelpService.getSuggestions(text, null, null);

        response.subscribeOn(Schedulers.io())
                .doOnError((error) -> mView.showErrorDialog())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                            if (data.isSuccessful()) {
                                Log.d(LOG_TAG, "Auth Token Success!");
                                mView.onSuggestionsReturned(data.body());
                            } else {
                                Log.d(LOG_TAG, "Auth Token Failed");
                                mView.showErrorDialog();
                            }
                            mView.hideProgress();
                        },
                        error -> {
                            mView.hideProgress();
                            mView.showErrorDialog();
                        }
                );
    }

    public void getToken() {
        mView.showProgress();

        Observable<Response<GetTokenResponse>> response = authService.getToken();

        response.subscribeOn(Schedulers.io())
                .doOnError((error) -> mView.showErrorDialog())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                            if (data.isSuccessful()) {
                                Log.d(LOG_TAG, "Auth Token Success!");
                                mSession.setSession(data.body());
                                initYelpService();
                            } else {
                                Log.d(LOG_TAG, "Auth Token Failed");
                                mView.showErrorDialog();
                            }
                            mView.hideProgress();
                        },
                        error -> {
                            mView.hideProgress();
                            mView.showErrorDialog();
                        }
                );
    }
}
