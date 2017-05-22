package matt.rea.yelpsample.network;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import matt.rea.yelpsample.APIConstants;
import matt.rea.yelpsample.BuildConfig;
import matt.rea.yelpsample.network.auth.Session;
import okhttp3.Interceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Request client for the Yelp API
 */

public class YelpService extends BaseRetrofitInstance<YelpAPI> {

    private final Session session;

    public YelpService(Session session) {
        super(YelpAPI.class);

        this.session = session;
    }

    protected String createAuthHeaderValue() {
        return "Bearer " + session.getSessionToken();
    }

    @Override
    protected String provideBaseUrl() {
        return APIConstants.Yelp.BASE_URL;
    }

    protected void provideConverterFactories(
            @NonNull final List<Converter.Factory> converterFactories) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();

        converterFactories.add(GsonConverterFactory.create(gson));
    }


    @Override
    protected void provideNetworkInterceptors(@NonNull final List<Interceptor> interceptors) {
        super.provideNetworkInterceptors(interceptors);

        if (BuildConfig.DEBUG) {
            interceptors.add(new StethoInterceptor());
            interceptors.add(getLoggingInterceptor());
        }
    }

    protected void providerCallAdapters(@NonNull final List<CallAdapter.Factory> adapterFactories) {
        super.providerCallAdapters(adapterFactories);
        adapterFactories.add(RxJavaCallAdapterFactory.create());
    }

    public Observable<Response<SearchResponse>> getBusinesses(String searchTerm,
            String city,
            Long latitude,
            Long longitude) {
        return getApi().getBusinesses(createAuthHeaderValue(), searchTerm, city, latitude, longitude, null, null);
    }

    public Observable<Response<SearchResponse>> getBusinesses(String searchTerm, String city) {
        return getApi().getBusinesses(createAuthHeaderValue(), searchTerm, city, null, null, null, null);
    }

    public Observable<Response<SearchResponse>> getBusinesses(String searchTerm,
            String city,
            Integer limit,
            Integer offset) {
        return getApi().getBusinesses(createAuthHeaderValue(), searchTerm, city, null, null, limit, offset);
    }

    public Observable<Response<AutoCompleteResponse>> getSuggestions(String searchTerm, Long latitude, Long longitude) {
        return getApi().getAutoComplete(createAuthHeaderValue(), searchTerm, latitude, longitude);
    }

}
