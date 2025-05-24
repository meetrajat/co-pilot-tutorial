package com.example.a29th_mar_android_project.countrydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a29th_mar_android_project.countrydetail.repo.CountryDetailRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for CountryDetail screen.
 */
class CountryDetailViewModel(
    private val repository: CountryDetailRepository
) : ViewModel() {

    private val _countryDetail = MutableLiveData<CountryDetail>()
    val countryDetail: LiveData<CountryDetail> = _countryDetail

    fun fetchCountryDetail(code: String) {
        viewModelScope.launch {
            val detail = repository.getCountryDetail(code)
            _countryDetail.value = detail
        }
    }
}