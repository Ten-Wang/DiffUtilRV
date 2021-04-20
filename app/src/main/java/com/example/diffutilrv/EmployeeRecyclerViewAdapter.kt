package com.example.diffutilrv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class EmployeeRecyclerViewAdapter(employeeList: List<Employee>) :
    RecyclerView.Adapter<EmployeeRecyclerViewAdapter.ViewHolder>() {
    private val list: MutableList<Employee> = mutableListOf()

    init {
        list.addAll(employeeList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = list[position]
        holder.bind(employee)
    }

    fun updateEmployeeListItems(employees: List<Employee>) {
        val diffCallback = EmployeeDiffCallback(list, employees)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val role: TextView = itemView.findViewById(R.id.employee_role)
        private val name: TextView = itemView.findViewById(R.id.employee_name)

        fun bind(employee: Employee) {
            name.text = employee.name
            role.text = employee.role
        }
    }


}