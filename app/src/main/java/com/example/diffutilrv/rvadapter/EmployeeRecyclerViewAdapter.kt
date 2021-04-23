package com.example.diffutilrv.rvadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.rvadapter.viewholder.ViewHolder
import java.util.concurrent.Executor

class EmployeeRecyclerViewAdapter(
    workerThreadExecutor: Executor,
    private val clickListener: (Employee, position: Int) -> Unit
) :
    ListAdapter<Employee, ViewHolder>(
        AsyncDifferConfig.Builder(EmployeeDiffCallback())
            .setBackgroundThreadExecutor(workerThreadExecutor)
            .build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(parent, clickListener = { adapterPosition ->
            if (adapterPosition != RecyclerView.NO_POSITION) {
                clickListener(getItem(adapterPosition), adapterPosition)
            }
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
    }
}