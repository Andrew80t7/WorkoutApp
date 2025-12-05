package com.example.treningapp

import android.app.Application
import com.example.treningapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TreningApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TreningApp)
            modules(appModule)
        }
    }

}