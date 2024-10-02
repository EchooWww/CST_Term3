package com.example.lab4echowang

import kotlin.random.Random

class Gather(minion: Minion, item: Item?, companion: Companion?): Mission(minion,item, companion){
    override fun determineMissionTime(): Int {
        return (this.minion.backpackSize + this.minion.baseSpeed + this.item!!.timeModifier)* Random.nextInt(0,5)
    }

    override fun reward(amount: Int): String {
        var reward = "nothing"
        if (amount in 10..21) reward = "bronze"
        if (amount in 22..33) reward = "silver"
        if (amount in 34..44) reward = "gold"
        if (amount in 45..60) reward = "diamond"
        return reward
    }
}