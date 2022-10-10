package com.naph.cellulantandroidinterview.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naph.cellulantandroidinterview.models.WeatherResponse
import com.naph.cellulantandroidinterview.models.forecast.WeatherForecastResponse
import com.naph.cellulantandroidinterview.repository.WeatherRepository
import com.naph.cellulantandroidinterview.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository:  WeatherRepository,
) : ViewModel() {

    private val _response = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse>
        get() = _response

    private val _responseforecast = MutableLiveData<WeatherForecastResponse>()
    val weatherForecastResponse: LiveData<WeatherForecastResponse>
        get() = _responseforecast



    companion object {
        const val ERROR_TAG = "ERR"
        const val INFO_TAG = "INFO"
    }

    fun getWeather(q: String) = viewModelScope.launch {
        repository.getWeather(q).let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body())
            } else {
                Log.d(ERROR_TAG, "getWeather Error: ${response.code()}")
            }
        }
    }

//    Get today forecast function

    fun getTodayForecastWeather(q: String) = viewModelScope.launch {
        repository.getTodayForecastWeather(q).let { response ->
            if (response.isSuccessful) {
                _responseforecast.postValue(response.body())
                Log.i(INFO_TAG, "Our data : ${response.body()}")
            } else {
                Log.d(ERROR_TAG, "getTodayForecastWeather Error: ${response.code()}")
            }
        }
    }

    //    Get tomorrow forecast function

    fun getTomorrowForecastWeather(q: String) = viewModelScope.launch {
        repository.getTomorrowForecastWeather(q).let { response ->
            if (response.isSuccessful) {
                _responseforecast.postValue(response.body())
            } else {
                Log.d(ERROR_TAG, "getTomorrowForecastWeather Error: ${response.code()}")
            }
        }
    }

    //    Get later forecast function

    fun getLaterForecastWeather(q: String) = viewModelScope.launch {
        repository.getLaterForecastWeather(q).let { response ->
            if (response.isSuccessful) {
                _responseforecast.postValue(response.body())
            } else {
                Log.d(ERROR_TAG, "getLaterForecastWeather Error: ${response.code()}")
            }
        }
    }
}