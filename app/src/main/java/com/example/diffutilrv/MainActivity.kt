package com.example.diffutilrv

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.DummyEmployeeDataUtils.employeeListSortedByName
import com.example.diffutilrv.DummyEmployeeDataUtils.employeeListSortedByRole

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRecyclerViewAdapter: EmployeeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerViewAdapter = EmployeeRecyclerViewAdapter(
            employeeListSortedByRole
        )
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mRecyclerViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_name -> {
                mRecyclerViewAdapter.updateEmployeeListItems(
                    employeeListSortedByName
                )
                return true
            }
            R.id.sort_by_role -> {
                mRecyclerViewAdapter.updateEmployeeListItems(
                    employeeListSortedByRole
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}