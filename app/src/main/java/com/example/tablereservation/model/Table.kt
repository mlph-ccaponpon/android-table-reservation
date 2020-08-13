package com.example.tablereservation.model

class Table {
    companion object {
        val TABLE_ID_KEY = "TABLE_ID"
    }

    var id: Int? = 0
    var icon: Int? = 0

    constructor(id: Int?, icon: Int?) {
        this.id = id
        this.icon = icon
    }
}