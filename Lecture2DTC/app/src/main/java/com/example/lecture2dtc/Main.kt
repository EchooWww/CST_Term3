package com.example.lecture2dtc

import java.util.*

fun main() {
    var num = 5

    println(num++)
    println(++num)
    println(num--)
    println(--num)

    // print 105
    println("10" + 5)
    // doesn't work if we try to add a string to a number, but we can use toInt() to convert the string to a number
    println(10 + "5".toInt())

    val num1 = 1.23456789
    println(num1)
    println(num1.toFloat())
    println(num1.toInt())

    // arrays
    val species = arrayOf("cat", "dog", "sponge")
    val arr1 = arrayOfNulls<String>(5)
    arr1[0] = "sponge"
    val arr2 = intArrayOf(5,7,9) // this creates a java primitive array
    val arr3 = arrayOf<Int>(5,7,9) // this creates a generic array, we usually use this one in kotlin
    println(species.contentToString())
    println(if ("squirrel" in species) "found" else "not found")
    // we can modify the content (elements) of the array, but we cannot reassign the array if it's a val
    species[0] = "squirrel"
    println(if("squirrel" in species) "found" else "not found")
    // deconstructing an array
    val (a, b, c) = species
    // or
    val(a1,_,_) = species

    // readonly list
    val species2 = listOf("cat", "dog", "sponge")
    println(species2.containsAll(listOf("cat", "dog")))

    // mutable list
    val species3 = mutableListOf("cat", "dog", "sponge")
    species3.add("squirrel")
    species3.remove("dog")
    println(species3) // we can print the list directly
    species3.shuffle()
    println(species3)

    val arr4 = arrayOf<Any?>("sponge", 4, null)
    for(i in arr4.indices) println(arr4[i])

    val animals = listOf("sponge","squirrel","star","crab")
    for (animal in animals) {
        val toUpper = animal.uppercase()
        println(toUpper)
    }
    for (index in animals.indices.reversed()) {
        println("$index: ${animals[index]}")
    }

    for (i in 1..10) println(i)
    for (i in 10 downTo 1) println(i)
    for (i in 100 downTo 1 step 4) println(i)
    animals.forEach{println(it)} // the it keyword is the default name for the lambda parameter
    var name = "spongebob"
    var i = 0
    while (i < name.length) println(name[i++])

    var sum = 0
    for (i in 0..100 step 2) sum += i
    println(sum)

    // input
    println("Enter some text:")
//    val input = readlnOrNull() // the non-nullable version is readln
//    println("You entered: $input")

//    val sc = Scanner(System.`in`)
//    println("Enter a number:")
//    val num2 = sc.nextInt()
//    println("You entered: $num2")

    val arr = arrayOfNulls<String>(5)
    arr[0] = "sponge"
    for (i in arr) {
        val g = i?:"unknown"
        println(g)
    }

    val set = setOf("sponge", "squirrel", "star", "crab")

    val fact1 :(Int)->String ={ it.toString()}
    val species5 = Species("sponge")
    val species6 = Species("sponge")
    println(species5 == species6)

}

 class Species(val name:String, val age:Int) {
    init{
        println("First init block")
    }
    constructor(name:String): this(name,0) {
        println("Secondary constructor")
    }

}



