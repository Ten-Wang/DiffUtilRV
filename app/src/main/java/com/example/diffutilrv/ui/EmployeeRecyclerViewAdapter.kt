package com.example.diffutilrv.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.data.Employee

class EmployeeRecyclerViewAdapter : ListAdapter<Employee, RecyclerView.ViewHolder>(EMPLOYEE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EmployeeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EmployeeViewHolder).bind(getItem(position))
    }

    companion object {
        private val EMPLOYEE_COMPARATOR = object : DiffUtil.ItemCallback<Employee>() {
            override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean =
                oldItem.name == newItem.name
        }
    }
}
