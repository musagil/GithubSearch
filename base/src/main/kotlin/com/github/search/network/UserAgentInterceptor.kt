package com.github.search.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UserAgentInterceptor @Inject internal constructor(
    private val userAgent: UserAgent
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("User-Agent", userAgent.userAgent)
                .build()
        )
}
