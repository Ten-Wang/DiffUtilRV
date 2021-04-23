package com.example.diffutilrv.rvadapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.R
import com.example.diffutilrv.model.Employee

class ViewHolder(
    itemView: View,
    clickListener: (adapterPosition: Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val role: TextView = itemView.findViewById(R.id.employee_role)
    private val name: TextView = itemView.findViewById(R.id.employee_name)

    init {
        itemView.setOnClickListener { clickListener(adapterPosition) }
    }

    fun bind(employee: Employee) {
        name.text = employee.name
        role.text = employee.role
    }
}