package org.lijun.kotlin_demos.flows.stateflow

import kotlinx.coroutines.flow.MutableStateFlow

class StateFlowDemo {

    val numStateFlow = MutableStateFlow(0)

    fun increment() {
        numStateFlow.value++
    }

    fun decrement() {
        numStateFlow.value--
    }
}

fun main(){
    val  stateFlowDemo = StateFlowDemo()
    stateFlowDemo.increment()
    println(stateFlowDemo.numStateFlow.value)
    stateFlowDemo.decrement()
    stateFlowDemo.decrement()
    println(stateFlowDemo.numStateFlow.value)
}