package org.lijun.kotlin_demos.reflect

//定义一个注解
annotation class Anno

@Deprecated("该类已经不再推荐使用")
@Anno
@Suppress("UNCHECKED_CAST")
class MyDemoClass(age: Int) {
    var name: String = "kotlin";

    //定义一个私有构造器
    private constructor() : this(28) {}

    //定义一个有参构造器
    constructor(name: String) : this(27) {
        println("执行有参构造器，输出参数：${name}")
    }

    //定义一个无参方法、
    fun info() {
        println("执行无参方法")
    }

    //定义一个有参方法
    fun info(str: String) {
        println("执行无参方法")
    }

    //定义一个嵌套类
    class Inner

    //定义一个扩展方法
    fun MyDemoClass.show() {
        println("一个扩展方法bar()")
    }

    //定义一个扩展属性
    val MyDemoClass.sex: Int
        get() = 1
}