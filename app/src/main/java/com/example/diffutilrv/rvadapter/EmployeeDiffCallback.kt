package com.example.diffutilrv.rvadapter

import androidx.recyclerview.widget.DiffUtil
import com.example.diffutilrv.model.Employee

class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem == newItem
    }
}