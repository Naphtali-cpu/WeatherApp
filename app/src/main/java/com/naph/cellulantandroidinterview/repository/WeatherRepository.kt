package com.naph.cellulantandroidinterview.repository

import com.naph.cellulantandroidinterview.api.RetrofitInstance
import com.naph.cellulantandroidinterview.api.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: WeatherApi
) {
    suspend fun getWeather() = apiService.getSearchedData()

//    suspend fun getSearchedWeatherData(searchQuery: String) =
//        RetrofitInstance.api.getSearchedData(searchQuery)
}