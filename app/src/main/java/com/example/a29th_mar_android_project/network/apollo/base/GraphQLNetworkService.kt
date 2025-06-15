package com.example.a29th_mar_android_project.network.apollo.base

import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.ApolloResponse

interface GraphQLNetworkService {
    suspend fun <D : Query.Data> executeQuery(query: Query<D>): ApolloResponse<D>
    suspend fun <D : Mutation.Data> executeMutation(mutation: Mutation<D>): ApolloResponse<D>
}