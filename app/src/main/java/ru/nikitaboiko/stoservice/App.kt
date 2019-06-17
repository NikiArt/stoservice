package ru.nikitaboiko.stoservice

import android.app.Application
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import ru.nikitaboiko.stoservice.database.DatabaseControl
import ru.nikitaboiko.stoservice.services.MainService

class App : Application() {
    lateinit var dataControl: DatabaseControl
        private set
    lateinit var database: SQLiteDatabase
        private set
    lateinit var mAuth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        instance = this
        startService(Intent(this, MainService::class.java))
        dataControl = DatabaseControl(this, "database.db", null, 1)
        database = dataControl.getWritableDatabase()
        mAuth = FirebaseAuth.getInstance()
        Log.i("DDLog", "startService(): Done!")
    }

    companion object {
        lateinit var instance: App

        fun instance(): App {
            return instance
        }
    }
}