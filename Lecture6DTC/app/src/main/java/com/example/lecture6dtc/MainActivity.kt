package com.example.lecture6dtc

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MyBoxData(val color:Color, val size:Int)

val boxDataList = listOf(
    MyBoxData(Color(0xFF00BCD4), 150),
    MyBoxData(Color(0xFF8BC34A), 250),
    MyBoxData(Color(0xFFFF9800), 350),
    MyBoxData(Color(0xDFF78FBF), 450),

    )

// Main activity: the entry point of the app
// We also have a main function, but we don't use it. It is hidden.

class MainActivity : ComponentActivity() {
    // CREATED STATE - onCreate  is called
    // We have 6 states: CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // startup logic
        // init logic
        // UI
        Log.i("Activity Lifecycle", "onCreate")
        setContent {
            // with lazyRow, we can scroll horizontally
            LazyRow{
                // we can use the item function to create a list of items, without needing to create them one by one
                items(boxDataList.size) {
                    Box(
                        modifier = Modifier.size(boxDataList[it].size.dp).background(boxDataList[it].color),
                    )
                }
//            MyText()
//                item {
//                    Box(
//                        // chaining the modifiers
//                        // Order matters: if padding is after size, the padding will be applied within the size
//                        modifier = Modifier
//                            .size(350.dp)
//                            .background(Color(0xFFFF9800)),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        // we need to arrange the text in a column or row
//                        Row {
//                            // on top of each other
//                            MyText("Hello")
//                            MyText("World")
//                        }
//                    }
//                }
//                item{
//                    Box(modifier = Modifier.size(150.dp).background(Color(0xFF2A7770)),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        // we need to arrange the text in a column or row
//                        Column {
//                            // next to each other
//                            MyText("Hello")
//                            MyText("World")
//                        }
//                    }
//                }
            }
        }
    }

    @Composable
    fun MyText(str:String) {
        Text(str,
            // modifier: to change the style of the text
            modifier = Modifier.background(Color(0xFFB2DFDB)),
            color = Color(0xFF009688),
            fontSize = 20.sp,
            letterSpacing = 2.sp

        )
    }

    // invoked when the UI just became visible to the user
    // Gets called many times, in contrast to onCreate (which is called only once)
    override fun onStart() {
        Log.i("Activity Lifecycle", "onStart")
        super.onStart()
    }

    // RESUME STATE: when the app is in the foreground
    override fun onResume() {
        Log.i("Activity Lifecycle", "onResume")
        super.onResume()
    }

    // Something interrupts the app, like a phone call or a notification, the app is getting to PAUSE and RESUME
    override fun onPause() {
        Log.i("Activity Lifecycle", "onPause")
        super.onPause()
    }

    // When the app is not visible to the user (minimized, another app is in the foreground. Still in the memory)
    override fun onStop() {
        Log.i("Activity Lifecycle", "onStop")
        super.onStop()
    }

    // We want to save some UI data when the app is in the background
    // So when the app is opened again, we can restore the data
    // We use bundle to save the data
    // before stop or destroy, we save the data with onSaveInstanceState

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("Activity Lifecycle", "onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    // Close the app, the app is not in the memory anymore
    // If I rotate the screen, the activity is destroyed and recreated
    override fun onDestroy() {
        Log.i("Activity Lifecycle", "onDestroy")
        super.onDestroy()
    }
}

// Imperative approach of creating UI: we have to write a lot of code
// Declarative approach: we just have to describe the UI
// We can do everything in Kotlin, not in the XML
