package com.example.lab4echowang

abstract class Mission(val minion: Minion, val item: Item? = null, val companion: Companion?=null) {
    fun start(missionListener: MissionListener) {
        missionListener.missionStart(this.minion)
        missionListener.missionProgress()
        missionListener.missionComplete(this.minion, this.reward(this.determineMissionTime()))
    }
    abstract fun determineMissionTime():Int
    abstract fun reward(amount:Int):String
}


