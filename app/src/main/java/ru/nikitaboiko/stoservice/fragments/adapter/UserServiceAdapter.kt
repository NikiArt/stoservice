package ru.nikitaboiko.stoservice.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.work_oreder_item.view.*
import ru.nikitaboiko.stoservice.R
import ru.nikitaboiko.stoservice.structure.Helpers


class UserServiceAdapter : RecyclerView.Adapter<UserServiceAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var car: TextView = view.work_order_item_car
        var dateWork: TextView = view.work_order_item_date
        var price: TextView = view.work_order_item_price
        var user: TextView = view.work_order_item_user
        var done: ImageView = view.work_order_item_image_done
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.work_oreder_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Helpers.instance.servicesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.car.text = Helpers.instance.servicesList[position].car
        holder.dateWork.text =
            Helpers.instance.getStringbyDate(Helpers.instance.servicesList[position].date, "EEE, dd MMMM yyyy HH:mm")
        holder.price.text = Helpers.instance.servicesList[position].price.toString()
        holder.user.text = Helpers.instance.servicesList[position].user
        if (Helpers.instance.servicesList[position].done) {
            holder.done.visibility = View.VISIBLE
        } else {
            holder.done.visibility = View.INVISIBLE
        }
    }

    fun update() {
        notifyDataSetChanged()
    }


}