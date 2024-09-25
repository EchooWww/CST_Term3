package com.example.lab2echowang

import kotlin.math.max

/*
    Echo Wang
    A01347203
 */

fun main() {
    val words = arrayOf("A", "Raymond", "Shino", "Stitches", "Bob","Molly","Zucker","Sherb","Ankha","Refrigerator")
    val lengths = mutableListOf<Int>()
    words.forEach {
        lengths.add(it.length)
    }
    val longestWords = mutableListOf<String>()
    val shortestWords = mutableListOf<String>()
    for (word in words) if (word.length == lengths.max()) longestWords.add(word)
    var index = 0
    while(index < words.size) {
        if (words[index++].length != lengths.min()) continue
        shortestWords.add(words[index - 1])
    }
    println("Words: ${words.contentToString()} \nWord lengths: [${lengths.joinToString()}] \nLongest word(s): [${longestWords.joinToString()}] \nShortest word(s): [${shortestWords.joinToString()}]")
}