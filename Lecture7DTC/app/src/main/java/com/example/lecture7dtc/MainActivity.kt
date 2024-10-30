package com.example.lecture7dtc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.benchmark.perfetto.Row
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Card
import com.example.lecture7dtc.ui.theme.Lecture7DTCTheme

data class Cartoons(val name: String, val imageId: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val cartoonNames = stringArrayResource(id = R.array.cartoons)
            val images = listOf(R.drawable.krabs, R.drawable.patrick, R.drawable.squidward, R.drawable.sandy, R.drawable.pearl, R.drawable.spongebob)
            val cartoonList = cartoonNames.mapIndexed { index, name ->
                Cartoons(name, images[index])
            }

            LazyColumn{
                items(cartoonList.size) {
                    CartoonCard(cartoonList[it])
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lecture7DTCTheme {
        Greeting("Android")
    }
}

@Composable
fun CartoonCard(cartoon:Cartoons) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp,color= Color(0xFF000000))
    ){
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        )
    }
}