package com.example.carvtask

import kotlin.reflect.KClass

// Could create a singleton and inject where needed using koin depending on the usecase
class PubSub {
    val subscribers: MutableMap<KClass<*>, (Any) -> Unit> = mutableMapOf()
    inline fun <reified T : Any> subscribe(noinline subscriber: (T) -> Unit) {
        subscribers[T::class] = subscriber as (Any) -> Unit
    }

    inline fun <reified T : Any> publish(event: T) {
        subscribers[T::class]?.invoke(event)
    }
}

/*
Or could use an abstract class
abstract class Event {
    open val data: Any? = null
}

class ConnectedStatusEvent(override val data: String) : Event()
class NotificationEvent(override val data: String) : Event()
class TemperatureEvent(override val data: Float) : Event()
class NoDataEvent() : Event()
class SensorEvent(override val data: SensorData) : Event
*/

data class SensorData(val reading: Float, val sensorID: Int)

interface Event {
    val data: Any?
}

class ConnectedStatusEvent(override val data: String) : Event
class NotificationEvent(override val data: String) : Event
class TemperatureEvent(override val data: Float) : Event
class NoDataEvent(override val data: Any? = null) : Event
class SensorEvent(override val data: SensorData) : Event

fun main() {
    val pubSub = PubSub()
    val connectedStatusEvent = ConnectedStatusEvent("Connecting...")
    val notificationEvent = NotificationEvent("Device Online")
    val temperatureEvent = TemperatureEvent(10f)
    val noDataEvent = NoDataEvent()
    val sensorEvent = SensorEvent(SensorData(reading = 50f, sensorID = 111))

    pubSub.subscribe { event: ConnectedStatusEvent -> println("Device status is: ${event.data}") }
    pubSub.subscribe { event: NotificationEvent -> println("Received notification: ${event.data}") }
    pubSub.subscribe { event: TemperatureEvent -> println("Temperature is: ${event.data}C") }
    pubSub.subscribe<NoDataEvent> { println("Event happened with no data") }
    pubSub.subscribe { event: SensorEvent -> println("Sensor reading is: ${event.data.reading}, sensor ID is: ${event.data.sensorID}") }

    pubSub.publish(connectedStatusEvent)
    pubSub.publish(notificationEvent)
    pubSub.publish(temperatureEvent)
    pubSub.publish(noDataEvent)
    pubSub.publish(sensorEvent)
}



