package com.example.lecture4dtc

// in kotlin, a class can inherit from only one class
// but it can implement multiple interfaces
class Sponge(name:String, age:Int, occupation:String) : Species(name, age, occupation) {
    override var hobby = "Jellyfishing"

    override fun displayCartoonFact() {
//        displayBio() // if displayBio() is private, it's not accessible, but if it's protected, it's accessible to the child class
        println("He lives in a pineapple under the sea")
    }

    override fun displayBio() {
        super.displayBio()
        println("Hobby: $hobby")
    } // we need to open the function in the parent class to override it
}