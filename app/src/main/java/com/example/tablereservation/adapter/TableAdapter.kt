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
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var tableView = View.inflate(context, R.layout.table_grid_item, null)
        var tableId: TextView = tableView.findViewById(R.id.table_id)
        var tableIcon: ImageView = tableView.findViewById(R.id.table_icon)
        var table: Table = tableList[position]

        tableId.text = context.getString(R.string.table_id, table.id)
        tableIcon.setImageResource(table.icon!!)

        return tableView
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