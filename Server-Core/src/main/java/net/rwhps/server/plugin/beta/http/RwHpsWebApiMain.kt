/*
 * Copyright 2020-2023 RW-HPS Team and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/RW-HPS/RW-HPS/blob/master/LICENSE
 */

package net.rwhps.server.plugin.beta.http

import net.rwhps.server.game.event.EventGlobalManage
import net.rwhps.server.game.event.EventManage
import net.rwhps.server.data.bean.BeanPluginInfo
import net.rwhps.server.data.global.Data
import net.rwhps.server.game.event.core.EventListenerHost
import net.rwhps.server.game.event.global.ServerConsolePrintEvent
import net.rwhps.server.plugin.Plugin
import net.rwhps.server.plugin.beta.http.data.GetData
import net.rwhps.server.plugin.beta.http.data.HttpApiEvent
import net.rwhps.server.util.annotations.core.EventListenerHandler

/**
 * @date  2023/6/27 11:11
 * @author  RW-HPS/Dr
 */
class RwHpsWebApiMain: Plugin() {

    override fun onEnable() {
        val mc = MessageForwardingCenter()
        val auth = Authentication(mc)
        auth.registerAuthenticationCenter()
    }

    override fun registerEvents(eventManage: EventManage) {
        eventManage.registerListener(HttpApiEvent())
    }

    override fun registerGlobalEvents(eventManage: EventGlobalManage) {
        eventManage.registerListener(object: EventListenerHost {
            @EventListenerHandler
            fun registerServerConsolePrintEvent(serverConsolePrintEvent: ServerConsolePrintEvent) {
                GetData.consoleCache.addSeq(serverConsolePrintEvent.print)
                GetData.agentConsoleLog.values.forEach {
                    it(serverConsolePrintEvent.print)
                }
            }
        })
    }

    companion object {
        val pluginInfo = BeanPluginInfo(
                name = "RW-HPS API",
                author = "RW-HPS/Dr",
                description = "API interface for RW-HPS",
                version = "0.0.2",
                supportedVersions = "= ${Data.SERVER_CORE_VERSION}"
        )

        const val name = "HttpApi"
        const val cookieName = "HttpApi-Authentication"
    }
}