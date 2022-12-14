/*
 * Copyright 2020-2022 RW-HPS Team and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/RW-HPS/RW-HPS/blob/master/LICENSE
 */

package cn.rwhps.server.dependent.redirections.lwjgl

import cn.rwhps.asm.api.Redirection
import java.nio.ByteBuffer

enum class MemASCIIRedirection : Redirection {
    INSTANCE;

    @Throws(Throwable::class)
    override fun invoke(obj: Any, desc: String, type: Class<*>?, vararg args: Any): Any {
        val buffer = args[0] as ByteBuffer
        val length = args[1] as Int
        val sb = StringBuilder(length)
        for (i in 0 until length) {
            sb.append(Char(buffer.get().toUShort()))
        }
        return sb.toString()
    }

    companion object {
        const val DESC = "Lorg/lwjgl/system/MemoryUtil;memASCII(Ljava/nio/ByteBuffer;I)Ljava/lang/String;"
    }
}