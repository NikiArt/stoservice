package ru.nikitaboiko.stoservice.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.admin_order_item.view.*
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.R
import ru.nikitaboiko.stoservice.structure.Helpers

class AdminOrderAdapter : RecyclerView.Adapter<AdminOrderAdapter.MyViewHolder>() {
    private lateinit var listener: OnFragmentInteractionListener

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var user: TextView = view.admin_order_item_user
        var date: TextView = view.admin_order_item_date
        var work: TextView = view.admin_order_item_work
        var car: TextView = view.admin_order_item_car
        var price: TextView = view.admin_order_item_price
        var done: CheckBox = view.admin_order_item_done
    }

    fun setLongClickListener(mlistener: OnFragmentInteractionListener) {
        listener = mlistener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_order_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Helpers.instance.servicesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.user.text = Helpers.instance.servicesList[position].user
        holder.date.text =
            Helpers.instance.getStringbyDate(Helpers.instance.servicesList[position].date, "dd MMMM yyyy\nEEE, HH:mm")
        holder.work.text = Helpers.instance.servicesList[position].service
        holder.car.text = Helpers.instance.servicesList[position].car
        holder.price.text = Helpers.instance.servicesList[position].price.toString()
        holder.done.isChecked = Helpers.instance.servicesList[position].done

        holder.done.setOnClickListener {
            Helpers.instance.servicesList[position].done = holder.done.isChecked
            App.instance.dataControl.modifyService(Helpers.instance.servicesList[position])
        }
    }

    fun update() {
        notifyDataSetChanged()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(currentActivity: String, unit: String)
    }
}