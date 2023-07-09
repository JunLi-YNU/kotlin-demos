package org.lijun.kotlin_demos.functional_programming

fun main() {
    val numbers = listOf<Int>(7, 4, 8, 4, 3, 22, 18, 11)
    val primes = numbers.filter { number -> (2 until number).map { number % it }.none { it == 0 } }
    println(primes)
}