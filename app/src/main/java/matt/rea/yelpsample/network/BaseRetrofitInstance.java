package matt.rea.yelpsample.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Matt on 5/19/17.
 */

public abstract class BaseRetrofitInstance<Api> {

    private Api mApi;
    private Class<Api> mApiClass;

    protected BaseRetrofitInstance(Class<Api> apiClass) {
        mApiClass = apiClass;
        mApi = createAPI();
    }

    public @NonNull Api getApi() {
        if (mApi == null) {
            throw new RuntimeException("Constructor must call through to super");
        }
        return mApi;
    }

    protected @NonNull Api createAPI() {

        Retrofit.Builder builder = new Retrofit.Builder();

        String url = provideBaseUrl();

        if (!url.endsWith("/")) {
            Log.w("BaseRetrofitClient", "Base url does not end with \"/\" ");
        }

        builder.baseUrl(url);

        // Add any required converter factories that have been provided
        List<Converter.Factory> converterFactories = new ArrayList<>();
        provideConverterFactories(converterFactories);
        for (Converter.Factory factory : converterFactories) {
            builder.addConverterFactory(factory);
        }

        // Add any required adapter factories that have been provided
        final List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
        providerCallAdapters(adapterFactories);
        for (CallAdapter.Factory adapter : adapterFactories) {
            builder.addCallAdapterFactory(adapter);
        }

        // Add a callback executor if provided
        Executor executor = providerExecutor();
        if (executor != null) {
            builder.callbackExecutor(executor);
        }

        // Set the http client
        builder.client(provideHttpClient());

        Retrofit retrofit = builder.build();

        // Create the required Api with the provided class
        return retrofit.create(mApiClass);

    }

    protected abstract @NonNull String provideBaseUrl();

    protected void provideNetworkInterceptors(@NonNull final List<Interceptor> interceptors) {

    }

    protected void provideConverterFactories(@NonNull final List<Converter.Factory> converterFactories) {

    }

    protected void providerCallAdapters(@NonNull final List<CallAdapter.Factory> adapterFactories) {

    }

    protected @Nullable Executor providerExecutor() {
        return null;
    }


    protected @NonNull OkHttpClient provideHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder = provideHttpClientConfiguration(builder);

        final List<Interceptor> interceptors = new ArrayList<>();
        provideNetworkInterceptors(interceptors);

        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }

        return builder.build();
    }

    protected OkHttpClient.Builder provideHttpClientConfiguration(OkHttpClient.Builder builder) {
        return builder;
    }

    public static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                message -> Log.d("OkHttp", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

}
