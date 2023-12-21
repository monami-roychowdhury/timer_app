package com.example.countdowntimerapp

import android.app.Application
import com.example.countdowntimerapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CountDownTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CountDownTimerApplication)
            modules(appModule)
        }
    }
}