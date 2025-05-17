package com.github.terrakok

import kotlinx.browser.window

internal actual fun openUrl(url: String?) {
    url?.let { window.open(it) }
}

internal actual fun isNarrowScreen(screenWidth: Int, threshold: Int): Boolean {
    return screenWidth <= threshold
}