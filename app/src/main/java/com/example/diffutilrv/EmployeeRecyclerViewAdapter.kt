package com.example.diffutilrv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class EmployeeRecyclerViewAdapter(employeeList: List<Employee>?) :
    RecyclerView.Adapter<EmployeeRecyclerViewAdapter.ViewHolder>() {
    private val mEmployees: MutableList<Employee> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_, name, role) = mEmployees[position]
        holder.name.text = name
        holder.role.text = role
    }

    fun updateEmployeeListItems(employees: List<Employee>?) {
        val diffCallback = EmployeeDiffCallback(mEmployees, employees)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mEmployees.clear()
        mEmployees.addAll(employees!!)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return mEmployees.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val role: TextView
        val name: TextView

        init {
            name = itemView.findViewById<View>(R.id.employee_name) as TextView
            role = itemView.findViewById<View>(R.id.employee_role) as TextView
        }
    }

    init {
        mEmployees.addAll(employeeList!!)
    }
}