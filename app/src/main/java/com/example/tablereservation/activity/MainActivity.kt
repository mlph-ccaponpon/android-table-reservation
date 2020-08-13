package com.example.tablereservation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import com.example.tablereservation.R
import com.example.tablereservation.adapter.TableAdapter
import com.example.tablereservation.model.Table

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    lateinit var tableGridView: GridView
    lateinit var tableAdapter: TableAdapter
    private var tableList: ArrayList<Table> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableGridView = findViewById(R.id.table_grid_view)
        tableList = setTableList()
        tableAdapter = TableAdapter(applicationContext, tableList)
        tableGridView.adapter = tableAdapter
        tableGridView.onItemClickListener = this
    }

    private fun setTableList() : ArrayList<Table> {
        var tableList: ArrayList<Table> = ArrayList()
        tableList.add(Table(R.drawable.ic_table_1, 1))
        tableList.add(Table(R.drawable.ic_table_2, 2))
        tableList.add(Table(R.drawable.ic_table_3, 3))
        tableList.add(Table(R.drawable.ic_table_4, 4))
        return tableList
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val customerList: Intent
    }
}