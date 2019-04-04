package ru.nikitaboiko.stoservice.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.structure.Helpers
import ru.nikitaboiko.stoservice.structure.Registration
import ru.nikitaboiko.stoservice.structure.Service
import java.util.*
import kotlin.collections.ArrayList

class DatabaseControl(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        const val DATABASE_NAME = "database.db"
        const val VERSION = 1

        const val USERS_TABLE_NAME = "Users"
        const val SERVICE_TABLE_NAME = "Service"
        const val REGISTRATION_TABLE_NAME = "Registration"
        const val PAY_TABLE_NAME = "Pay"

        const val ID = "id"
        const val USER = "user"
        const val PASSWORD = "password"
        const val CAR = "car"
        const val SERVICE = "service"
        const val PRICE = "price"
        const val DATE = "date" //Date format: "YYYY-MM-DD HH:MM"
        const val DONE = "done"
        const val COMMENT = "comment"

        const val CREATE_USERS_TABLE = "create table $USERS_TABLE_NAME ( $ID TEXT primary key," +
                " $USER TEXT NOT NULL, $PASSWORD TEXT NOT NULL)"
        const val CREATE_SERVICE_TABLE = "create table $SERVICE_TABLE_NAME ( $ID TEXT primary key," +
                " $CAR TEXT NOT NULL, $SERVICE TEXT, $USER TEXT NOT NULL, $PRICE REAL, $DATE INTEGER NOT NULL, $DONE INTEGER)"
        const val CREATE_REGISTRATION_TABLE = "create table $REGISTRATION_TABLE_NAME ( $ID TEXT primary key," +
                " $CAR TEXT NOT NULL, $DATE INTEGER NOT NULL, $COMMENT TEXT)"
        const val CREATE_PAY_TABLE = "create table $PAY_TABLE_NAME ( $ID TEXT primary key," +
                " $USER TEXT NOT NULL, $DATE INTEGER NOT NULL, $PRICE REAL, $COMMENT TEXT)"

    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE)
        sqLiteDatabase.execSQL(CREATE_SERVICE_TABLE)
        sqLiteDatabase.execSQL(CREATE_REGISTRATION_TABLE)
        sqLiteDatabase.execSQL(CREATE_PAY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addUser(user: String, password: String): String? {
        val userId = findUserId(user)
        return if (userId == null) {
            val id = UUID.randomUUID().toString()
            val values = ContentValues()
            values.put(ID, id)
            values.put(USER, user)
            values.put(PASSWORD, md5Hex(password))
            App.instance().database.insert(USERS_TABLE_NAME, null, values)
            Toast.makeText(App.instance().baseContext, "Сохранен пользователь - $user", Toast.LENGTH_LONG).show()
            id
        } else {
            Toast.makeText(
                App.instance().baseContext,
                "Пользователь $user уже есть в базе. Выберите другое имя пользователя",
                Toast.LENGTH_LONG
            ).show()
            null
        }
    }

    fun findUserId(user: String): String? {
        val cursor = App.instance().database.query(
            USERS_TABLE_NAME,
            null,
            "UPPER($USER) = '${user.toUpperCase()}'",
            null,
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        return if (cursor != null && !cursor.isAfterLast) {
            cursor.getString(0)
        } else {
            null
        }
    }

    fun getUser(userId: String): String {
        val cursor = App.instance().database.query(
            USERS_TABLE_NAME,
            null,
            "UPPER($USER) = '${userId.toUpperCase()}'",
            null,
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        return if (cursor != null && !cursor.isAfterLast) {
            cursor.getString(1)
        } else {
            ""
        }
    }

    fun addService(service: Service) {
        val values = ContentValues()
        values.put(ID, service.id)
        values.put(CAR, service.car)
        values.put(SERVICE, service.service)
        values.put(USER, findUserId(service.user))
        values.put(PRICE, service.price)
        values.put(DATE, (service.date.time / 1000))
        values.put(DONE, service.done)
        App.instance().database.insert(SERVICE_TABLE_NAME, null, values)
        Toast.makeText(App.instance().baseContext, "Текущие работы успешно сохранены", Toast.LENGTH_LONG).show()
    }

    fun addRegistration(registration: Registration) {
        val values = ContentValues()
        values.put(ID, registration.id)
        values.put(CAR, registration.car)
        values.put(DATE, registration.date.toString())
        values.put(DONE, registration.comment)
        App.instance().database.insert(REGISTRATION_TABLE_NAME, null, values)
        Toast.makeText(
            App.instance().baseContext,
            "Запись успешно сохранена на дату ${registration.date}",
            Toast.LENGTH_LONG
        ).show()
    }

    fun addPay(user: String, price: Double) {
        val values = ContentValues()
        val id = UUID.randomUUID().toString()
        values.put(ID, id)
        values.put(USER, findUserId(user))
        values.put(DATE, Date().toString())
        values.put(PRICE, price)
        App.instance().database.insert(PAY_TABLE_NAME, null, values)
        Toast.makeText(
            App.instance().baseContext,
            "Выдача денежных средств сотруднику $user в размере $price руб. зафиксирована успешно",
            Toast.LENGTH_LONG
        ).show()
    }


    fun getUserList(): ArrayList<String> {
        val userList = ArrayList<String>()
        val cursor = App.instance().database.query(USERS_TABLE_NAME, null, null, null, null, null, null)
        cursor?.moveToFirst()
        if (cursor != null && !cursor.isAfterLast) {
            do {
                userList.add(cursor.getString(1))
            } while (cursor.moveToNext())
        }
        return userList
    }

    fun passIsCorrect(user: String, password: String): Boolean {
        var currentPassword = ""
        val cursor = App.instance().database.query(
            USERS_TABLE_NAME,
            null,
            "UPPER($USER) = '${user.toUpperCase()}'",
            null,
            null,
            null,
            null
        ) ?: null
        cursor?.moveToFirst()
        if (cursor != null && !cursor.isAfterLast) {
            currentPassword = cursor.getString(2)
        } else {
            Toast.makeText(
                App.instance().baseContext,
                "не нашли пользователя $user в базе",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        if (currentPassword.equals(md5Hex(password))) {
            return true
        } else {
            Toast.makeText(
                App.instance().baseContext,
                "пароль не верный",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
    }

    fun getServices(user: String = "", startDate: Date? = null, endDate: Date? = null) {
        val serviceList = Helpers.instance.servicesList
        serviceList.clear()

        val req = makeRequirement(user, startDate, endDate)
        var currentDate: Date
        var status: Boolean

        val cursor = App.instance.database.query(SERVICE_TABLE_NAME, null, req, null, null, null, null)
        cursor?.moveToFirst()
        if (!cursor.isAfterLast) {
            do {
                //currentDate = Helpers.instance.getDatebyString(cursor.getString(5))
                status = cursor.getInt(6) == 1
                val service = Service(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    getUser(cursor.getString(3)),
                    cursor.getDouble(4),
                    Date(cursor.getLong(5) * 1000),
                    status
                )
                serviceList.add(service)
            } while (cursor.moveToNext())
        }


    }

    fun getTotalAmount(user: String = "", startDate: Date? = null): Double {
        var totalAmount = 0.0
        val req = makeRequirement(user, startDate)

        val cursor = App.instance().database.query(
            SERVICE_TABLE_NAME,
            arrayOf("SUM(PRICE) AS amount"),
            req,
            null,
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        if (cursor != null && !cursor.isAfterLast) {
            totalAmount = cursor.getDouble(0)
        }
        return totalAmount
    }


    fun getTotalSalary(user: String = "", startDate: Date? = null): Double {
        var totalAmount = 0.0
        val req = makeRequirement(user, startDate, Date())

        val cursor =
            App.instance().database.query(PAY_TABLE_NAME, arrayOf("SUM(PRICE) AS amount"), req, null, null, null, null)
        cursor?.moveToFirst()
        if (cursor != null && !cursor.isAfterLast) {
            totalAmount = cursor.getDouble(0)
        }
        return totalAmount
    }

    fun md5Hex(text: String): String {
        return Hex.encodeHex(DigestUtils.md5("vicomlite$text")).joinToString("")
    }

    private fun makeRequirement(user: String, startDate: Date? = null, endDate: Date? = null): String? {
        var period =
            if (startDate == null) "" else "DATE >= '${(startDate.time / 1000)}'"
        if (!period.isEmpty() && endDate != null) {
            period += " AND  DATE <= '${(endDate.time / 1000)}'"
        }
        val userText = if (user.isEmpty()) "" else "USER = '${findUserId(user)}'"
        val req =
            if (userText.isEmpty() && period.isEmpty()) null else (period + (if (period.isEmpty() || userText.isEmpty()) "" else " AND ") + userText)
        return req
    }
}