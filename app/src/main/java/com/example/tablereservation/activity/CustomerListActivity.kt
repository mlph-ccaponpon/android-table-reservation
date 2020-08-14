package com.example.tablereservation.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tablereservation.R
import com.example.tablereservation.adapter.CustomerAdapter
import com.example.tablereservation.database.AppDatabase
import com.example.tablereservation.model.Customer
import com.example.tablereservation.model.Table
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CustomerListActivity : AppCompatActivity() {
    private var tableId: Int = 0
    lateinit var customerListView: RecyclerView
    private var customerList:List<String> = ArrayList()
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)

        tableId = intent.getIntExtra(Table.TABLE_ID_KEY, 0)
        val navBarTitle = getString(R.string.table_id, tableId)
        supportActionBar?.title = navBarTitle

        appDatabase = AppDatabase.getDatabase(this)
        showCustomerListByTable()
    }

    @SuppressLint("CheckResult")
    private fun showCustomerListByTable(){
        Observable.fromCallable {
            appDatabase.customerDao().getCustomerByTable(tableId)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ customerList ->
                customerListView = findViewById(R.id.customer_list)
                customerListView.layoutManager = LinearLayoutManager(this)
                customerListView.adapter = CustomerAdapter(customerList, this)
            }
    }
}