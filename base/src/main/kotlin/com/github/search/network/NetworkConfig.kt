package com.github.search.network

import okhttp3.CertificatePinner
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit.MINUTES

interface NetworkConfig {
    val baseUrl: String
    val certificatePinner: CertificatePinner get() = CertificatePinner.DEFAULT
    val networkTimeoutInSeconds: Long get() = DEFAULT_NETWORK_TIMEOUT_IN_SECONDS

    val interceptors: List<Interceptor>
        get() = emptyList()

    val networkInterceptors: List<Interceptor>
        get() = emptyList()

    companion object {
        private val DEFAULT_NETWORK_TIMEOUT_IN_SECONDS = MINUTES.toSeconds(3)
    }
}
