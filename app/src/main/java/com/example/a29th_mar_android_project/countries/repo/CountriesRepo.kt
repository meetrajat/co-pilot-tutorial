package com.example.a29th_mar_android_project.countries.repo

import android.content.Context
import com.example.a29th_mar_android_project.countries.network.remote.CountriesRemoteDataSource
import com.example.a29th_mar_android_project.countries.network.remote.CountriesRemoteGQLImpl
import com.example.a29th_mar_android_project.countries.uidata.Country
import com.example.a29th_mar_android_project.network.apollo.cache.GraphQLCacheManager
import com.apollographql.apollo3.api.ApolloResponse
import com.example.a29th_mar_android_project.CountriesByContinentQuery
import com.example.a29th_mar_android_project.util.AppUtils
import com.example.a29th_mar_android_project.util.BuildJsonObject
import org.json.JSONObject

class CountriesRepo(
    private val remoteDataSource: CountriesRemoteDataSource,
    private val cacheManager: GraphQLCacheManager
) {
    companion object {
        fun create(context: Context): CountriesRepo {
            val gqlImpl = CountriesRemoteGQLImpl() // No context needed
            val remoteDataSource = CountriesRemoteDataSource(gqlImpl)
            val cacheManager = GraphQLCacheManager(context)
            return CountriesRepo(remoteDataSource, cacheManager)
        }
    }

    private suspend fun storeResponseInCache(response: ApolloResponse<CountriesByContinentQuery.Data>, networkRequestKey: Int) {
        BuildJsonObject.buildCountriesJson(response)?.let { jsonResponse ->
            cacheManager.cacheResponse(jsonResponse, networkRequestKey)
        }
    }

    suspend fun getCountriesByContinent(continentCode: String): MutableList<Country> {
        val query = CountriesByContinentQuery(continentCode)
        val networkRequestKey = AppUtils.getUniqueCountryCacheKey(continentCode)
        val cachedResponse: String = cacheManager.getCachedResponse(networkRequestKey)

        if (cachedResponse.isNotEmpty()) {
            return BuildJsonObject.parseCountriesFromJson(cachedResponse)
        }

        // Not in cache, fetch from network
        val response = remoteDataSource.countriesRemoteGQLImpl.executeQuery(query)
        storeResponseInCache(response, networkRequestKey)
        val countries = response.data?.continent?.countries
        return countries?.map { Country(it.name, it.code) }?.toMutableList() ?: mutableListOf()
    }
}