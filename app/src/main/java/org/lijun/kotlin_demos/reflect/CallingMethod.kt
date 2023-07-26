package org.lijun.kotlin_demos.reflect

import android.telecom.Call
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredFunctions

class CallingMethod {
    fun test(name: String) {
        println("执行带name参数的test方法,name:${name}")
    }

    fun test(name: String, price: Double) {
        println("执行带name和price参数的test方法,name:${name}，price:${price}")
    }
}

fun main() {
    val callingMethodClazz = CallingMethod::class
    val instance = callingMethodClazz.createInstance()
    val functions = callingMethodClazz.declaredFunctions
    for (f in functions) {
        if (f.parameters.size == 3) {
            f.call(instance, "kotlin", 40)
        }
        if (f.parameters.size == 2) {
            f.call(instance, "Kotlin")
        }
    }
}