package org.lijun.kotlin_demos.reflect

import kotlin.reflect.full.createInstance

class Item(var name: String) {
    var price = 30.0

    constructor() : this("kotlin")
    constructor(name: String, price: Double) : this() {
        this.price = price
    }
}

fun main(args: Array<String>) {
    val itemClazz = Item::class
    val item = itemClazz.createInstance()
    println("item.name:" + item.name + " item.price:" + item.price)
    itemClazz.constructors.forEach() {
        if (it.parameters.size == 2) {
            val itemSecond = it.call("java", 50.0)
            println("itemSecond.name:" + itemSecond.name + " itemSecond.price:" + itemSecond.price)
        }
    }
}