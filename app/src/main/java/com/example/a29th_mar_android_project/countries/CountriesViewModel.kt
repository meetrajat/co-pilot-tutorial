package com.example.a29th_mar_android_project.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.a29th_mar_android_project.countries.repo.CountriesRepo
import com.example.a29th_mar_android_project.countries.uidata.Country
import kotlinx.coroutines.launch

class CountriesViewModel(private val countriesRepo: CountriesRepo) : ViewModel() {

    private val _countriesLiveData = MutableLiveData<List<Country>>()
    val countriesLiveData: MutableLiveData<List<Country>> get() = _countriesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun fetchCountriesByContinent(continentCode: String) {
        viewModelScope.launch {
            try {
                val response = countriesRepo.getCountriesByContinent(continentCode)
                _countriesLiveData.postValue(response)
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }
}