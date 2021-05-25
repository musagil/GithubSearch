package com.github.search.network

import android.net.ConnectivityManager
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class OfflineCacheInterceptor @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val invocation: Invocation? = request.tag(Invocation::class.java)

        if (invocation != null) {
            val annotation: Cacheable? =
                invocation.method().getAnnotation(Cacheable::class.java)

            /* check if this request has the [Cacheable] annotation */
            if (annotation != null &&
                annotation.annotationClass.simpleName.equals("Cacheable") &&
                isNetworkConnected().not()
            ) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()

                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            } else {
                Log.d(TAG, "cache interceptor: not called.")
            }
        }
        return chain.proceed(request)
    }

    private fun isNetworkConnected(): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    companion object {

        const val TAG = "OfflineCacheInterceptor"

        const val HEADER_CACHE_CONTROL = "Cache-Control"
        const val HEADER_PRAGMA = "Pragma"
    }
}
