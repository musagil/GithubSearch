package com.github.search.app.config

import com.github.search.network.NetworkConfig
import com.github.search.network.NetworkInterceptor
import com.github.search.network.OfflineCacheInterceptor
import okhttp3.Interceptor
import javax.inject.Inject

class ProdNetworkConfig @Inject constructor(
    private val networkInterceptor: NetworkInterceptor,
    private val offlineCacheInterceptor: OfflineCacheInterceptor
) : NetworkConfig {
    override val baseUrl: String
        get() = "https://api.github.com"

    override val networkInterceptors: List<Interceptor>
        get() = listOf(networkInterceptor)

    override val interceptors: List<Interceptor>
        get() = listOf(offlineCacheInterceptor)
}
