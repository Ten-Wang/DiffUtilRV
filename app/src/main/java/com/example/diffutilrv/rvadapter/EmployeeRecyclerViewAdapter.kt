package com.example.diffutilrv.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.R
import com.example.diffutilrv.rvadapter.viewholder.ViewHolder

class EmployeeRecyclerViewAdapter(employeeList: List<Employee>) :
    RecyclerView.Adapter<ViewHolder>() {
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
}