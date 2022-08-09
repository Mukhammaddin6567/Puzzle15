package uz.gita.puzzle15MBF

import android.app.Application
import uz.gita.puzzle15MBF.database.MySharedPreferences

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        // registration shared preferences
        MySharedPreferences.initPreferences(this)
    }
}