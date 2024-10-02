package com.example.lab4echowang

class Companion {
    fun huntReward(amount:Int):String {
        if (amount in (11..20)) return "a fish"
        if (amount in (21..30)) return "a forest bear"
        if (amount in (31..40)) return "an orc"
        if (amount in (41..60)) return "a troll"
        return "nothing"
    }
}