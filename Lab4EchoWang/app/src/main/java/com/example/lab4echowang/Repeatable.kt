package com.example.lab4echowang

interface Repeatable{
    abstract var repeatNum : Int
    fun repeat(time:Int, missionLister: MissionListener)
}

