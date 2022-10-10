package com.naph.cellulantandroidinterview.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather_item")
data class WeatherItem(
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)