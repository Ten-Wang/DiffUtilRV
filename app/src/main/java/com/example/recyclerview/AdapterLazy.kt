package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil

inline fun <reified T : ListItem> AppCompatActivity.recyclerViewAdapter(
    itemVariableId: Int,
    diffUtilItemCallback: DiffUtil.ItemCallback<T> = SimpleDiffUtilItemCallback(),
): Lazy<RecyclerViewAdapter<T>> {
    return lazy { RecyclerViewAdapter(itemVariableId, this, diffUtilItemCallback) }
}
