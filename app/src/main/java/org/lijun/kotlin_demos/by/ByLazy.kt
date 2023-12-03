package org.lijun.kotlin_demos.by

fun main() {
    val propertyDefinitionByLazy = PropertyDefinitionByLazy()
    println(propertyDefinitionByLazy.property)
    //延迟初始化已经完成了，可以直接调用
    println(propertyDefinitionByLazy.property)
}

class PropertyDefinitionByLazy{
    val property:String by lazy {
        println("Property definition by lazy.")
        "property"
    }
}