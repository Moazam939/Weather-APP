package com.example.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.theme.light_blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather App") },
                Modifier.background(light_blue)
            )
        }
    ) { innerPadding ->
        WeatherScreen(modifier = Modifier.padding(innerPadding))
    }
}
@Preview(showSystemUi = true)
@Composable
fun check_preview(modifier: Modifier = Modifier) {
    WeatherScreen()
}



@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
    val view_model : view_Model = viewModel()

    val weather_ = view_model.weather.collectAsState()

    val apiKey = "20b24ea188d4ed19d9de7d687e5e6413"

    val city = rememberSaveable {
        mutableStateOf("")
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 80.dp)){

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier
                .background(color = light_blue)
                .fillMaxWidth()
                .height(300.dp)){
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        OutlinedTextField(value = city.value, onValueChange = {
                            city.value = it
                        },
                            label = { Text(text = "Enter the City")},
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        )
                        Button(onClick = { view_model.fetch_data(city.value, apiKey)},
                            modifier = Modifier.padding(top = 10.dp)) {
                            Text(text = "Search")
                            modifier.padding(10.dp)
                        }
                    }
                    Image(painter = painterResource(id = R.drawable.cloudy),
                        contentDescription = "Cloudy",
                        modifier = Modifier.size(300.dp)
                            .align(Alignment.CenterHorizontally))
                }
            }
            Box(modifier = Modifier
                .padding(2.dp)){
                // for show temperature humidity city and descrition
                Column {
                    weather_.value?.let {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            data_show(label = "City", value = it.name, icon = Icons.Default.Place)
                            data_show(label = "Humadity", value = "${it.main.humidity}%", icon = Icons.Default.Star)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            data_show(label = "Temperature", value = "${ it.main.temp}C", icon = Icons.Default.Warning)
                            data_show(label = "Description", value = it.weather[0].description, icon = Icons.Default.Info)
                        }
                        // reset the value of text field
//                        city.value = ""
                    }
                }
            }
        }

    }

}

@Composable
fun data_show(label : String ,value: String, icon: ImageVector) {
    Box(modifier = Modifier
        .width(180.dp)
        .height(150.dp)
    ){
        Card(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            colors = CardDefaults.cardColors(Color.White)) {

            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Row {
                    Icon(imageVector = icon, contentDescription = null)
//                    Spacer(modifier = Modifier
//                        .height(5.dp)
//                        .padding(5.dp))
                    Text(text = label,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold)
                }
//                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier,
                    contentAlignment = Alignment.Center){
                    Text(text = value,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center)
                }
            }
        }
    }
}