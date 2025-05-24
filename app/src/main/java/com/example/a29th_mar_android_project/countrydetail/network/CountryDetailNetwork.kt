package com.example.a29th_mar_android_project.countrydetail.network

import com.example.a29th_mar_android_project.countrydetail.CountryDetail

interface CountryDetailNetwork {
    suspend fun getCountryDetail(code: String): CountryDetail

}