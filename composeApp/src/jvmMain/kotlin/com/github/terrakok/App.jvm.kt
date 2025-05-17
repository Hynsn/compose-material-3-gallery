package com.github.terrakok

import java.awt.Desktop
import java.net.URI

internal actual fun openUrl(url: String?) {
    val uri = url?.let { URI.create(it) } ?: return
    Desktop.getDesktop().browse(uri)
}

internal actual fun isNarrowScreen(screenWidth: Int, threshold: Int): Boolean {
    return screenWidth <= threshold
}