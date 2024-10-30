fun main() {
    println( "%s is %s years old".format("Alice",20))
    val name = "Jerry"
    val name2 = String("Jerry".toCharArray())
    println(name === name2)
    val species = "C"
    val res = when(species) {
        "C" -> "cat"
        "D" -> "Dog"
        "F" -> "Fish"
        else -> "Unknown"
    }
    val isSponge:Boolean? = null
    val str = if (isSponge==true) "Srponge" else "Not Sponge"

    val fact1:(String) -> Unit = {name -> println("$name is a sponge")}
    val strings = listOf("sponge","bob", "")
    for(str in strings) {
        if (str.startsWith("s")) {
            println(str)
        }
    }

    for (i in 3 downTo -3 step 2) {
        println(i)
    }

}

fun twoValues(): Pair<String, String> {
    return "sponge" to "bob"
}

