package com.example.diffutilrv.rvadapter

import androidx.recyclerview.widget.DiffUtil
import com.example.diffutilrv.rvadapter.payload.CheckStateChange
import com.example.diffutilrv.uimodel.EmployeeCheckbox

class EmployeeDiffCallback : DiffUtil.ItemCallback<EmployeeCheckbox>() {
    override fun areItemsTheSame(oldItem: EmployeeCheckbox, newItem: EmployeeCheckbox): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EmployeeCheckbox, newItem: EmployeeCheckbox): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: EmployeeCheckbox, newItem: EmployeeCheckbox): Any? {
        if (oldItem.employee == newItem.employee && oldItem.isChecked != newItem.isChecked) {
            return CheckStateChange(newItem.isChecked)
        }
        return null
    }
}