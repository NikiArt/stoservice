package ru.nikitaboiko.stoservice.structure

import java.sql.Date

class Registration(
    val id: String,
    val car: String,
    val date: Date,
    val comment: String
)