package org.lijun.kotlin_demos.by

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    var property: String by Delegates.observable("Kotlin") { _, oldValue, newValue ->
        println("OldValue:${oldValue} NewValue:${newValue}")
    }
    property = "Java"
    println(property)

    var myProperty: String by MyDelegates("Java") { oldValue, newValue ->
        println("OldValue:${oldValue} NewValue:${newValue}")
    }
    myProperty = "Kotlin"
    println(myProperty)
}

class MyDelegates(
    private var definitionValue: String = "",
    val onChange: (String, String) -> Unit
) : ReadWriteProperty<Nothing?, String> {
    override fun getValue(thisRef: Nothing?, property: KProperty<*>): String = definitionValue

    override fun setValue(thisRef: Nothing?, property: KProperty<*>, value: String) {
        if (definitionValue == value) return
        onChange(definitionValue, value)
        definitionValue = value
    }
}