package ru.nikitaboiko.stoservice.structure

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Helpers {
    val servicesList = ArrayList<Service>()
    val record = ArrayList<Record>()
    val userList = ArrayList<String>()
    val salaryList = ArrayList<Pay>()

    val calendar = Calendar.getInstance()

    private object Holder {
        val INSTANCE = Helpers()
    }

    companion object {
        val instance: Helpers by lazy { Holder.INSTANCE }
    }

    fun delSpaces(text: String, full: Boolean = true): String {
        val charArray = text.toCharArray()
        var convertedText = ""
        if (full) {
            for (i in 0 until charArray.size) {
                if (charArray[i] != ' ') convertedText += charArray[i]
            }
        } else {
            var startChar = 0
            var endChar = charArray.size - 1
            for (i in 0 until charArray.size) {
                if (charArray[i] == ' ') startChar = i + 1
                else break
            }
            for (i in charArray.size - 1 downTo 0) {
                if (charArray[i] == ' ') endChar = i - 1
                else break
            }
            for (i in startChar..endChar) {
                convertedText += charArray[i]
            }
        }
        return convertedText.toString()
    }

    fun getDatebyString(dateText: String, format: String = "dd MMMM y"): Date {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.parse(dateText)
    }

    fun getStringbyDate(date: Date, format: String = "dd MMMM y"): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

    fun getStartDay(date: Date): Date {
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.time
    }

    fun getEndDay(date: Date): Date {
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        return calendar.time
    }
}