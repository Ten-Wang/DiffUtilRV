package com.example.diffutilrv

import androidx.recyclerview.widget.DiffUtil

object EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean = oldItem == newItem
}