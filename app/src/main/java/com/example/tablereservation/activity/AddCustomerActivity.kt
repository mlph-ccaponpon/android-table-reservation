package com.example.tablereservation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.tablereservation.R
import com.example.tablereservation.database.AppDatabase
import com.example.tablereservation.model.Customer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddCustomerActivity : AppCompatActivity() {
    lateinit var customerName: EditText
    lateinit var tableSpinner: Spinner
    lateinit var submitButton: Button
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        supportActionBar?.title = getString(R.string.add_customer)

        initAddCustomerForm()
    }

    private fun initAddCustomerForm(){
        initCustomerName()
        initTableSpinner()
        initSubmitButton()
    }

    private fun initCustomerName(){
        customerName = findViewById(R.id.customer_name)
    }

    private fun initTableSpinner(){
        val tableOptions = arrayOf(1, 2, 3, 4)

        tableSpinner = findViewById(R.id.table_spinner)
        tableSpinner.adapter = ArrayAdapter<Int>(this, android.R.layout.simple_list_item_1,tableOptions)
        tableSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                //TODO
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO
            }
        }
    }

    private fun initSubmitButton(){
        submitButton = findViewById(R.id.add_customer_submit)
        submitButton.setOnClickListener(View.OnClickListener {
            // Save Customer
            Observable.fromCallable {
                var customer = Customer(0, customerName.text.toString(), tableSpinner.selectedItem as Int)
                appDatabase = AppDatabase.getDatabase(this)
                appDatabase.customerDao().saveCustomer(customer)
            }.doOnNext {
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        })
    }
}