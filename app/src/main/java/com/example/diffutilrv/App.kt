package com.example.diffutilrv

import android.app.Application
import com.example.diffutilrv.di.DiModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(DiModule().diModuleList)
        }
    }
}