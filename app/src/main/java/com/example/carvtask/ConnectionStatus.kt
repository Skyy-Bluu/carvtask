package com.example.carvtask

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

fun main() {
    var isConnected = true
    Observable.interval(1000L, TimeUnit.MILLISECONDS)
        .concatMap {
            val seconds = it.toInt()
            when {
                seconds % 5 == 0 && seconds % 3 == 0 -> {
                    isConnected = true
//                    println("seconds; $seconds , connection: isConnected")
                    Observable.just(true)
                }
                seconds % 5 == 0 -> {
                    isConnected = true
//                    println("seconds; $seconds , connection: isConnected")
                    Observable.just(true)
                }
                seconds % 3 == 0 -> {
//                    println("seconds; $seconds , connection: isConnected")
                    if (!isConnected) {
//                        println("seconds; $seconds , connection: isConnected")
                        Observable.just(false)
                    } else {
                        isConnected = false
                        Observable.just(true)
                    }
//                    println("seconds; $seconds , connection: isConnected")
                }
                else -> Observable.just(true)
            }
        }
        .subscribe {
            println("Connection status sequence: $it")
        }

    Thread.sleep(15000L)
}