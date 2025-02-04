/*
 * Copyright 2020-2023 RW-HPS Team and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/RW-HPS/RW-HPS/blob/master/LICENSE
 */
package net.rwhps.server.util.game

import kotlinx.coroutines.*
import net.rwhps.server.func.Control
import net.rwhps.server.game.event.core.AbstractEventCore
import net.rwhps.server.struct.ObjectMap
import net.rwhps.server.struct.Seq
import net.rwhps.server.util.annotations.core.EventAsync
import net.rwhps.server.util.inline.ifNull

/**
 * @author RW-HPS/Dr
 */
class Events {
    private val eventData = ObjectMap<Any, Seq<(Any) -> Any?>>()
    private val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun <T> addEvent(type: Class<T>, listener: (T) -> Unit) {
        addEvent(false, type, listener)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> addEvent(async: Boolean, type: Class<T>, listener: (T) -> Unit) {
        if (async || type.getAnnotation(EventAsync::class.java) != null) {
            eventData[type, { Seq() }].add { value ->
                return@add mainScope.launch {
                    listener(value as T)
                }
            }
        } else {
            eventData[type, { Seq() }].add { value ->
                listener(value as T)
                return@add null
            }
        }
    }

    fun addEvent(type: Any, listener: () -> Unit) {
        eventData[type, { Seq() }].add { listener() }
    }

    fun <T> remove(type: Class<T>) {
        eventData[type, { Seq() }].clear()
    }

    fun <T> fire(type1: Class<*>, type: T) {
        val async = Seq<Job>()

        val eventType = eventData[type as Any]
        eventType?.eachAll { e: (Any) -> Any? -> runAsync(async, e, type) }

        val eventType1 = eventData[type1]
        eventType1?.eachAll { e: (Any) -> Any? -> runAsync(async, e, type) }

        runBlocking {
            async.forEach {
                it.join()
            }
        }
    }

    private fun <T> runAsync(async: Seq<Job>, e: (T) -> Any?, type: T) {
        if (type is AbstractEventCore) {
            if (type.status() == Control.EventNext.CONTINUE) {
                e(type).ifNull({
                    if (it is Job) {
                        async.add(it)
                    }
                })
            }
        }
    }
}