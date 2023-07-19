package org.lijun.kotlin_demos.reflect

import kotlin.reflect.jvm.javaConstructor

class ConstructorReference(var name: String = "unknown")

fun test(factory: (String) -> ConstructorReference) {
    val instance: ConstructorReference = factory("Kotlin")
    println(instance.name)
}

fun main(args: Array<String>) {
    test(::ConstructorReference)
    println(::ConstructorReference.javaConstructor)
}


