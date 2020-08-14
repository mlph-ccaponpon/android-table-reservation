package com.example.tablereservation.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "NAME")
    var name: String,

    @ColumnInfo(name = "TABLE_ID")
    var tableId: Int
)