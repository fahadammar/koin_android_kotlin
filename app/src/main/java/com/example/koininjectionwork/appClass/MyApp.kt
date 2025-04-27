package com.example.koininjectionwork.appClass

import android.app.Application
import com.example.koininjectionwork.di.module.dataModule
import com.example.koininjectionwork.di.module.diAppModule
import com.example.koininjectionwork.di.module.interfaceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(diAppModule, interfaceModule, dataModule)
        }
    }
}