package com.example.mercados.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object Coroutines {

    fun main(work: suspend (()-> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }

    fun<T: Any> ioTheMain(work: suspend (() -> T?), callback: ((T?)->Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt work()
            }.await()

            callback(data)
        }


}
