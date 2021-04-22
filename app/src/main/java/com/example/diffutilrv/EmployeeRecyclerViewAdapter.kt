package com.example.diffutilrv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class EmployeeRecyclerViewAdapter : RecyclerView.Adapter<EmployeeRecyclerViewAdapter.ViewHolder>() {

    private val mEmployees: MutableList<Employee> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = mEmployees[position]
        holder.name.text = employee.name
        holder.role.text = employee.role
    }

    fun updateEmployeeListItems(employees: List<Employee>) {
        val diffCallback = EmployeeDiffCallback(mEmployees, employees)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mEmployees.clear()
        mEmployees.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return mEmployees.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val role: TextView = itemView.findViewById<View>(R.id.employee_role) as TextView
        val name: TextView = itemView.findViewById<View>(R.id.employee_name) as TextView
    }
}