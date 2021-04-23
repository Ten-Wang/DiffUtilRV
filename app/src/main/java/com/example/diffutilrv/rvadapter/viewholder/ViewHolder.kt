package com.example.diffutilrv.rvadapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.R
import com.example.diffutilrv.model.Employee

class ViewHolder(
    itemView: View,
    clickListener: (adapterPosition: Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun newInstance(parent: ViewGroup, clickListener: (adapterPosition: Int) -> Unit) =
            ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false),
                clickListener
            )
    }

    private val role: TextView = itemView.findViewById(R.id.employee_role)
    private val name: TextView = itemView.findViewById(R.id.employee_name)
    private val cost: TextView = itemView.findViewById(R.id.employee_cost)

    init {
        itemView.setOnClickListener { clickListener(adapterPosition) }
    }

    fun bind(employee: Employee) {
        name.text = employee.name
        role.text = employee.role
        cost.text = employee.cost.toString()
    }
}