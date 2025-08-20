package com.recipebook

import android.app.Application
import com.recipebook.logging.LoggingConfigurator
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
internal class TheApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LoggingConfigurator.enableLogging = true
    }
}
