package ru.nikitaboiko.stoservice.fragments

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_service_add.view.*
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.R
import ru.nikitaboiko.stoservice.structure.Service
import java.util.*

class ServiceAddDialog : DialogFragment() {
    lateinit var car: TextView
    lateinit var service: TextView
    lateinit var price: TextView
    lateinit var comment: TextView
    lateinit var user: String
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cont = context ?: return super.onCreateDialog(savedInstanceState)
        val activ = activity ?: return super.onCreateDialog(savedInstanceState)
        val bundle = arguments
        user = bundle?.getString("user") ?: ""
        if (cont is OnFragmentInteractionListener) {
            listener = cont
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        val builder = AlertDialog.Builder(cont)
        val inflater = activ.layoutInflater
        val inflatedView = inflater.inflate(R.layout.fragment_service_add, null)
        builder.setView(inflatedView)
        val buttonAdd = inflatedView.fragment_service_add_button_ok
        car = inflatedView.fragment_service_add_car
        service = inflatedView.fragment_service_add_service
        price = inflatedView.fragment_service_add_price
        comment = inflatedView.fragment_service_add_comment

        buttonAdd.setOnClickListener {
            addService()
        }

        return builder.create()
    }

    private fun addService() {
        if (car.text.toString().isEmpty()) {
            Toast.makeText(context, "Необходимо указать информацию по автомобилю", Toast.LENGTH_SHORT).show()
            return
        }
        if (service.text.toString().isEmpty()) {
            Toast.makeText(context, "Необходимо указать информацию по сделанным работам", Toast.LENGTH_SHORT).show()
            return
        }
        if (price.text.toString().isEmpty()) {
            Toast.makeText(context, "Необходимо указать цену", Toast.LENGTH_SHORT).show()
            return
        }

        val id = UUID.randomUUID().toString()
        val currentService = Service(
            id,
            car.text.toString(),
            service.text.toString(),
            user,
            price.text.toString().toDouble(),
            Date()
        )
        App.instance().dataControl.addService(currentService)
        listener.onFragmentInteraction("UpdateServices", "")
        dismiss()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(currentActivity: String, unit: String)
    }
}