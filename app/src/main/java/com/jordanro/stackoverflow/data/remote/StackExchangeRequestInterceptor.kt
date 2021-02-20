package com.jordanro.stackoverflow.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class StackExchangeRequestInterceptor (private val apiKey: String) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url().newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}