package org.lijun.kotlin_demos.functional_programming

fun main(){
    val names = listOf<String>("Jimmy","lili","Jack")
    val ages = listOf<Int>(12,16,18)
    val employeesMap = names.zip(ages).toMap()
    println(employeesMap["lili"])
}