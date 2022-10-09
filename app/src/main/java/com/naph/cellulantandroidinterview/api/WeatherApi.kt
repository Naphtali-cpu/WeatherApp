package com.naph.cellulantandroidinterview.api

import com.naph.cellulantandroidinterview.models.WeatherResponse
import com.naph.cellulantandroidinterview.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getSearchedData(
        @Query("q") q: String,
        @Query("APPID") APPID: String = API_KEY
    ): Response<WeatherResponse>
}