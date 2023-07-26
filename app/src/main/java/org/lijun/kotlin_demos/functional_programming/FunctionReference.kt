package org.lijun.kotlin_demos.functional_programming

fun isSmall(i: Int) = i < 5
fun isSmall(str: String) = str.length < 5
fun main() {
    val intList = listOf(20, 30, 50, -30, -20)
    val resultList = intList.filter(::isSmall)
    println(resultList)
    val strList = listOf("java", "kotlin", "javaScript", "go", "groovy")
    val filterList = strList.filter(::isSmall)
    println(filterList)

    val function : (String)->Boolean = ::isSmall
    println(function("java"))

    val toCharArray : (String)->CharArray = String::toCharArray
    println(toCharArray("kotlin").size)
}