package com.example.diffutilrv.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.R
import com.example.diffutilrv.databinding.ItemEmployeeBinding
import com.example.diffutilrv.model.Employee

class EmployeeRecyclerViewAdapter :
    ListAdapter<Employee, EmployeeRecyclerViewAdapter.EmployeeViewHolder>(EmployeeDiffItemCallback()) {

    private var itemList: List<Employee> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun submitList(list: List<Employee>?) {
        super.submitList(list)
        itemList = list ?: emptyList()
    }

    override fun getItemCount(): Int = itemList.count()

    class EmployeeViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemEmployeeBinding.bind(itemView)

        fun bind(employee: Employee) {
            binding.employeeName.text = employee.name
            binding.employeeRole.text = employee.role
        }

        companion object {
            fun create(parent: ViewGroup): EmployeeViewHolder {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_employee, parent, false)
                return EmployeeViewHolder(view)
            }
        }
    }


    class EmployeeDiffItemCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.name == newItem.name
        }
    }
}