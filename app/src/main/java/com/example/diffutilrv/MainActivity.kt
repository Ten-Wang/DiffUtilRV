package com.example.diffutilrv

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: EmployeeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewAdapter = EmployeeRecyclerViewAdapter(DummyEmployeeDataUtils.getEmployeeListSortedByRole())
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.sort_by_name -> {
            recyclerViewAdapter.updateEmployeeListItems(DummyEmployeeDataUtils.getEmployeeListSortedByName())
            true
        }
        R.id.sort_by_role -> {
            recyclerViewAdapter.updateEmployeeListItems(DummyEmployeeDataUtils.getEmployeeListSortedByRole())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}