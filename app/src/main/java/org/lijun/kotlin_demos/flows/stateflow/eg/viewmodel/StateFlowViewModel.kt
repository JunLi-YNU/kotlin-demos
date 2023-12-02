package org.lijun.kotlin_demos.flows.stateflow.eg.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class StateFlowViewModel(application: Application) : AndroidViewModel(application) {

    val numberSateFlow = MutableStateFlow(0)
    fun increment() {
        numberSateFlow.value++
    }

    fun decrement() {
        numberSateFlow.value--
    }
}