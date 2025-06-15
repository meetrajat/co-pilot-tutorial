package com.example.a29th_mar_android_project.countries.repo

import com.apollographql.apollo3.api.ApolloResponse
import com.example.a29th_mar_android_project.CountriesByContinentQuery
import com.example.a29th_mar_android_project.countries.uidata.Country
import com.example.a29th_mar_android_project.network.countries.network.CountriesNetworkService

class CountriesRepo(private val networkService: CountriesNetworkService) {

    suspend fun getCountriesByContinent(continentCode: String): MutableList<Country> {
        return networkService.fetchCountriesByContinent(continentCode)
    }


}