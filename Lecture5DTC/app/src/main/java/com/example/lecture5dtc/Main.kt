package com.example.lecture5dtc

import kotlin.properties.Delegates

// Delegation: Delegation is a design pattern that allows object composition to achieve code reuse.
// Sometimes it's called interface delegation, as we achieve code reuse by implementing an interface in Kotlin

fun main() {
    val cafe = BikiniBottomCafe(KrustyKrab())
    cafe.processOrder()
    cafe.calculateFoodInventory()
    cafe.processOnlineOrder()

    // lazy delegation: only initialized when it's accessed
    val lazyVal:String by lazy{"hello"}

    println(lazyVal)
    // it's less expensive to use lazy delegation than to initialize the variable right away
    println(lazyVal)

    val reg = RegularClass()
    // init only called once
    reg.heavy
    reg.heavy
    reg.heavy

    val sponge = Sponge()
    sponge.name = "Patrick"
    sponge.name = "Squidward"

    sponge.height = 6
    println(sponge.height)
    sponge.height = 4
    println(sponge.height)

    val list = listOf(1,2,3,4,5)
    val customSortedList = IntListUtils(list).sortElementFirst(5)
    println(customSortedList)

    val species = Species2("Spongebob", 20, "Fry Cook")
    val species2 = species.copy(age = 3)
    println(species2)

    // scope functions: with, let, run, apply, also. They are used to execute a block of code on an object

    // with
    with(Car("ford", "blue", 100)){
        drive(100)
        paint("red")
    }

    // use of let and it: let is a function that takes a lambda and applies it to the object
    var car = Car("ford", "blue", 100).let{
        it.drive(100)
        it.paint("red")
        it
    }

    // let statement is recommended to be used with nullable objects
    val car2:Car?=  null
    car2?.let{
        car = car2
    }

    // run is similar to let, but we can access the object using this
    val car3 = Car("ford", "blue", 100).run{
        drive(100)
        paint("red")
        this
    }

    // apply is similar to run, but it returns the object itself
    val car4 = Car("ford", "blue", 100).apply{
        drive(100)
        paint("red")
    }

    // also is similar to let, but it returns the object itself
    val car5 = Car("ford", "blue", 100).also{
        it.drive(100)
        it.paint("red")
    }


}

class IntListUtils(private val data:List<Int>) {
    fun sortElementFirst(element:Int):List<Int> {
        val list = data.toMutableList()
        val index = list.indexOf(element)

        return if (index != -1) {
            list.removeAt(index)
            list.add(0, element)
            list
        } else {
            println("Element not found")
            list
        }
    }
}

class Car(private var make : String? = null,
        private var color : String? = null,
        private var kilometers: Int = 0) {
    fun drive (k: Int) {
        kilometers += k
        println("Drove $k kilometers")
    }
    fun paint (c: String) {
        color = c
        println("Painted the car $c")
    }

    override fun toString(): String {
        return "Car(make=$make, color=$color, kilometers=$kilometers)"
    }
}

// data class: a class that only holds data

data class Species2 (var name:String, var age:Int, var occupation:String) {
    // data class automatically generates the following functions:
    // toString(), equals(), hashCode(), copy()
    // we can override them if we want
    // we can also have custom functions

}

class Species (var name:String) {
    override fun toString(): String {
        return "Species(name='$name')"
    }

    override fun equals (other:Any?):Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Species

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

class Sponge {
    // observable delegation: observe changes to the variable and do something
    var name:String by Delegates.observable("Bob"){_,old,new->
        println("old value: $old, new value: $new")
    }

    // the vetoable delegation is used to veto the change of a variable
    // if the boolean returned from the lambda is false, the change is vetoed
    // if the boolean returned from the lambda is true, the change is allowed
    var height:Int by Delegates.vetoable(5){_,_,new->
        new > 5
    }
}


class HeavyClass {
    init {
        println("Heavy class initialized")
    }
}


class RegularClass {
    // Only initialized when it's accessed
    val heavy by lazy{
        HeavyClass()
    }
}