package ru.nikitaboiko.stoservice

import android.app.Application
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import ru.nikitaboiko.stoservice.database.DatabaseControl
import ru.nikitaboiko.stoservice.services.MainService

class App : Application() {
    var dataControl: DatabaseControl? = null
        private set
    var database: SQLiteDatabase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        startService(Intent(this, MainService::class.java))
        dataControl = DatabaseControl(this, "database.db", null, 1)
        database = dataControl!!.getWritableDatabase()
        Log.i("DDLog", "startService(): Done!")
    }

    companion object {
        private var instance: App? = null

        fun instance(): App? {
            return instance
        }
    }
}