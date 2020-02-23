package com.example.newmarket

import android.app.Application
import com.example.lesson32.Database.Database

class App:Application(){

    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        Database.getDatabase().closeDatabase()
    }
}