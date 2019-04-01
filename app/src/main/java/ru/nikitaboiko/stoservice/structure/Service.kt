package ru.nikitaboiko.stoservice.structure

import java.util.*

class Service(
    val id: String,
    val car: String,
    val service: String,
    val user: String,
    val price: Double,
    val date: Date,
    val done: Boolean
)
