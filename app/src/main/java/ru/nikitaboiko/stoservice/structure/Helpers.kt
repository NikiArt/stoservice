package ru.nikitaboiko.stoservice.structure

import java.text.SimpleDateFormat
import java.util.*

class Helpers {
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
        val sdf = SimpleDateFormat(format)
        return sdf.parse(dateText)
    }

    fun getStringbyDate(date: Date, format: String = "dd MMMM y"): String {
        val sdf = SimpleDateFormat(format)
        return sdf.format(date)
    }
}