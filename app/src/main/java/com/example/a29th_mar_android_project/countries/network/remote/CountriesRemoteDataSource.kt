package com.example.a29th_mar_android_project.countries.network.remote

import com.apollographql.apollo3.ApolloClient
import com.example.a29th_mar_android_project.CountriesByContinentQuery
import com.example.a29th_mar_android_project.countries.uidata.Country

class CountriesRemoteDataSource(val countriesRemoteGQLImpl: CountriesRemoteGQLImpl) {
    suspend fun fetchCountriesByContinent(continentCode: String): MutableList<Country> {
        val list = mutableListOf<Country>()
        try {
            val response = countriesRemoteGQLImpl.executeQuery(CountriesByContinentQuery(continentCode));
            if (response.hasErrors()) {
                throw Exception("GraphQL errors: ${response.errors}")
            } else {
                val countries = response.data?.continent?.countries
                if (countries != null) {
                    for (country in countries) {
                        list.add(Country(country.name))
                    }
                }
            }
        } catch (e: Exception) {
            throw Exception("Error fetching countries: ${e.message}")
        }
        return list
    }
}
