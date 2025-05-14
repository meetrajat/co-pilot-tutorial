package com.example.a29th_mar_android_project.network.retrofit.api

import com.example.a29th_mar_android_project.continent_detail.ContinentsServer
import retrofit2.http.GET
//TODO: Should all country related things like ui/api etc in one package
interface CountryApiService {
    @GET("all") // Assuming "/all" is the endpoint to get all countries
    suspend fun getAllCountries(): List<ContinentsServer> // For a list
    // or
    // @GET("all")
    // fun getAllCountries(): Call<List<Country>> //For a callback approach

    //Example with path param
    // @GET("country/{code}")
    // suspend fun getCountryByCode(@Path("code") code: String) : Country
}