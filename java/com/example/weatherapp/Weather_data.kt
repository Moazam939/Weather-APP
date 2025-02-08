package com.example.weatherapp

data class Weather_data(
    val name : String,
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp : String,
    val humidity : Int
)

data class Weather(
    val description : String
)
