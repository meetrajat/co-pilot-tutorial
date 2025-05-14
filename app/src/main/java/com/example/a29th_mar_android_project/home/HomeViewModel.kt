package com.example.a29th_mar_android_project.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _buttonClickEvent = MutableLiveData<Int>()
    val buttonClick = _buttonClickEvent

    private val _movementToContinentDetail = MutableLiveData<Int>()
    val movementToContinentDetail = _movementToContinentDetail
    //TODO : How to make this method more optimized or do we need this method
    fun validateClickAction(buttonId: Int){
        return when(buttonId){
            1001 -> _buttonClickEvent.value = 1001
            1002 -> _buttonClickEvent.value = 1002
            1003 -> _buttonClickEvent.value = 1003
            1004 -> _buttonClickEvent.value = 1004
            1005 -> _buttonClickEvent.value = 1005
            1006 -> _buttonClickEvent.value = 1006
            else -> {}
        }
    }

    fun validateMovementToContinentDetail(continentId: Int){
        _movementToContinentDetail.value = 1;
    }
}