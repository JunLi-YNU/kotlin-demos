package org.lijun.kotlin_demos.functional_programming

fun  main(){
    val result = listOf(listOf<Int>(1,2,3), listOf<Int>(4,5,6)).flatMap { it+7 }
    val resultNew = listOf(listOf<Int>(1,2,3), listOf<Int>(4,5,6)).flatten()
    println(result)
    println(resultNew)
}