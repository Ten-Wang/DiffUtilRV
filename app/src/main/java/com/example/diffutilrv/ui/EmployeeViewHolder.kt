package com.example.diffutilrv.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.R
import com.example.diffutilrv.data.Employee
import com.example.diffutilrv.databinding.ItemEmployeeBinding

class EmployeeViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemEmployeeBinding.bind(itemView)

    private var employee: Employee? = null

    fun bind(employee: Employee?) {
        if (employee != null) {
            showData(employee)
        }
    }

    private fun showData(employee: Employee) {
        this.employee = employee
        binding.employeeName.text = employee.name
        binding.employeeRole.text = employee.role
    }

    companion object {
        fun create(parent: ViewGroup): EmployeeViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_employee, parent, false)
            return EmployeeViewHolder(view)
        }
    }
}
