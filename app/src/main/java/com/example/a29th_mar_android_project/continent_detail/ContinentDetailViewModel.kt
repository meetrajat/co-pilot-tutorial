package com.example.a29th_mar_android_project.continent_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.okHttpClient
import com.example.a29th_mar_android_project.ContinentQuery
import com.example.a29th_mar_android_project.network.base.LoggingInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

class ContinentDetailViewModel : ViewModel(){
    private val _progressBarVisibility = MutableLiveData<Boolean>();
    val progressBarVisibility = _progressBarVisibility;

    //TODO- Difference between MutableLiveData and LiveData
    private val _continentsLiveData = MutableLiveData<List<ContinentsServer>>()
    val continentsLiveData: MutableLiveData<List<ContinentsServer>> = _continentsLiveData

    fun fetchCountriesInContinent(continentCode: String) {
        handlerProgressBarVisibilityChange(true);
        viewModelScope.launch {
            val countries = fetchContinents()
            if (countries != null) {
                 // Update your UI with the list of countries
                 println("Fetched ${countries.size} countries")
                _continentsLiveData.value = countries
                 handlerProgressBarVisibilityChange(false);
            } else {
                 // Handle the error (e.g., show a message)
                handlerProgressBarVisibilityChange(false);
                 println("Failed to fetch countries")
            }
        }
      // return TODO("Provide the return value")
    }

    suspend fun fetchContinents(): MutableList<ContinentsServer>? = withContext(Dispatchers.IO) {
        val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .build()
        }
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com/graphql")
            .okHttpClient(okHttpClient)
            .addInterceptor(LoggingInterceptor())
            .build()

        try {
            //val continentCode: Optional<String?> = "EU" // Replace with the actual continent code
            val response = apolloClient.query(ContinentQuery())
                .execute() // Replace with your generated query class
            if (response.hasErrors()) {
                println("GraphQL errors 123: ${response.errors}")
                null
            } else {
               val continents = response.data?.continents ;
                println("GraphQL response: $continents")
                if (continents != null) {
                    var listContinents:MutableList<ContinentsServer> = ArrayList<ContinentsServer>();
                    for ( index in continents){
                        val continent:ContinentsServer;
                        if (index != null && index.name != null) {
                             continent = ContinentsServer("",index.name)
                        }else{
                             continent = ContinentsServer("","")
                        }
                        listContinents.add(continent)
                    }
                    println("listContinents: $listContinents")
                    return@withContext listContinents;
                }
               null
            }
        } catch (e: ApolloException) {
            println("Apollo error: ${e.message}")
            null
        }
    }

    private fun handlerProgressBarVisibilityChange(boolean: Boolean){
        _progressBarVisibility.value = boolean;
    }

}