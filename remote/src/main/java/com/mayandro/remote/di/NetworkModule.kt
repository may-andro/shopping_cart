package com.mayandro.remote.di

import com.mayandro.utility.BASE_URL
import com.mayandro.remote.RemoteDataSource
import com.mayandro.remote.RemoteDataSourceImpl
import com.mayandro.remote.retrofit.RetrofitApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private val REQUEST_TIMEOUT = 10

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor{chain ->
                val request = chain.request().newBuilder().addHeader(
                    "Connection",
                    "close"
                ).build()
                chain.proceed(request)
            }
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()

        return (okHttpClient)
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideServiceApi(retrofit: Retrofit): RetrofitApi = retrofit.create(RetrofitApi::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        retrofitApi: RetrofitApi
    ): RemoteDataSource {
        return RemoteDataSourceImpl(retrofitApi)
    }
}