package ru.nikitaboiko.stoservice.structure

import java.util.*

class Pay(
    val id: String,
    val user: String,
    val date: Date,
    val price: Double,
    val comment: String
)