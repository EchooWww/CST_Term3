package com.example.lecture4dtc

// file level: by default public, can be accessed everywhere(event from other files)
val str = "Hello, World!"
private val str1 = "Hello, World!" // only accessible within this file

fun main() {
    val anonymousClass = object {
        fun greet() {
            println("Hello, World!")
        }

    }
    val sponge = Sponge("Bob", 14, "Fry Cook")
//    sponge.displayBio() // inherited from Species, when we make it private, it's not accessible
// if we make it protected, it's accessible to the child class, but not directly accessible to the main function
    sponge.displayCartoonFact()
    doSomething()

    val snorlax = Pokemon()
    val snorlaxSleepListener = SnorlaxSleepListener()
    snorlax.sleep(snorlaxSleepListener)

    val jigglyPuff = Pokemon()
    jigglyPuff.sleep(object : PokemonSleepListener {
        override fun startSleeping() {
            println("JigglyPuff is sleeping")
        }

        override fun recoveryProgress(recovered: String) {
            println("JigglyPuff has recovered $recovered")
        }

        override fun wakeUp() {
            println("JigglyPuff has woken up, DANCE!")
        }
    })
}

