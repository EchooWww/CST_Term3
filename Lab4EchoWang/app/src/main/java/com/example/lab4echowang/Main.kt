package com.example.lab4echowang

fun main() {
    val anonymous = object : MissionListener {
        override fun missionStart(minion: Minion) {
            println(minion.catchphrase)
            val article = if (minion.race == "Elf") "An" else "A"
            println("$article ${minion.race} started a new hunt!")
        }

        override fun missionProgress() {
            println("...\n...\n...")
        }

        override fun missionComplete(minion: Minion, reward: String) {
            val article = if (minion.race == "Elf") "An" else "A"
            println("$article ${minion.race} has returned from a hunt, and found $reward!")
        }

    }
    val orc = Orc (Human())

    val mission = Hunt(orc, null, null)
    mission.repeat(3, anonymous)

    val minion2 = Dwarf()
    val mission2 = Hunt(minion2, null, Companion())
    mission2.repeat(4, anonymous)
    }