package ru.nikitaboiko.stoservice

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WorkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)
        val displaymetrics = resources.displayMetrics
        val addWorkButton = findViewById<View>(R.id.activity_worker_button_addwork)
        val priceDate = findViewById<View>(R.id.activity_worker_date_price)
        val workList = findViewById<View>(R.id.activity_worker_work_list)
        workList.layoutParams.height = displaymetrics.heightPixels / 2

    }
}
