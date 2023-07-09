package org.lijun.kotlin_demos.functional_programming

fun main() {
    val result = listOf<Int>(1, 2, 3).fold(0) { accumulator, number -> accumulator + (number * 3) }
    println(result)
}