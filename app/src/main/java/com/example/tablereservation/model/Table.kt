package com.example.tablereservation.model

class Table {
    companion object {
        const val TABLE_ID_KEY: String = "TABLE_ID"
        const val TABLE_CUSTOMER_MAX: Int = 5
        const val ALL_TABLE_CUSTOMER_MAX: Int = 20
    }

    var id: Int? = 0
    var icon: Int? = 0

    constructor(id: Int?, icon: Int?) {
        this.id = id
        this.icon = icon
    }
}