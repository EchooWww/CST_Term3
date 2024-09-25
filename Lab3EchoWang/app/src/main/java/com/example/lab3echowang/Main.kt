package com.example.lab3echowang

/*
    Echo Wang
    A01347203
 */

fun main() {
    val facts = mutableMapOf(
        "1492" to "Columbus discovers America",
        "1601" to "Shakespeare writes Hamlet",
        "1632" to "Galileo discovered the acceleration of gravity on Earth to be 9.8 m/s",
        "1838" to "Roughly 9.46 trillion km, the light-year is first used as a measurement in astronomy",
        "2020" to "Covid 19 Pandemic"
    )
    println(facts.entries.joinToString("\n") { "${it.key}=${it.value}" })

    println("")

    val printLine1 = fun(line:String):String {
        println(line)
        return line
    }
    printLine1(facts.values.first())

    val printLine2:(String)->Unit = {println(it)}
    printLine2(facts.values.elementAt(1))

    printLine3({println(it)}, facts.values.elementAt(2))

    val printLine4 = {map:Map<String, String>, key:String, function:(Map<String, String>, String)->String? -> println(function(map, key))}
    printLine4(facts, "1838", ::getFact)

    val printFact5 = {map:Map<String, String>, index:Int, function: (Map<String,String>, index:Int)->String -> println(function(map, index))}
    printFact5(facts, 4) { map, index -> map.values.elementAt(index) }
}

fun printLine3(function: (String) -> Unit, line: String) {
    function(line)
}

fun getFact(facts:Map<String, String>, year:String):String? {
    return facts[year]
}
