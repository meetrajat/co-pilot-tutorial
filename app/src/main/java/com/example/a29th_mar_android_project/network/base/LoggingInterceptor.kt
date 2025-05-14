package com.example.a29th_mar_android_project.network.base

import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class LoggingInterceptor : ApolloInterceptor {
    override fun <D : com.apollographql.apollo3.api.Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        println("GraphQL Request: ${request.operation.name()}")
        // You can log more details from the request, like variables:
        // println("Variables: ${request.variables}")

        return chain.proceed(request).onEach { response ->
            println("GraphQL Response for ${request.operation.name()}:")
            if (response.hasErrors()) {
                response.errors?.forEach { error ->
                    println("Error: ${error.message}")
                }
            } else {
                // For large responses, consider logging specific fields instead of the entire data object
                println("Data: ${response.data}")
                println("Successful GraphQL Response for ${request.operation.name()}")
            }
        }
    }
}