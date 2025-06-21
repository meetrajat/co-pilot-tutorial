package com.example.a29th_mar_android_project.continent_detail

import ContinentRepo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ContinentDetailViewModel(private val continentRepo: ContinentRepo) : ViewModel(){
    private val _progressBarVisibility = MutableLiveData<Boolean>();
    val progressBarVisibility = _progressBarVisibility;

    //TODO- Difference between MutableLiveData and LiveData
    private val _continentsLiveData = MutableLiveData<List<ContinentsServer>>()
    val continentsLiveData: MutableLiveData<List<ContinentsServer>> = _continentsLiveData

    fun fetchContinent(continentCode: String) {
        handlerProgressBarVisibilityChange(true);
        viewModelScope.launch {
            val continentList = continentRepo.getContinent(continentCode)
            if (continentList != null) {
                 // Update your UI with the list of countries
                 println("Fetched ${continentList.size} continents")
                _continentsLiveData.value = continentList
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