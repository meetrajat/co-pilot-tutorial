package com.example.a29th_mar_android_project.countrydetail.repo

import com.example.a29th_mar_android_project.countrydetail.CountryDetail
import com.example.a29th_mar_android_project.countrydetail.network.CountryDetailNetwork

class CountryDetailRepository(val countryDetailNetworkImpl: CountryDetailNetwork){
    suspend fun getCountryDetail(code:String): CountryDetail {
        // This method should be implemented to fetch country details
        // from the network or database.
        // For example, you can use ApolloClient to fetch data from GraphQL API.
        // val response = apolloClient.query(CountryDetailQuery()).execute()
        // return response.data?.countryDetail?.let { CountryDetail(it) } ?: throw Exception("Country detail not found")
        return countryDetailNetworkImpl.getCountryDetail(code);
    }
}