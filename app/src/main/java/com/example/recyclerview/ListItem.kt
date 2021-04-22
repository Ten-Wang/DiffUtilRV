package com.example.recyclerview

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil

interface ListItem {

    @get:LayoutRes
    val layoutId: Int
}

/**
 * Default DiffUtil.ItemCallback
 */
class SimpleDiffUtilItemCallback<T : ListItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}