package org.lijun.kotlin_demos.reflect

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.*

fun main(args: Array<String>) {
    //获取MyClass对应的KClass
    val clazz = MyClass::class
    //通过constructors属性获取KClass对象对应类的全部构造器
    val ctors = clazz.constructors;
    println("MyClass的全部构造器如下,使用forEach()方法:")
    ctors.forEach() { println(it) }
    println("MyClass的构造器如下:")
    println(clazz.primaryConstructor)

    //通过functions属性获取KClass对象对应类的全部方法
    val functions = clazz.functions
    println("MyClass的全部方法如下:")
    functions.forEach { println(it) }

    //通过declaredFunctions属性获取KClass对象本身所声明的全部方法*不包括继承的方法
    val declaredFunctions = clazz.declaredFunctions
    println("MyClass声明的全部方法如下:")
    declaredFunctions.forEach { println(it) }

    //通过declaredMemberFunctions属性获取KClass对象本身所声明的全部成员方法*不包括继承方法
    val declaredMemberFunctions = clazz.declaredMemberFunctions
    println("MyClass声明的全部成员方法如下:")
    declaredMemberFunctions.forEach { println(it) }

    //通过memberExtensionFunctions属性获取LKClass对象所代表的全部扩展方法*不包括继承方法
    val memberExtensionFunctions = clazz.memberExtensionFunctions
    println("MyClass的全部扩展方法:")
    memberExtensionFunctions.forEach { println(it) }

    //通过declareMemberProperties属性获取KClass对象所声明的全部成员属性
    val declaredMemberProperties = clazz.declaredMemberFunctions
    println("MyClass声明的全部成员属性:")
    declaredMemberProperties.forEach() { println(it) }

    //通过memberExtensionProperties属性获取LClass对象所代表的全部扩展属性
    val memberExtensionProperties = clazz.memberExtensionProperties
    println("MyClass声明的全部扩展属性:")
    memberExtensionProperties.forEach { println(it) }

    //通过annotation属性获取KClass对象对应的全部注解
    val annotations = clazz.annotations
    println("MyClass的全部注解如下:")
    annotations.forEach { println(it) }

    //通过nestedClasses属性获取KClass对象对应的全部嵌套类
    val nestedClasses = clazz.nestedClasses
    println("MyClass的全部嵌套类")
    nestedClasses.forEach { println(it) }

    //通过supertypes属性获取该类对应的全部父类*包括父类和父接口
    val supertypes = clazz.supertypes
    println("MyClass的父类和父接口如下:")
    supertypes.forEach { println(it) }

    val instance = clazz.createInstance()
    val property = clazz.declaredMemberProperties
    property.forEach {
        when (it.name) {
            "name" -> {
                @Suppress("UNCHECKED_CAST")
                val mp = it as KMutableProperty1<MyClass, Any>
                mp.set(instance, "java")
                println(it.get(instance))
            }
            "price" -> {
                println(it.get(instance))
            }
        }
    }
}