package com.example.tablereservation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import com.example.tablereservation.adapter.TableAdapter
import com.example.tablereservation.model.Table

class MainActivity : AppCompatActivity() {

    private var tableGridView: GridView ? = null
    private var tableList: ArrayList<Table> ? = null
    private var tableAdapter: TableAdapter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableGridView = findViewById(R.id.table_grid_view)
        tableList = setTableList()
        tableAdapter = TableAdapter(applicationContext, tableList!!)
        tableGridView?.adapter = tableAdapter
    }

    private fun setTableList() : ArrayList<Table> {
        var tableList: ArrayList<Table> = ArrayList()
        tableList.add(Table(R.drawable.ic_table_1, 1))
        tableList.add(Table(R.drawable.ic_table_2, 2))
        tableList.add(Table(R.drawable.ic_table_3, 3))
        tableList.add(Table(R.drawable.ic_table_4, 4))
        return tableList
    }
}