package com.example.a29th_mar_android_project.continent_detail.repo


import com.example.a29th_mar_android_project.continent_detail.ContinentsServer
import com.example.a29th_mar_android_project.continent_detail.network.remote.ContinentRemoteDataSource

class ContinentRepo(private val remoteDataSource: ContinentRemoteDataSource) {
     suspend fun getContinent(continentCode: String): MutableList<ContinentsServer> {
         //Based on some business logic, you can decide which data source to use
         return remoteDataSource.getContinentFromRemote(continentCode)
     }
    // Additional methods for fetching continent details can be added here
}