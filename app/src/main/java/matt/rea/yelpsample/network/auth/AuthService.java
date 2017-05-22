package matt.rea.yelpsample.network.auth;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.List;

import matt.rea.yelpsample.APIConstants;
import static matt.rea.yelpsample.APIConstants.Auth.GRANT_TYPE_VALUE;
import matt.rea.yelpsample.BuildConfig;
import matt.rea.yelpsample.network.BaseRetrofitInstance;
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

public class AuthService extends BaseRetrofitInstance<AuthAPI> {

    public AuthService() {
        super(AuthAPI.class);
    }

    @Override
    protected String provideBaseUrl() {
        return APIConstants.Yelp.AUTH_BASE_URL;
    }

    protected void provideConverterFactories(
            @NonNull final List<Converter.Factory> converterFactories) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .excludeFieldsWithoutExposeAnnotation()
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


    public Observable<Response<GetTokenResponse>> getToken() {
        return getApi().getToken(
                GRANT_TYPE_VALUE,
                APIConstants.Auth.CLIENT_ID_VALUE,
                APIConstants.Auth.CLIENT_ID_SECRET);
    }

}
