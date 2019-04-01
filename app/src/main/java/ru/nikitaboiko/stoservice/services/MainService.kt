package ru.nikitaboiko.stoservice.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat

class MainService : Service() {
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("DDLog", "MainService: OnCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("DDLog", "MainService: onStartCommand()")
        val thread = Thread(Runnable { runCycleRequest() })
        thread.start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun runCycleRequest() {
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}