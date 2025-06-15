package com.example.a29th_mar_android_project.continent_detail.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.example.a29th_mar_android_project.ContinentQuery
import com.example.a29th_mar_android_project.continent_detail.ContinentsServer

class ContinentNetworkService(private val apolloClient: ApolloClient) {

    suspend fun fetchContinents(code:String): MutableList<ContinentsServer>{
        val listContinents:MutableList<ContinentsServer> = ArrayList<ContinentsServer>();
        try {
            //val continentCode: Optional<String?> = "EU" // Replace with the actual continent code
            val response = apolloClient.query(ContinentQuery())
                .execute() // Replace with your generated query class
            if (response.hasErrors()) {
                println("GraphQL errors 123: ${response.errors}")
            } else {
                val continents = response.data?.continents ;
                println("GraphQL response: $continents")
                if (continents != null) {
                    for ( index in continents){
                        val continent: ContinentsServer;
                        if (index != null && index.name != null) {
                            continent = ContinentsServer("",index.name)
                        }else{
                            continent = ContinentsServer("","")
                        }
                        listContinents.add(continent)
                    }
                    println("listContinents: $listContinents")
                    return listContinents;
                }
            }
        } catch (e: ApolloException) {
            println("Apollo error: ${e.message}")
        }
        return listContinents;
    }
}