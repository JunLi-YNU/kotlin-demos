package org.lijun.kotlin_demos.by

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