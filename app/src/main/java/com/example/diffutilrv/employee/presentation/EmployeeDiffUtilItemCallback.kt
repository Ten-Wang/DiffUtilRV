package com.example.diffutilrv.employee.presentation

import androidx.recyclerview.widget.DiffUtil

class EmployeeDiffUtilItemCallback : DiffUtil.ItemCallback<EmployeeListItem>() {

    override fun areItemsTheSame(oldItem: EmployeeListItem, newItem: EmployeeListItem): Boolean {
        return oldItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: EmployeeListItem, newItem: EmployeeListItem): Boolean {
        return oldItem == newItem
    }
}