package com.example.lab4echowang

import kotlin.random.Random

class Hunt(override val minion: Minion): Mission(), Repeatable {
    override fun determineMissionTime(): Int {
        return (this.minion.baseHealth + this.minion.baseSpeed)* Random.nextInt(0,5)
    }

    override fun reward(amount: Int): String {
        var reward = "nothing"
        if (amount in 11..20) reward = "a mouse"
        if (amount in 21..30) reward = "a fox"
        if (amount in 31..50) reward = "a buffalo"
        return reward
    }

    override fun repeat(time: Int, missionListener: MissionListener) {
        for (i in 0..time){
            this.start(missionListener)
        }
    }
}