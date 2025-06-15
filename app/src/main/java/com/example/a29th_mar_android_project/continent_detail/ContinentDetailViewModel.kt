package com.example.a29th_mar_android_project.continent_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.okHttpClient
import com.example.a29th_mar_android_project.ContinentQuery
import com.example.a29th_mar_android_project.continent_detail.repo.ContinentRepo
import com.example.a29th_mar_android_project.countries.repo.CountriesRepo
import com.example.a29th_mar_android_project.network.base.LoggingInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

class ContinentDetailViewModel(private val continentRepo: ContinentRepo) : ViewModel(){
    private val _progressBarVisibility = MutableLiveData<Boolean>();
    val progressBarVisibility = _progressBarVisibility;

    //TODO- Difference between MutableLiveData and LiveData
    private val _continentsLiveData = MutableLiveData<List<ContinentsServer>>()
    val continentsLiveData: MutableLiveData<List<ContinentsServer>> = _continentsLiveData

    fun fetchCountriesInContinent(continentCode: String) {
        handlerProgressBarVisibilityChange(true);
        viewModelScope.launch {
            val countries = continentRepo.getCountriesByContinent("AU")
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
    }



    private fun handlerProgressBarVisibilityChange(boolean: Boolean){
        _progressBarVisibility.value = boolean;
    }

}