package org.lijun.kotlin_demos.functional_programming

fun main(){
    val result = listOf<String>("Jack","Jimmy","Rose","Tom").filter { it.contains("J") }
    println(result)
}