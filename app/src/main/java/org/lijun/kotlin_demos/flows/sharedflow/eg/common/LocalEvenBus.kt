package org.lijun.kotlin_demos.flows.sharedflow.eg.common


import kotlinx.coroutines.flow.MutableSharedFlow

object LocalEvenBus {
    val events = MutableSharedFlow<Event>()
    suspend fun postEvent(event: Event) {
        events.emit(event)
    }
}

data class Event(val timeStamp: Long)