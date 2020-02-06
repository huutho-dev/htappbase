package com.huutho.baselibrary

import androidx.multidex.MultiDexApplication
import com.google.gson.Gson
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            if (!LeakCanary.isInAnalyzerProcess(this))
                LeakCanary.install(this)

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    viewModelModule,
                    module { single { Gson() } },
                    *setModules()
                )
            )
        }
    }

    /**
     * Add more module to DI
     * + NetModule
     * + ViewModelModule
     * + AppModule
     * ...
     */
    open fun setModules(): Array<Module> {
        return arrayOf()
    }

    abstract val appModule: Module

    abstract val networkModule: Module

    abstract val viewModelModule: Module


}