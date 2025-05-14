package com.example.a29th_mar_android_project.network.apollo.base

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.a29th_mar_android_project.network.base.LoggingInterceptor
import okhttp3.OkHttpClient

object ApolloClientProvider {

    private const val BASE_URL = "https://countries.trevorblades.com/graphql"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    val apolloClient: ApolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .addInterceptor(LoggingInterceptor())
            .build()
    }

}