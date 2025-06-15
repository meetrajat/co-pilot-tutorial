package com.example.a29th_mar_android_project.network.countries.network

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.example.a29th_mar_android_project.CountriesByContinentQuery
import com.example.a29th_mar_android_project.countries.uidata.Country

class CountriesNetworkService(private val apolloClient: ApolloClient) {

    suspend fun fetchCountriesByContinent(continentCode: String): MutableList<Country> {
       val list = mutableListOf<Country>();
        try {
            println("GraphQL Query" + CountriesByContinentQuery.OPERATION_DOCUMENT)
            println("GraphQL Variables: $continentCode")
            val response = apolloClient.query(CountriesByContinentQuery("EU")).execute()
            if (response.hasErrors()) {
                Log.e("GraphQL Error", "Errors: ${response.errors}")
                throw Exception("GraphQL errors: ${response.errors}")
            }else{
                val countries = response.data?.continent?.countries
                if (countries != null) {
                    for (country in countries) {
                        list.add(Country(country.name))
                    }
                    println("Fetched ${list.size} countries")
                } else {
                    println("No countries found")
                }
            }
        }catch (e: Exception) {
            println("-----" + e.message)
            throw Exception("Error fetching countries: ${e.message}")
        }
        return list;
    }
}