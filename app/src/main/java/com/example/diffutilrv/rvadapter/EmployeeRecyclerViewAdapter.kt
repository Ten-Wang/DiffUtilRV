package com.example.diffutilrv.rvadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.rvadapter.viewholder.ViewHolder
import com.example.diffutilrv.uimodel.EmployeeCheckbox
import java.util.concurrent.Executor

class EmployeeRecyclerViewAdapter(
    workerThreadExecutor: Executor,
    private val onCheckedChangeListener: (EmployeeCheckbox, position: Int, isChecked: Boolean) -> Unit
) :
    ListAdapter<EmployeeCheckbox, ViewHolder>(
        AsyncDifferConfig.Builder(EmployeeDiffCallback())
            .setBackgroundThreadExecutor(workerThreadExecutor)
            .build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(
            parent,
            onCheckedChangeListener = { adapterPosition, isChecked ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onCheckedChangeListener(getItem(adapterPosition), adapterPosition, isChecked)
                }
            })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        holder.onPartialUpdate(payloads)
    }
}