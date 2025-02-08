package com.example.weatherapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Retrofit_instance {

    @GET("weather")

    suspend fun get_weather(
        @Query("q") city : String,
        @Query("appid") apiKey : String,
        @Query("units") units : String = "metric"
    ): Weather_data

    companion object{
        val Base_url = "https://api.openweathermap.org/data/2.5/"

        fun weather_api() : Retrofit_instance{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Base_url)
                .build()

            return retrofit.create(Retrofit_instance::class.java)
        }
    }
}