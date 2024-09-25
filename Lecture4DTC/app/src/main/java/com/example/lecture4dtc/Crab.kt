package com.example.lecture4dtc

class Crab(name:String, age: Int, occupation: String) : Species(name, age, occupation), SeaCreature {
    override var hobby: String
        get() = "Manager"
        set(value) {}

    override fun displayCartoonFact() {
        println("He has a daughter that is a whale")
    }

    override fun displaySeaCreatureFact() {
        println("A crab has hard shell")
    }
}