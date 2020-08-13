package com.example.tablereservation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.tablereservation.R
import com.example.tablereservation.model.Table

class TableAdapter(var context: Context, var tableList: ArrayList<Table>) : BaseAdapter() {
    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.table_grid_item, null)
        var tableNum: TextView = view.findViewById(R.id.table_num)
        var tableIcon: ImageView = view.findViewById(R.id.table_icon)
        var table: Table = tableList[position]

        tableNum.text = "Table"
        tableIcon.setImageResource(table.icon!!)

        return view
    }

    override fun getItem(position: Int): Any {
        return tableList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return tableList.size
    }

}