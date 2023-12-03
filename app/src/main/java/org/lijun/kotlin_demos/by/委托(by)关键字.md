# by 关键字 委托
## 接口委托
```kotlin
interface Subject {
    fun method()
}

class RealSubject : Subject {
    override fun method() {
        println("real subject method.")
    }
}

class ProxySubject : Subject by RealSubject() {
    fun proxyMethod() {
        println("Proxy object real method.")
    }
}

class ProxySubjectSecond(realSubject: RealSubject) : Subject by realSubject {
    fun proxyMethod() {
        println("Proxy object real method.")
    }
}

fun main() {

    println(ProxySubject().method())
    println(ProxySubject().proxyMethod())
    println(ProxySubjectSecond(RealSubject()).method())
    println(ProxySubjectSecond(RealSubject()).proxyMethod())
}
```
## 顶级函数参数委托
```kotlin
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
```
## 类参数委托
```kotlin
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
```
## 延迟加载 将对的实例化委托给lazy函数
```kotlin
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
```
## 监听委托 将对象交给观察进行监听
```kotlin
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
```