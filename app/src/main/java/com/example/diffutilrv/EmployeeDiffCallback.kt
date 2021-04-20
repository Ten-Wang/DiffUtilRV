package com.example.diffutilrv

import androidx.recyclerview.widget.DiffUtil

class EmployeeDiffCallback(
    private val oldEmployeeList: List<Employee>,
    private val newEmployeeList: List<Employee>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return newEmployeeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldEmployeeList[oldItemPosition].id == newEmployeeList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = oldEmployeeList[oldItemPosition]
        val newEmployee = newEmployeeList[newItemPosition]
        return oldEmployee.name == newEmployee.name
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}