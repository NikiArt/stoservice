package ru.nikitaboiko.stoservice.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.salary_item.view.*
import ru.nikitaboiko.stoservice.R
import ru.nikitaboiko.stoservice.structure.Helpers

class AdminSalaryAdapter : RecyclerView.Adapter<AdminSalaryAdapter.MyViewHolder>() {
    private lateinit var listener: OnFragmentInteractionListener

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var user: TextView = view.salary_item_user
        var date: TextView = view.salary_item_date
        var amount: TextView = view.salary_item_amount
        var comment: TextView = view.salary_item_comment
    }

    fun setLongClickListener(mlistener: OnFragmentInteractionListener) {
        listener = mlistener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.salary_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Helpers.instance.salaryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.user.text = Helpers.instance.salaryList[position].user
        holder.date.text =
            Helpers.instance.getStringbyDate(Helpers.instance.salaryList[position].date, "dd MMMM yyyy\nEEE, HH:mm")
        holder.amount.text = Helpers.instance.salaryList[position].price.toString() + "\u20BD"
        holder.comment.text = Helpers.instance.salaryList[position].comment
        holder.view.setOnLongClickListener {
            listener.onFragmentInteraction("deleteSalary", "$position")
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