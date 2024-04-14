package ru.mirea.andreevapk.yandexdriver

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App : Application() {

    companion object {
        private const val MAPKIT_API_KEY = "7c261af7-77f3-4391-bf60-986a9e956b0c"
    }

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey(MAPKIT_API_KEY)
    }
}