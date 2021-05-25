package com.github.search.network

import com.github.search.network.di.NetworkScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

@Module
class NetworkModule {

    @Provides
    internal fun provideOkHttpClient(
        networkConfig: NetworkConfig,
        cache: Cache
    ): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(networkConfig.networkTimeoutInSeconds, SECONDS)
        .readTimeout(networkConfig.networkTimeoutInSeconds, SECONDS)
        .certificatePinner(networkConfig.certificatePinner)
        .cache(cache)
        .addInterceptors(networkConfig)
        .addNetworkInterceptors(networkConfig)
        .build()

    @Provides
    internal fun provideNetwork(
        okHttpClient: OkHttpClient,
        networkConfig: NetworkConfig
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(networkConfig.baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @CheckReturnValue
    @NetworkScheduler
    internal fun provideNetworkScheduler(): Scheduler = Schedulers.io()

    companion object {
        private fun OkHttpClient.Builder.addInterceptors(networkConfig: NetworkConfig) = apply {
            networkConfig.interceptors.forEach {
                addInterceptor(it)
            }
        }

        private fun OkHttpClient.Builder.addNetworkInterceptors(networkConfig: NetworkConfig) =
            apply {
                networkConfig.networkInterceptors.forEach {
                    addInterceptor(it)
                }
            }
    }
}
