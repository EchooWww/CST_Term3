package com.example.lab7echowang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

data class AnimalCrossing(val name: String, val imageId: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val names = stringArrayResource(id = R.array.first) +
                    stringArrayResource(id = R.array.second) +
                    stringArrayResource(id = R.array.third)

            val images = listOf(
                R.drawable.blathers, R.drawable.celeste, R.drawable.isabelle,
                R.drawable.kappn, R.drawable.kicks, R.drawable.pascal,
                R.drawable.redd, R.drawable.timmytommy, R.drawable.timnook
            )

            var allCards by remember {
                mutableStateOf(
                    names.mapIndexed { index, name ->
                        AnimalCrossing(name, images[index])
                    }
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Button(
                    onClick = {
                        allCards = allCards.shuffled()
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Shuffle", fontSize = 20.sp)
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val numberOfRows = (allCards.size + 2) / 3

                    items(numberOfRows) { rowIndex ->
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            val startIndex = rowIndex * 3
                            val rowCards = allCards.subList(
                                startIndex,
                                minOf(startIndex + 3, allCards.size)
                            )

                            items(rowCards.size) { index ->
                                AnimalCrossingCard(rowCards[index])
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimalCrossingCard(animalCrossing: AnimalCrossing) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(300.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .height(if (isExpanded) 450.dp else 300.dp)
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!isExpanded) {
                Text(
                    text = animalCrossing.name,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Image(
                painter = painterResource(id = animalCrossing.imageId),
                contentDescription = animalCrossing.name,
                modifier = Modifier.size(if (isExpanded) 300.dp else 200.dp)
            )
        }
    }
}