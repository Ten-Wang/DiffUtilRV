package com.example.diffutilrv

import android.app.Application
import com.example.diffutilrv.di.DiModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DiModule().init()
    }
}