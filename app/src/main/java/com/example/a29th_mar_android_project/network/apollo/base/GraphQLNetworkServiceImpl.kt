package com.example.a29th_mar_android_project.network.apollo.base

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Query

open class GraphQLNetworkServiceImpl: GraphQLNetworkService {
    private val apolloClient = ApolloClientProvider.apolloClient;
    override suspend fun <D : Query.Data> executeQuery(query: Query<D>): ApolloResponse<D> {
        return apolloClient.query(query).execute()
    }

    override suspend fun <D : Mutation.Data> executeMutation(mutation: Mutation<D>): ApolloResponse<D> {
        return apolloClient.mutation(mutation).execute()
    }

}