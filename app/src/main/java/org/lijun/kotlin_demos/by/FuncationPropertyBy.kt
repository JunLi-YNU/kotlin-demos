package org.lijun.kotlin_demos.by

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    var propertyBy: String by PropertyImpl()
    propertyBy = "Property"
    println(propertyBy)
}

class PropertyImpl : ReadWriteProperty<Nothing?, String> {
    private var property = ""

    override fun getValue(thisRef: Nothing?, property: KProperty<*>): String = this.property

    override fun setValue(thisRef: Nothing?, property: KProperty<*>, value: String) {
        this.property = value
    }

}