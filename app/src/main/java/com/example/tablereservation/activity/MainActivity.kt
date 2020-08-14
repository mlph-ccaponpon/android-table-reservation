package com.example.tablereservation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import com.example.tablereservation.R
import com.example.tablereservation.adapter.TableAdapter
import com.example.tablereservation.model.Table

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    lateinit var tableGridView: GridView
    lateinit var tableAdapter: TableAdapter
    private var tableList: ArrayList<Table> = ArrayList()

    lateinit var addCustomerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }

    private fun initAddCustomerButton() {
        addCustomerButton = findViewById(R.id.add_customer)
        addCustomerButton.setOnClickListener(View.OnClickListener {
            val addCustomerIntent = Intent(this, AddCustomerActivity::class.java)
            startActivity(addCustomerIntent)
        })
    }

    override fun onBackPressed() {
        finishAffinity();
        finish();
    }
}