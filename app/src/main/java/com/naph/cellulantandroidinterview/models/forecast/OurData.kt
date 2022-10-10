package com.naph.cellulantandroidinterview.models.forecast

data class OurData(
    val dt: Long,
    val clouds: Clouds,
    val dt_txt: String,
    val main: Main,
    val pop: Int,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
