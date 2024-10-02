package com.example.lab4echowang

import kotlin.properties.Delegates
import kotlin.random.Random

class Hunt(minion: Minion,item: Item?,companion: Companion?): Mission(minion,item, companion), Repeatable {
    override var repeatNum: Int by Delegates.vetoable(3) { _, _, newValue ->
        if (newValue <= 3) {
            true
        } else {
            println("A minion cannot repeat a hunt more than 3 times! Repeating a hunt 3 times...")
            false
        }
    }
    override fun determineMissionTime(): Int {
        return (this.minion.baseHealth + this.minion.baseSpeed +  (this.item?.timeModifier ?: 0))* Random.nextInt(0,5)
    }

    override fun reward(amount: Int): String {
        if (this.companion != null) return this.companion.huntReward(amount)

        if (amount in 11..20) return "a mouse"
        if (amount in 21..30) return "a fox"
        if (amount in 31..40) return "a buffalo"
        if (amount in 41..60) return "a dinosaur"
        return "nothing"
    }

    override fun repeat(time: Int, missionListener: MissionListener) {
        this.repeatNum = time
        for (i in 1..this.repeatNum) {
            this.start(missionListener)
        }
    }
}