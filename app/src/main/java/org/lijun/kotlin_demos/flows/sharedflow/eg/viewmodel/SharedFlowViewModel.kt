package org.lijun.kotlin_demos.flows.sharedflow.eg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.lijun.kotlin_demos.flows.sharedflow.eg.common.Event
import org.lijun.kotlin_demos.flows.sharedflow.eg.common.LocalEvenBus

class SharedFlowViewModel : ViewModel() {
    private lateinit var job: Job
    fun startedRefresh() {
        job = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                LocalEvenBus.postEvent(Event(System.currentTimeMillis()))
            }
        }
    }

    fun stopRefresh() {
        job.cancel()
    }
}