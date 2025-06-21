package com.example.a29th_mar_android_project.countries.repo

import android.content.Context
import com.example.a29th_mar_android_project.countries.network.remote.CountriesRemoteDataSource
import com.example.a29th_mar_android_project.countries.network.remote.CountriesRemoteGQLImpl
import com.example.a29th_mar_android_project.countries.uidata.Country
import com.example.a29th_mar_android_project.network.apollo.cache.GraphQLCacheManager
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.variables
import com.example.a29th_mar_android_project.CountriesByContinentQuery
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

    suspend fun getCountriesByContinent(continentCode: String): MutableList<Country> {
        val query = CountriesByContinentQuery(continentCode)
        val requestKey = "Countries";
        /*query::class.simpleName + query.variables().toString()*/
        val cachedResponse: String =
            cacheManager.getCachedResponse("Countries")
        if (cachedResponse != null) {
            val json = JSONObject(cachedResponse)
            val continents = json.getJSONObject("data")
                .getJSONArray("continents")
            val countriesList = mutableListOf<Country>()
            for (i in 0 until continents.length()) {
                val countryJson = continents.getJSONObject(i)
                val name = countryJson.getString("name")
                val code = countryJson.getString("code")
                countriesList.add(Country(name, code))
            }
            return countriesList
        }
        // Not in cache, fetch from network
        val response = remoteDataSource.countriesRemoteGQLImpl.executeQuery(query)
        cacheManager.cacheResponse(requestKey)
        val countries = response.data?.continent?.countries
        return countries?.map { Country(it.name, it.code) }?.toMutableList() ?: mutableListOf()
    }
}