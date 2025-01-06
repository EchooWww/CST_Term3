package com.example.lecturedtc.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

data class Episode(
    val code: String,
    val season: Int,
    val no_in_season: Int,
    val title: String,
    val synopsis: String,
    val image_url: String,
    val netflix_url: String
)

class EpisodeApi {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun getRandomEpisode(): Episode {
        return client.get("https://friends-episodes-api.vercel.app/randomize").body()
    }
}
