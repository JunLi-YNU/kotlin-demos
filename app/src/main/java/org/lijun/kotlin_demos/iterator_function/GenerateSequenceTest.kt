package org.lijun.kotlin_demos.iterator_function

fun Int.isPrime(): Boolean {
    (2 until this).map {
        if(this % it  == 0)
            return false
    }
    return true
}

fun main() {
    val oneThousandPrimes = generateSequence(0) { value -> value+1 }.filter { it.isPrime() }.take(1000)
    println(oneThousandPrimes.toList())
}