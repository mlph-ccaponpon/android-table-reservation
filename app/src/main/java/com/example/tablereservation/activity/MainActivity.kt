package com.example.tablereservation.activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tablereservation.R
import com.example.tablereservation.adapter.TableAdapter
import com.example.tablereservation.database.AppDatabase
import com.example.tablereservation.model.Table
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    lateinit var tableGridView: GridView
    lateinit var tableAdapter: TableAdapter
    private var tableList: ArrayList<Table> = ArrayList()

    lateinit var addCustomerButton: Button
    lateinit var clearTablesButton: Button

    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher_round)


        appDatabase = AppDatabase.getDatabase(this)
        initTableGridView()
        initOptionButtons()
    }

    private fun initTableGridView() {
        tableGridView = findViewById(R.id.table_grid_view)
        tableList = setTableList()
        tableAdapter = TableAdapter(applicationContext, tableList)
        tableGridView.adapter = tableAdapter
        tableGridView.onItemClickListener = this
    }

    private fun setTableList() : ArrayList<Table> {
        var tableList: ArrayList<Table> = ArrayList()
        tableList.add(Table( 1, R.drawable.ic_table_1))
        tableList.add(Table(2, R.drawable.ic_table_2))
        tableList.add(Table(3, R.drawable.ic_table_3))
        tableList.add(Table(4, R.drawable.ic_table_4))
        return tableList
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val customerListIntent = Intent(view?.context, CustomerListActivity::class.java)
        customerListIntent.putExtra(Table.TABLE_ID_KEY, tableList[position].id)
        startActivity(customerListIntent)
    }

    private fun initOptionButtons() {
        initAddCustomerButton()
        initClearTablesButton()
    }

    private fun initAddCustomerButton() {
        addCustomerButton = findViewById(R.id.add_customer)

        addCustomerButton.setOnClickListener {
            Observable.fromCallable {
                appDatabase.customerDao().getAllCustomerCount()
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    customerCount ->
                    if(customerCount < Table.ALL_TABLE_CUSTOMER_MAX) {
                        val addCustomerIntent = Intent(this, AddCustomerActivity::class.java)
                        startActivity(addCustomerIntent)
                    } else {
                        val tablesFull = Toast.makeText(this, getString(R.string.all_table_full), Toast.LENGTH_LONG)
                        tablesFull.show()
                    }
                }
        }
    }
    
    private fun initClearTablesButton() {
        clearTablesButton = findViewById(R.id.clear_tables)
        clearTablesButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.clear_tables))
            builder.setMessage(getString(R.string.clear_tables_msg))
            builder.setPositiveButton(getString(R.string.clear_tables_yes)) { _:DialogInterface, _:Int ->
                clearTables()
            }
            builder.setNegativeButton(getString(R.string.clear_tables_no)) { _:DialogInterface, _:Int -> }
            builder.show()
        }
    }

    @SuppressLint("CheckResult")
    private fun clearTables() {
        Observable.fromCallable {
            appDatabase.customerDao().deleteAllCustomer()
        }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            val clearTablesSuccess = Toast.makeText(this, getString(R.string.clear_tables_success), Toast.LENGTH_LONG)
            clearTablesSuccess.show()
        }
    }

    override fun onBackPressed() {
        finishAffinity();
        finish();
    }
}