package com.example.tablereservation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tablereservation.R

class AddCustomerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        supportActionBar?.title = getString(R.string.add_customer)
    }
}