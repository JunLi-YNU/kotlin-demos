package org.lijun.kotlin_demos.by

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    println("Property".also { PropertyDefinition().property = it })
}

class PropertyDefinition {
    var property: String by PropertyBy
}

object PropertyBy : ReadWriteProperty<PropertyDefinition, String> {
    private var property = ""
    override fun getValue(thisRef: PropertyDefinition, property: KProperty<*>): String {
        return this.property
    }

    override fun setValue(thisRef: PropertyDefinition, property: KProperty<*>, value: String) {
        this.property = value
    }

}
