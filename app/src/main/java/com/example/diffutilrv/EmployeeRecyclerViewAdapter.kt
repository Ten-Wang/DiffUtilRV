package com.example.diffutilrv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.databinding.ListItemBinding

class EmployeeRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner,
) : ListAdapter<Employee, EmployeeRecyclerViewAdapter.ViewHolder>(EmployeeDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            binding = ListItemBinding.inflate(inflater, parent, false),
            lifecycleOwner = lifecycleOwner,
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
    }

    class ViewHolder(
        private val binding: ListItemBinding,
        private val lifecycleOwner: LifecycleOwner,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee) {
            binding.lifecycleOwner = lifecycleOwner
            binding.employee = employee
        }
    }
}