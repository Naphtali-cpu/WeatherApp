package com.naph.cellulantandroidinterview.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherItem: WeatherItem)

    @Query("SELECT * FROM weather_item")
    fun showAllWeatherData(): LiveData<List<WeatherItem>>
}