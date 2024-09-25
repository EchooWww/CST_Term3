package com.example.lecture4dtc

interface PokemonSleepListener {
    fun startSleeping()
    fun recoveryProgress(recovered:String)
    fun wakeUp()
}

class SnorlaxSleepListener : PokemonSleepListener {
    override fun startSleeping() {
        println("Snorlax is sleeping")
    }

    override fun recoveryProgress(recovered: String) {
        println("Snorlax has recovered $recovered")
    }

    override fun wakeUp() {
        println("Snorlax has woken up")
    }
}

class Pokemon {
    fun sleep(listener: PokemonSleepListener){
        listener.startSleeping()
        for( i in 1 .. 10) {
            Thread.sleep(700L)
            // display i * 10% recovery progress
            listener.recoveryProgress("${i * 10}%")
        }
        listener.wakeUp()
    }
}