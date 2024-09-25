package com.example.lecture4dtc

fun doSomething() {
    println(str)
//    println(str1) // error: cannot access str1 coz it's private to the file
}

//val t = species.occupation // error: cannot access occupation coz it's private to the class

// class is by default final in Kotlin
// to make it inheritable, we need to use the open keyword
abstract class Species (var name: String = "", var age: Int = 0,  var occupation: String? = null) {
    abstract var hobby:String
    var height: Int = 0
        get() = field
        private set(value) {
            field = value
        } // we can also set the setter to private to prevent it from being accessed outside the class
    // by default, there are already getters and setters for each property
    // but we can override them if we want
//    var name: String = ""
//        get() = field
//        set(value) {
//            field = value.uppercase()
//        }
//    var age: Int = 0
    // we can also pass in a constructor
//    var job: String = occupation
    // or in the constructor, we set var occupation: String
    // or in init block
//    init {
//        job = occupation
//    }
    // we can also have a secondary constructors
    constructor(name:String) : this(name, 0, null) {
        println(1);
    }
    constructor(name:String, age:Int) : this(name, age, null) {
        println(2);
    }

    // we can have non-abstract members in an abstract class
    // but we cannot instantiate an abstract class
    protected open fun displayBio() {
        println("Name: $name, Age: $age, Occupation: $occupation")
    }

    abstract fun displayCartoonFact()
}