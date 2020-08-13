package com.example.tablereservation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tablereservation.R
import com.example.tablereservation.model.Table

class CustomerListActivity : AppCompatActivity() {
    private var tableId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)

        tableId = intent.getIntExtra(Table.TABLE_ID_KEY, 0)
        val navBarTitle = getString(R.string.table_id, tableId)
        supportActionBar?.title = navBarTitle
    }
}