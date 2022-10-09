package com.naph.cellulantandroidinterview.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naph.cellulantandroidinterview.models.WeatherResponse
import com.naph.cellulantandroidinterview.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository:  WeatherRepository
) : ViewModel() {

    private val _response = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse>
        get() = _response


    init {
        getWeather()
    }

    private fun getWeather() = viewModelScope.launch {
        repository.getWeather().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body())
            } else {
                Log.d("ERRWET", "getWeather Error: ${response.code()}")
            }
        }
    }
}