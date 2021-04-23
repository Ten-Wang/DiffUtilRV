package com.example.diffutilrv.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.example.diffutilrv.R
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.rvadapter.viewholder.ViewHolder
import java.util.concurrent.Executor

class EmployeeRecyclerViewAdapter(
    workerThreadExecutor: Executor
) :
    ListAdapter<Employee, ViewHolder>(
        AsyncDifferConfig.Builder(EmployeeDiffCallback())
            .setBackgroundThreadExecutor(workerThreadExecutor)
            .build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
    }
}