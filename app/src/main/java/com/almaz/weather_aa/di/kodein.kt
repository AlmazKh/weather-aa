package com.almaz.weather_aa.di

import android.app.Application
import org.kodein.di.Kodein

class Kodein {

    lateinit var di: Kodein

    fun initKodein(app: Application): Kodein {
        di = Kodein {
            import(appModule(app))
            import(viewModelModule())
            import(netModule())
            import(interactorModule())
            import(repoModule())
        }
        return di
    }
}
