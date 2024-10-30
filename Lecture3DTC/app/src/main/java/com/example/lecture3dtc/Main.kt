package com.example.lecture3dtc

// File-level function in Kotlin: functions can exist outside a class
fun main() {
    // Named arguments
    greet(arg3 = "star", arg1 = "spongebob", arg2 = 3)
    greet2() // Using default arguments

    // Using pairs and triples
    println(twoValues("spongebob", 3).first)
    println(threeValues("spongebob", 3, true).second)
    print(threeValues("spongebob", 3, true).third)

    // Destructuring a Triple
    val (name, age, isStudent) = threeValues("spongebob", 3, true)

    // Maps in Kotlin
    // - mapOf returns a read-only map; keys need to be unique
    val map = mapOf(
        "key1" to "value1",
        "key2" to "value2",
        "key3" to "value4"  // Replaces previous value for "key3"
    )
    println(map["key1"])  // Access a value by key
    println(map.keys)     // Print all keys
    println(map.values)   // Print all values

    // Mutable maps
    val map1 = mutableMapOf(
        "key1" to "value1",
        "key2" to "value2",
        "key3" to "value3"
    )
    map1["key3"] = "value4"  // Update value
    map1["key4"] = "value4"  // Add new key-value pair
    println(map1["key3"])

    // HashMap: unordered, but faster due to hashing
    val hashMap1 = hashMapOf(
        "key1" to "value1",
        "key2" to "value2",
        "key3" to "value3"
    )
    hashMap1["key4"] = "value4"
    println(map1)
    println(hashMap1)

    // Sets in Kotlin: unordered collection with unique elements
    val set = setOf(1, 2, 3, 4, 5, 5)
    println(set)

    // Converting a list to a set to remove duplicates
    val list = listOf(1, 2, 3, 4, 5, 5)
    println(list.toSet())

    // Accessing elements in a set (no indexing)
    println(set.elementAt(0))

    // Mutable sets
    val mutableSet = mutableSetOf(1, 2, 3, 4, 5)
    mutableSet.add(6)     // Add element
    mutableSet.remove(1)  // Remove element
    println(mutableSet)

    // First-class functions: storing functions in variables
    val greet = greet("spongebob", 3, "star")

    // Anonymous function: function literal with no name
    val greetAnonymous = fun(arg1: String, arg2: Int, arg3: String) {
        println("$arg1 has $arg2 friends, one of them is $arg3")
    }

    // Lambda functions: concise anonymous function
    val greetLambda: (String, Int, String) -> Unit = { arg1, arg2, arg3 ->
        println("$arg1 has $arg2 friends, one of them is $arg3")
    }

    // Lambda with a single parameter: `it` represents the parameter
    val double: (String) -> Int = { it.toInt() * 2 }

    // Passing functions as arguments
    greet0 { println("Goodbye") }

    // Trailing lambda syntax: lambda outside parentheses
    greet1("spongebob") { println("Goodbye") }

    // Higher-order function: function taking another function as a parameter
    greeet("Hello") { species, friends -> println("$species has $friends friends") }

    // Storing lambda in a variable and passing it
    val fact1 = { species: String, friends: Int -> println("$species has $friends friends") }
    greeet("Hello", fact1)

    // Passing named functions as arguments using `::`
    greeet("Hello", ::fact)
}

// Regular function with named arguments
fun greet(arg1: String, arg2: Int, arg3: String) {
    println("$arg1 has $arg2 friends, one of them is $arg3")
}

// Function with default arguments
fun greet2(arg1: String = "spongebob", arg2: Int = 3, arg3: String = "star") {
    println("$arg1 has $arg2 friends, one of them is $arg3")
}

// Higher-order function that accepts a lambda
fun greeet(greeting: String, fact: (String, Int) -> Unit) {
    println(greeting)
    fact("spongebob", 3)
}

// Function with default argument as a function literal
fun greet0(function: () -> Unit = {}) {
    println("Hello")
    function()
}

// Function taking a lambda as the second argument
fun greet1(arg1: String, arg2: () -> Unit) {
    println(arg1)
    arg2()
}

// Single-expression function
fun double1(arg: Int) = arg * 2

// Function returning a Pair
fun twoValues(name: String, age: Int): Pair<String, Int> {
    return Pair(name, age)
}

// Function returning a Triple
fun threeValues(name: String, age: Int, isStudent: Boolean): Triple<String, Int, Boolean> {
    return Triple(name, age, isStudent)
}

// A fact function passed to `greeet`
fun fact(species: String, friends: Int) {
    println("$species has $friends friends, one of them is")
}