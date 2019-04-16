package ru.nikitaboiko.stoservice.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.record_item.view.*
import ru.nikitaboiko.stoservice.R
import ru.nikitaboiko.stoservice.structure.Helpers


class RecordServiceAdapter : RecyclerView.Adapter<RecordServiceAdapter.MyViewHolder>() {
    private lateinit var listener: OnFragmentInteractionListener

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var car: TextView = view.record_item_car
        var dateRec: TextView = view.record_item_date
        var client: TextView = view.record_item_client
        var telephone: TextView = view.record_item_telephone
        var comment: TextView = view.record_item_comment
    }

    fun setLongClickListener(mlistener: OnFragmentInteractionListener) {
        listener = mlistener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Helpers.instance.record.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.car.text = Helpers.instance.record[position].car
        holder.dateRec.text =
            Helpers.instance.getStringbyDate(Helpers.instance.record[position].date, "dd MMMM yyyy\nEEE, HH:mm")
        holder.client.text = Helpers.instance.record[position].client
        holder.telephone.text = Helpers.instance.record[position].telephone
        holder.comment.text = Helpers.instance.record[position].comment
        holder.view.setOnLongClickListener {
            listener.onFragmentInteraction("deleteRecord", "$position")
            true
        }
    }

    fun update() {
        notifyDataSetChanged()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(currentActivity: String, unit: String)
    }
}