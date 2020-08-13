package com.example.tablereservation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tablereservation.R

class CustomerListActivity : AppCompatActivity() {
    private var tableId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)

        tableId = intent.getIntExtra("tableId", 0)
        val navBarTitle = "Table ${tableId}"
        supportActionBar?.title = navBarTitle
    }
}