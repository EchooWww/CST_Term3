package com.example.lecture8dtc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Hippo(val name: String, val imageId: Int)

@Composable
fun Home(navController: NavController){
    val hippos = listOf(
        Hippo("Sally", R.drawable.hippo1) ,
        Hippo("Freed", R.drawable.hippo2),
        Hippo("Ana", R.drawable.hippo3),
        Hippo("Shelly", R.drawable.hippo4),
        Hippo("Fikl", R.drawable.hippo5),
        Hippo("Patril", R.drawable.hippo6),
        Hippo("Ella", R.drawable.hippo7),
        Hippo("Sarah", R.drawable.hippo8),
        Hippo("Star", R.drawable.hippo9),

        )

    val hippoStateList = remember{
        hippos.toMutableStateList()
    }

    val removeItemCallback:(Hippo) -> Unit = {
        hippoStateList.remove(it)
    }

    LazyColumn {
        items(hippoStateList.size){
            HippoItem(
                navController,
                hippoStateList[it],
                removeItemCallback
            )
        }
    }
}

@Composable
fun HippoItem(
    navController: NavController,
    hippo: Hippo,
    removeItemCallback: (Hippo) -> Unit)
{

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
        .height(100.dp)
        .clickable {
            navController.navigate(
                "details/${hippo.name}/${hippo.imageId}"
            )

        }
    ){
        Row(modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            Text(hippo.name, fontSize = 30.sp)
            IconButton(onClick ={
                removeItemCallback(hippo)
            }){
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp))
            }
        }

    }
}