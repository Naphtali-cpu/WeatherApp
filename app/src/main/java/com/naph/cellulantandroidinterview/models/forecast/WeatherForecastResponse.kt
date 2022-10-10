package com.naph.cellulantandroidinterview.models.forecast

data class WeatherForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<OurData>,
    val message: Int
)