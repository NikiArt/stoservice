package ru.nikitaboiko.stoservice.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import ru.nikitaboiko.stoservice.App
import ru.nikitaboiko.stoservice.structure.Registration
import ru.nikitaboiko.stoservice.structure.Service
import java.util.*

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
                " $CAR TEXT NOT NULL, $SERVICE TEXT, $USER TEXT NOT NULL, $PRICE REAL, $DATE TEXT NOT NULL, $DONE INTEGER)"
        const val CREATE_REGISTRATION_TABLE = "create table $REGISTRATION_TABLE_NAME ( $ID TEXT primary key," +
                " $CAR TEXT NOT NULL, $DATE TEXT NOT NULL, $COMMENT TEXT)"
        const val CREATE_PAY_TABLE = "create table $PAY_TABLE_NAME ( $ID TEXT primary key," +
                " $USER TEXT NOT NULL, $DATE TEXT NOT NULL, $PRICE TEXT)"

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
            App.instance()?.database?.insert(USERS_TABLE_NAME, null, values)
            Toast.makeText(App.instance()?.baseContext, "Сохранен пользователь - $user", Toast.LENGTH_LONG).show()
            id
        } else {
            Log.d("DDLog", "Пользователь $user уже есть в базе")
            null
        }
    }

    fun findUserId(user: String): String? {
        val cursor = App.instance()?.database?.query(
            USERS_TABLE_NAME,
            null,
            "UPPER($USER) = '${user.toUpperCase()}'",
            null,
            null,
            null,
            null
        ) ?: null
        cursor?.moveToFirst()
        return if (cursor != null && !cursor?.isAfterLast) {
            cursor?.getString(0)
        } else {
            null
        }
    }

    fun addService(service: Service) {
        val values = ContentValues()
        values.put(ID, service.id)
        values.put(CAR, service.car)
        values.put(SERVICE, service.service)
        values.put(USER, findUserId(service.user))
        values.put(PRICE, service.price)
        values.put(DATE, service.date.toString())
        values.put(DONE, service.done)
        App.instance()?.database?.insert(SERVICE_TABLE_NAME, null, values)
        Toast.makeText(App.instance()?.baseContext, "Текущие работы успешно сохранены", Toast.LENGTH_LONG).show()
    }

    fun addRegistration(registration: Registration) {
        val values = ContentValues()
        values.put(ID, registration.id)
        values.put(CAR, registration.car)
        values.put(DATE, registration.date.toString())
        values.put(DONE, registration.comment)
        App.instance()?.database?.insert(REGISTRATION_TABLE_NAME, null, values)
        Toast.makeText(
            App.instance()?.baseContext,
            "Запись успешно сохранена на дату ${registration.date}",
            Toast.LENGTH_LONG
        ).show()
    }

}