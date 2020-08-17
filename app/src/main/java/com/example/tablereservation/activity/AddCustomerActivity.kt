package com.example.tablereservation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.tablereservation.R
import com.example.tablereservation.database.AppDatabase
import com.example.tablereservation.model.Customer
import com.example.tablereservation.model.Table
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddCustomerActivity : AppCompatActivity() {
    lateinit var customerNameInputLayout: TextInputLayout
    lateinit var customerName: TextInputEditText
    lateinit var tableInputLayout: TextInputLayout
    lateinit var tableDropdown: AutoCompleteTextView
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
        initTableDropdown()
        initSubmitButton()
    }

    private fun initCustomerName(){
        customerNameInputLayout = findViewById(R.id.customer_name_layout)
        customerName = findViewById(R.id.customer_name)

        customerName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(c: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(c: CharSequence?, start: Int, before: Int, count: Int) {
                validateCustomerName()
            }

        })
    }

    private fun initTableDropdown(){
        val tableOptions = arrayOf(1, 2, 3, 4)

        tableInputLayout = findViewById(R.id.customer_table_layout)
        tableDropdown = findViewById(R.id.customer_table_dropdown)

        tableDropdown.setAdapter(ArrayAdapter<Int>(this, R.layout.table_dropdown_item,tableOptions))
        tableDropdown.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(c: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(c: CharSequence?, start: Int, before: Int, count: Int) {
                validateTable(false)
            }
        })
        tableDropdown.setOnClickListener {
            closeKeyBoard()
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
        var isValid = validateCustomerName()
        validateTable(isValid)
    }

    private fun validateCustomerName() : Boolean {
        var isValid = true

        if(customerName.text.isNullOrBlank()) {
            customerNameInputLayout.error = getString(R.string.add_customer_name_req)
            isValid = false
        } else {
            customerNameInputLayout.isErrorEnabled = false
        }

        return isValid
    }

    @SuppressLint("CheckResult")
    private fun validateTable(isSubmitForm: Boolean) {
        if(tableDropdown.text.isNullOrBlank()) {
            tableInputLayout.error = getString(R.string.add_customer_table_req)
        } else {
            tableInputLayout.isErrorEnabled = false

            Observable.fromCallable {
                tableCustomerCount = appDatabase.customerDao().getCustomerCountByTable(tableDropdown.text.toString().toInt())
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    if(tableCustomerCount < Table.TABLE_CUSTOMER_MAX) {
                        tableInputLayout.isErrorEnabled = false
                        if(isSubmitForm) {
                            saveCustomer()
                        }
                    } else {
                        tableInputLayout.error = getString(R.string.add_customer_table_full)
                    }
                }
        }
    }


    @SuppressLint("CheckResult")
    private fun saveCustomer() {
        Observable.fromCallable {
            var customer = Customer(0, customerName.text.toString(), tableDropdown.text.toString().toInt())
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