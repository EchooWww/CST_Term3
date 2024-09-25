package com.example.lab4echowang

interface MissionListener {
    fun missionStart(minion: Minion)
    fun missionProgress()
    fun missionComplete(minion: Minion, reward:String)
}