package com.example.tablereservation.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tablereservation.model.Customer

@Dao
interface CustomerDao {
    @Insert
    fun saveCustomer(customer: Customer)

    @Query("SELECT * FROM Customer")
    fun getAllCustomer() : List<Customer>

    @Query("SELECT COUNT(ID) FROM Customer")
    fun getAllCustomerCount() : Int

    @Query("SELECT * FROM Customer WHERE TABLE_ID == :tableId")
    fun getCustomerByTable(tableId: Int) : List<Customer>

    @Query("SELECT COUNT(ID) FROM Customer WHERE TABLE_ID == :tableId")
    fun getCustomerCountByTable(tableId: Int) : Int

    @Query("DELETE FROM Customer")
    fun deleteAllCustomer()

}