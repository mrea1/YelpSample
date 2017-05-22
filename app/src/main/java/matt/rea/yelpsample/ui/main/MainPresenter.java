package matt.rea.yelpsample.ui.main;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import matt.rea.yelpsample.Storage;
import matt.rea.yelpsample.network.SearchResponse;
import matt.rea.yelpsample.network.YelpService;
import matt.rea.yelpsample.network.auth.AuthService;
import matt.rea.yelpsample.network.auth.GetTokenResponse;
import matt.rea.yelpsample.network.auth.Session;
import matt.rea.yelpsample.ui.settings.Settings;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    private static final String LOG_TAG = "MainPresenter";

    private static final int SEARCH_LIMIT = 20;
    private int offset;

    private YelpService yelpService;
    private AuthService authService;

    private MainContract.View mView;
    private Session mSession;
    private Settings mSettings;
    private String searchTerm;

    public MainPresenter(Storage storage) {
        mSession = new Session(storage);
        mSettings = new Settings(storage);
        offset = 0;
    }

    @Override
    public void detach() {
        mView = null;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public void loadMore(int offset) {
        getBusinesses(SEARCH_LIMIT, offset);
    }

    //TODO -- this should be handled by dagger
    private void initYelpService() {
        yelpService = new YelpService(mSession);
    }

    @Override
    public void onSearchClicked() {
        mView.navigateToSearch();
    }

    @Override
    public void onSettingsClicked() {
        mView.navigateToSettings();
    }

    @Override
    public void attach(MainContract.View view) {
        mView = view;

        if (!mSession.isLoggedIn()) {
            authService = new AuthService();
            getToken();
        } else {
            initYelpService();
            getBusinesses();
        }
    }

    public void getBusinesses() {
        mView.showProgress();

        Observable<Response<SearchResponse>> response = yelpService.getBusinesses(searchTerm, mSettings.getCity());

        response.subscribeOn(Schedulers.io())
                .doOnError(this::showError)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                            if (data.isSuccessful()) {
                                Log.d(LOG_TAG, "Success!");
                                mView.onBusinessesReturned(data.body());
                            } else {
                                mView.showErrorDialog();
                            }
                            mView.hideProgress();
                        },
                        error -> {
                            mView.hideProgress();
                            mView.showErrorDialog();
                        });
    }

    public void getBusinesses(int limit, int offset) {
        mView.showProgress();

        Observable<Response<SearchResponse>> response = yelpService.getBusinesses(searchTerm, mSettings.getCity(),
                limit, offset);

        response.subscribeOn(Schedulers.io())
                .doOnError(this::showError)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                            if (data.isSuccessful()) {
                                Log.d(LOG_TAG, "Success!");
                                mView.onMoreBusinessesReturned(data.body());
                            } else {
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
                .doOnError(this::showError)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                            if (data.isSuccessful()) {
                                Log.d(LOG_TAG, "Auth Token Success!");
                                mSession.setSession(data.body());
                                initYelpService();
                                getBusinesses();
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

    private void showError(Throwable error) {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(() -> mView.showErrorDialog());
    }
}
