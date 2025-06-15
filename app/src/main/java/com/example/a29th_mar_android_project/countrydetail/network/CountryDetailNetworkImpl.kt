package com.example.a29th_mar_android_project.countrydetail.network

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.example.a29th_mar_android_project.CountriesByContinentQuery
import com.example.a29th_mar_android_project.CountryDetailQuery
import com.example.a29th_mar_android_project.countries.uidata.Country
import com.example.a29th_mar_android_project.countrydetail.CountryDetail

class CountryDetailNetworkImpl(private val apolloClient: ApolloClient): CountryDetailNetwork {
    override suspend fun getCountryDetail(code: String): CountryDetail {
        try {
            val response = apolloClient.query(CountryDetailQuery(code)).execute()
            if (response.hasErrors()) {
                Log.e("GraphQL Error", "Errors: ${response.errors}")
                throw Exception("GraphQL errors: ${response.errors}")
            }else{
                val countryDetail = response.data?.country
                if (countryDetail != null) {
                    return CountryDetail(
                        name = countryDetail.name,
                        capital = countryDetail.capital ?: "Unknown",
                        flag = countryDetail.emoji ?: "Unknown",
                        currency = countryDetail.currency?: "Unknown",
                        phone = countryDetail.phone ?: "Unknown"
                    )
                } else {
                    throw Exception("Country detail not found")
                }
            }
        }catch (e: Exception) {
            println("-----" + e.message)
            throw Exception("Error fetching countries: ${e.message}")
        };
    }
}
