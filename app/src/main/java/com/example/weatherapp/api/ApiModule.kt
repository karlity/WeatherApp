package com.example.weatherapp.api

import android.content.Context
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.api.weather.WeatherApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun createMoshi() : Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun createMoshiDeserializer(@ApplicationContext context : Context, moshi: Moshi) : MoshiDeserializer {
        return MoshiDeserializer(context, moshi = moshi)
    }

    //Not actually using this but adding for funsies
    @Singleton
    @Provides
    fun createHttpLoggingInterceptor() : HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor.Logger { message -> Timber.tag("OkHttp").v(message) }
        return HttpLoggingInterceptor(logger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //Same with this
    @Singleton
    @Provides
    fun createOkHttp(
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    //Won't be used but demonstrating what I WOULD do if we were making API calls
    @Singleton
    @Provides
    fun createRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ) : Retrofit {
        return Retrofit.Builder()
            //Sub with the actual base URL in
            .baseUrl("https://fakeUrl.com")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
}