package com.naph.cellulantandroidinterview.repository

import com.naph.cellulantandroidinterview.api.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    public val apiService: WeatherApi,
) {
    suspend fun getWeather(q: String) = apiService.getSearchedData(q)

    suspend fun getTodayForecastWeather(q: String) = apiService.getTodayWeatherForecast(q)

    suspend fun getTomorrowForecastWeather(q: String) = apiService.getTomorrowWeatherForecast(q)

    suspend fun getLaterForecastWeather(q: String) = apiService.getLaterWeatherForecast(q)

}