package com.example.a29th_mar_android_project.network.apollo.cache

import android.content.Context
import android.net.NetworkRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.KSerializer

class GraphQLCacheManager(private val context: Context) {
    private val cacheDao by lazy { GraphQLCacheDatabaseProvider.getInstance(context).graphQLCacheDao() }

    suspend fun getCachedResponse(networkRequestType: Int): String = withContext(Dispatchers.IO) {
        val cacheEntity = cacheDao.getCacheByKey(networkRequestType)
        return@withContext cacheEntity?.response ?: ""
    }

    suspend fun cacheResponse(requestKey: String,networkRequestType: Int) {
        val dummyJson = requestKey;
        dummyJson?.let {
            cacheDao.insertOrUpdateCache(GraphQLCacheEntity(
                requestKey = networkRequestType,
                response = it,
                timestamp = System.currentTimeMillis()
            ))
            cacheDao.pruneToLast5()
        }
    }


}
