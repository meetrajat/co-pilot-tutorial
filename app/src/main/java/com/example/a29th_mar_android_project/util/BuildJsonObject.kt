package com.example.a29th_mar_android_project.util

import com.example.a29th_mar_android_project.ContinentQuery
import com.example.a29th_mar_android_project.CountriesByContinentQuery
import com.example.a29th_mar_android_project.continent_detail.ContinentsServer
import com.example.a29th_mar_android_project.countries.uidata.Country
import com.apollographql.apollo3.api.ApolloResponse
import org.json.JSONArray
import org.json.JSONObject

object BuildJsonObject {
    fun buildContinentsJson(continents: List<ContinentQuery.Continent?>): String {
        return JSONObject().apply {
            put("data", JSONObject().apply {
                put("continents", JSONArray().apply {
                    continents.filterNotNull().forEach { continent ->
                        put(JSONObject().apply {
                            put("code", continent.code)
                            put("name", continent.name)
                        })
                    }
                })
            })
        }.toString()
    }

    fun parseContinentsFromJson(cachedResponse: String): MutableList<ContinentsServer> {
        val continentsList = mutableListOf<ContinentsServer>()
        val json = JSONObject(cachedResponse)
        val continentsArray = json.getJSONObject("data").getJSONArray("continents")
        for (i in 0 until continentsArray.length()) {
            val continentJson = continentsArray.getJSONObject(i)
            val code = continentJson.optString("code", "")
            val name = continentJson.optString("name", "")
            continentsList.add(ContinentsServer(code, name))
        }
        return continentsList
    }

    fun buildCountriesJson(response: ApolloResponse<CountriesByContinentQuery.Data>): String? {
        return response.data?.continent?.let { continent ->
            JSONObject().apply {
                put("data", JSONObject().apply {
                    put("continents", JSONArray().apply {
                        continent.countries.forEach { country ->
                            put(JSONObject().apply {
                                put("name", country.name)
                                put("code", country.code)
                            })
                        }
                    })
                })
            }.toString()
        }
    }

    fun parseCountriesFromJson(cachedResponse: String): MutableList<Country> {
        val json = JSONObject(cachedResponse)
        val continents = json.getJSONObject("data")
            .getJSONArray("continents")
        val countriesList = mutableListOf<Country>()
        for (i in 0 until continents.length()) {
            val countryJson = continents.getJSONObject(i)
            val name = countryJson.getString("name")
            val code = countryJson.getString("code")
            countriesList.add(Country(name, code))
        }
        return countriesList
    }
}