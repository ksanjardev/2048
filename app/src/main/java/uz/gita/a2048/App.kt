package uz.gita.a2048

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App: Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        LocalStorage.setSharedPref(getSharedPreferences("Data_2048", MODE_PRIVATE))
        context = this
    }
}