/*
 * Copyright 2020-2023 RW-HPS Team and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/RW-HPS/RW-HPS/blob/master/LICENSE
 */

package net.rwhps.server.dependent.redirections.slick

internal object SilckClassPathProperties {
    val AppGameContainer = "org.newdawn.slick.AppGameContainer"
    val Image = "org.newdawn.slick.Image"
    val SlickLog = "org.newdawn.slick.util.Log"
    val ResourceLoader = "org.newdawn.slick.util.ResourceLoader"
    val ClasspathLocation = "org.newdawn.slick.util.ClasspathLocation"
    val FileSystemLocation = "org.newdawn.slick.util.FileSystemLocation"
    val DrFileSystemLocation = "net.rwhps.server.dependent.redirections.slick.ZipFileSystemLocation"
    val Graphics = "org.newdawn.slick.Graphics"
    val DrGraphics = "net.rwhps.server.dependent.redirections.slick.DrGraphics"
}
