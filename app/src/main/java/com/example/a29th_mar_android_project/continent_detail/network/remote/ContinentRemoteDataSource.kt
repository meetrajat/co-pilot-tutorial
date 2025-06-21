package com.example.a29th_mar_android_project.continent_detail.network.remote

import com.example.a29th_mar_android_project.ContinentQuery
import com.example.a29th_mar_android_project.continent_detail.ContinentsServer

class ContinentRemoteDataSource(val continentRepoGQLImpl:ContinentRepoGQLImpl) {
    suspend fun getContinentFromRemote(continentCode: String): MutableList<ContinentsServer> {
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
                        continent = ContinentsServer(index.code, index.name)
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
}