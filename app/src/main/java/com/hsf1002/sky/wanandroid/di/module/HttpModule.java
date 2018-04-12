package com.hsf1002.sky.wanandroid.di.module;

import com.hsf1002.sky.wanandroid.BuildConfig;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.core.http.api.GeeksApis;
import com.hsf1002.sky.wanandroid.core.http.cookies.CookiesManager;
import com.hsf1002.sky.wanandroid.di.qualifier.WanAndroidUrl;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hefeng on 18-4-8.
 */

@Module
public class HttpModule{
    @Singleton
    @Provides
    GeeksApis provideGeeksApi(@WanAndroidUrl Retrofit retrofit)
    {
        return retrofit.create(GeeksApis.class);
    }

    @Singleton
    @Provides
    @WanAndroidUrl
    Retrofit provideGeeksRetrofit(Retrofit.Builder builder, OkHttpClient client)
    {
        return createRetrofit(builder, client, GeeksApis.HOST);
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder()
    {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder()
    {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder)
    {
        if (BuildConfig.DEBUG)
        {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }

        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * Constants.CACHE_SIZE);
        Interceptor interceptor = chain -> {
            Request request = chain.request();

            if (!CommonUtils.isNetworkConnected())
            {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);

            if (CommonUtils.isNetworkConnected())
            {
                int maxAge = 0;
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            }
            else
            {
                int maxStale = 60 * 60 * 24 * 28;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();

            }

            return  response;
        };

        builder.addNetworkInterceptor(interceptor);
        builder.addInterceptor(interceptor);
        builder.cache(cache);

        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);

        builder.retryOnConnectionFailure(true);
        //builder.cookieJar(new CookiesManager());

        return builder.build();
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url)
    {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
