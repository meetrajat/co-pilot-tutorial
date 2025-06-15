package com.example.a29th_mar_android_project.countries.repo

import com.example.a29th_mar_android_project.countries.network.remote.CountriesRemoteDataSource
import com.example.a29th_mar_android_project.countries.uidata.Country

class CountriesRepo(private val remoteDataSource: CountriesRemoteDataSource) {
    suspend fun getCountriesByContinent(continentCode: String): MutableList<Country> {
        return remoteDataSource.fetchCountriesByContinent(continentCode)
    }
}