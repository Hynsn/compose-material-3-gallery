package com.github.terrakok

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import kotlin.math.pow
import kotlin.math.sqrt

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
    }
}

internal actual fun openUrl(url: String?) {
    val uri = url?.let { Uri.parse(it) } ?: return
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    AndroidApp.INSTANCE.startActivity(intent)
}
private fun isPad(context: Context): Boolean {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val disp = wm.defaultDisplay
    val dm = DisplayMetrics()
    disp.getMetrics(dm)
    val x = (dm.widthPixels / dm.xdpi).pow(2)
    val y = (dm.heightPixels / dm.ydpi).pow(2)
    val sInches = sqrt(x + y)

    return sInches >= 7
}

internal actual fun isNarrowScreen(screenWidth: Int, threshold: Int): Boolean {
    return !isPad(AndroidApp.INSTANCE)
}