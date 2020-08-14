package com.example.tablereservation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.tablereservation.R
import com.example.tablereservation.database.AppDatabase
import com.example.tablereservation.model.Customer
import com.example.tablereservation.model.Table
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddCustomerActivity : AppCompatActivity() {
    lateinit var customerName: EditText
    lateinit var tableSpinner: Spinner
    lateinit var submitButton: Button
    lateinit var appDatabase: AppDatabase

    private var tableCustomerCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        supportActionBar?.title = getString(R.string.add_customer)

        appDatabase = AppDatabase.getDatabase(this)
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
            closeKeyBoard()
            validateAddCustomerForm()
        })
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    @SuppressLint("CheckResult")
    private fun validateAddCustomerForm() {
        if(customerName.text.isNullOrBlank()) {
            val customerNameRequired = Toast.makeText(this, getString(R.string.add_customer_name_req), Toast.LENGTH_LONG)
            customerNameRequired.show()
        } else {
            Observable.fromCallable {
                tableCustomerCount = appDatabase.customerDao().getCustomerCountByTable(tableSpinner.selectedItem as Int)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    if(tableCustomerCount < Table.TABLE_CUSTOMER_MAX) {
                        saveCustomer()
                    } else {
                        val tableFull = Toast.makeText(this, getString(R.string.add_customer_table_full), Toast.LENGTH_LONG)
                        tableFull.show()
                    }
                }
        }
    }

    @SuppressLint("CheckResult")
    private fun saveCustomer() {
        Observable.fromCallable {
            var customer = Customer(0, customerName.text.toString(), tableSpinner.selectedItem as Int)
            appDatabase.customerDao().saveCustomer(customer)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                val addCustomerSuccess = Toast.makeText(this, getString(R.string.add_customer_success, tableCustomerCount + 1), Toast.LENGTH_LONG)
                addCustomerSuccess.show()
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }
    }
}