package com.example.lab4echowang

import kotlin.random.Random

class Gather(override val minion: Minion): Mission(){
    override fun determineMissionTime(): Int {
        return (this.minion.backpackSize + this.minion.baseSpeed)* Random.nextInt(0,5)
    }

    override fun reward(amount: Int): String {
        var reward = "nothing"
        if (amount in 10..21) reward = "bronze"
        if (amount in 22..33) reward = "silver"
        if (amount in 34..45) reward = "gold"
        return reward
    }
}