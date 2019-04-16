package ru.nikitaboiko.stoservice.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.R
import ru.nikitaboiko.stoservice.structure.Helpers
import ru.nikitaboiko.stoservice.structure.Pay
import java.util.*

class SalaryAddDialog : AppCompatActivity(), UserListDialog.OnFragmentInteractionListener {
    lateinit var user: EditText
    lateinit var amount: EditText
    lateinit var comment: EditText
    lateinit var totalAmount: TextView

    override fun onFragmentInteraction(nextActivity: String, unit: String) {
        when (nextActivity) {
            "UserLogin" -> {
                user.text.clear()
                user.text.append(unit)
                refreshTotalAmount()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_salary_add)
        val buttonAdd = findViewById<View>(R.id.fragment_salary_add_button_ok)
        val buttonAddAmount = findViewById<View>(R.id.fragment_salary_add_add_amount)
        user = findViewById<View>(R.id.fragment_salary_add_user) as EditText
        user.keyListener = null
        amount = findViewById<View>(R.id.fragment_salary_add_amount) as EditText
        comment = findViewById<View>(R.id.fragment_salary_add_comment) as EditText
        totalAmount = findViewById<View>(R.id.fragment_salary_add_total_amount) as TextView


        user.setOnClickListener {
            val manager = supportFragmentManager
            val myDialogFragment = UserListDialog()
            myDialogFragment.show(manager, "dialog")
        }
        buttonAdd.setOnClickListener {
            addSalary()
        }

        buttonAddAmount.setOnClickListener {
            if (!totalAmount.text.isEmpty() && totalAmount.text.toString().toDouble() > 0) {
                amount.text.clear()
                amount.text.append(totalAmount.text.toString())
            }
        }
    }

    private fun addSalary() {
        if (user.text.toString().isEmpty()) {
            Toast.makeText(this, "Необходимо указать сотрудника", Toast.LENGTH_SHORT).show()
            return
        }

        if (amount.text.toString().isEmpty()) {
            Toast.makeText(this, "Необходимо ввести сумму", Toast.LENGTH_SHORT).show()
            return
        }

        val id = UUID.randomUUID().toString()
        val currentPay = Pay(
            id,
            user.text.toString(),
            Date(),
            amount.text.toString().toDouble(),
            comment.text.toString()
        )
        App.instance().dataControl.addPay(currentPay)
        Helpers.instance.salaryList.add(currentPay)
        finish()
    }

    fun refreshTotalAmount() {
        if (user.text.isEmpty()) {
            return
        }

        amount.text.clear()
        var currentAmount =
            App.instance().dataControl.getTotalAmount(
                user.text.toString()
            ) * 0.4
        currentAmount = Math.rint(100.0 * currentAmount) / 100.0
        var currentSalary =
            App.instance().dataControl.getTotalSalary(user.text.toString())
        currentSalary = Math.rint(100.0 * currentSalary) / 100.0

        totalAmount.text = "${(Math.rint(100.0 * (currentAmount - currentSalary)) / 100.0)}"
    }
}