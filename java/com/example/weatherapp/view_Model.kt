package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class view_Model() : ViewModel() {
    private val _weather = MutableStateFlow<Weather_data?>(null)
    val weather : StateFlow<Weather_data?> = _weather

    val weather_interface = Retrofit_instance.weather_api()

    fun fetch_data(name : String, apiKey : String){
        viewModelScope.launch {
            try {
                val response = weather_interface.get_weather(name, apiKey)
                _weather.value = response
            }catch (e :Exception){
                e.printStackTrace()
            }
        }
    }
}