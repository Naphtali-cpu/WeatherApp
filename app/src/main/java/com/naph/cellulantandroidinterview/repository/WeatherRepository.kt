package com.naph.cellulantandroidinterview.repository

import com.naph.cellulantandroidinterview.api.RetrofitInstance
import com.naph.cellulantandroidinterview.api.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    public val apiService: WeatherApi
) {
    suspend fun getWeather(q: String) = apiService.getSearchedData(q)

//    suspend fun getSearchedWeatherData(searchQuery: String) =
//        RetrofitInstance.api.getSearchedData(searchQuery)
}