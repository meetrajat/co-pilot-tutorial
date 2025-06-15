package com.example.a29th_mar_android_project.continent_detail.repo

import com.example.a29th_mar_android_project.ContinentQuery
import com.example.a29th_mar_android_project.continent_detail.ContinentsServer
import com.example.a29th_mar_android_project.continent_detail.network.ContinentNetworkService

class ContinentRepo(private val continentRepoGQLImpl: ContinentRepoGQLImpl) {

    suspend fun getCountriesByContinent(continentCode: String): MutableList<ContinentsServer> {
        val listContinents: MutableList<ContinentsServer> = ArrayList<ContinentsServer>();
        val response = continentRepoGQLImpl.executeQuery(ContinentQuery());
        if (response.hasErrors()) {
            println("GraphQL errors 123: ${response.errors}")
        } else {
            val continents = response.data?.continents;
            println("GraphQL response: $continents")
            if (continents != null) {
                for (index in continents) {
                    val continent: ContinentsServer;
                    if (index != null && index.name != null) {
                        continent = ContinentsServer("", index.name)
                    } else {
                        continent = ContinentsServer("", "")
                    }
                    listContinents.add(continent)
                }
                println("listContinents: $listContinents")
                return listContinents;
            }
        }
        return listContinents;
    }

    // Additional methods for fetching continent details can be added here
}