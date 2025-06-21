package com.example.a29th_mar_android_project.network.apollo.cache

import android.content.Context
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

    fun getCachedResponse(requestKey: String): String {
        return when (requestKey) {
            "Continent" -> "{\"data\":{\"continents\":[{\"code\":\"AS\",\"name\":\"Asia\"},{\"code\":\"EU\",\"name\":\"Europe\"}]}}"
            "Country" -> "{\"data\":{\"country\":{\"name\":\"India\",\"capital\":\"New Delhi\",\"emoji\":\"🇮🇳\",\"currency\":\"INR\",\"phone\":\"91\"}}}"
            else -> ""
        }
    }

    suspend fun cacheResponse(requestKey: String) {
        val dummyJson = when (requestKey) {
            "Continent" -> "{\"data\":{\"continents\":[{\"code\":\"AS\",\"name\":\"Asia\"},{\"code\":\"EU\",\"name\":\"Europe\"}]}}"
            "Country" -> "{\"data\":{\"country\":{\"name\":\"India\",\"capital\":\"New Delhi\",\"emoji\":\"🇮🇳\",\"currency\":\"INR\",\"phone\":\"91\"}}}"
            else -> null
        }
        dummyJson?.let {
            cacheDao.insertOrUpdateCache(GraphQLCacheEntity(
                requestKey = requestKey,
                response = it,
                timestamp = System.currentTimeMillis()
            ))
            cacheDao.pruneToLast5()
        }
    }


}
