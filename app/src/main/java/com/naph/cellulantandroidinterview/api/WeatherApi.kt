package com.naph.cellulantandroidinterview.api

import com.naph.cellulantandroidinterview.models.WeatherResponse
import com.naph.cellulantandroidinterview.models.forecast.WeatherForecastResponse
import com.naph.cellulantandroidinterview.util.Constants.Companion.API_KEY
import com.naph.cellulantandroidinterview.util.Constants.Companion.BASE_URL
import com.naph.cellulantandroidinterview.util.Constants.Companion.ID
import com.naph.cellulantandroidinterview.util.Constants.Companion.LATER
import com.naph.cellulantandroidinterview.util.Constants.Companion.TODAY
import com.naph.cellulantandroidinterview.util.Constants.Companion.TOMORROW
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getSearchedData(
        @Query("q") q: String,
        @Query("APPID") APPID: String = API_KEY
    ): Response<WeatherResponse>

//    Today Forecast endpoint

    @GET("forecast")
    suspend fun getTodayWeatherForecast(
        @Query("q") q: String,
        @Query("id") id: String = ID,
        @Query("cnt") cnt: String = TODAY,
        @Query("appid") appid: String = API_KEY
    ): Response<WeatherForecastResponse>

    //    Tomorrow Forecast endpoint

    @GET("forecast")
    suspend fun getTomorrowWeatherForecast(
        @Query("q") q: String,
        @Query("id") id: String = ID,
        @Query("cnt") cnt: String = TOMORROW,
        @Query("appid") appid: String = API_KEY
    ): Response<WeatherForecastResponse>

    //    Later Forecast endpoint

    @GET("forecast")
    suspend fun getLaterWeatherForecast(
        @Query("q") q: String,
        @Query("id") id: String = ID,
        @Query("cnt") cnt: String = LATER,
        @Query("appid") appid: String = API_KEY
    ): Response<WeatherForecastResponse>

    companion object{
        operator fun invoke() : WeatherApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(WeatherApi::class.java)
        }
    }
}