package com.example.diffutilrv.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.diffutilrv.data.Employee

object EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean = oldItem == newItem
}