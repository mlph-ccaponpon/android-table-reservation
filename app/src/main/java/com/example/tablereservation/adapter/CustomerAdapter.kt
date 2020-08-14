package com.example.tablereservation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tablereservation.R
import com.example.tablereservation.model.Customer
import kotlinx.android.synthetic.main.customer_list_item.view.*

class CustomerAdapter(customerList: List<Customer>, context: Context) : RecyclerView.Adapter<CustomerAdapter.ViewHolder> () {
    var customerList = customerList
    var context = context

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val customerName = v.customer_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.customer_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var customer = customerList[position]
        viewHolder?.customerName?.text = customer.name
    }


}