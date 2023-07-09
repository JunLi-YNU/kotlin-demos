package org.lijun.kotlin_demos.functional_programming

fun main(){
    val animals = listOf<String>("zebra","giraffe","elephant","rat")
    val babies = animals.map { animal -> "A baby $animal" }
        .map { baby -> "$baby ,with the cutest little tail ever!" }
    println(animals)
    println(babies)
}