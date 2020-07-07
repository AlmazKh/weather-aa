package com.almaz.weather_aa

import android.app.Application
import org.kodein.di.KodeinAware
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.AnyKodeinContext
import org.kodein.di.KodeinTrigger

class App : Application(), KodeinAware {
    override lateinit var kodein: Kodein

    override val kodeinContext: KodeinContext<*>
        get() = AnyKodeinContext

    override val kodeinTrigger: KodeinTrigger?
        get() = KodeinTrigger()

    override fun onCreate() {
        super.onCreate()
        app = this
        kodein = com.almaz.weather_aa.di.Kodein().initKodein(this)
    }

    companion object {
        lateinit var app: App
    }
}