package com.example.diffutilrv

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.repo.DummyEmployeeDataUtils
import com.example.diffutilrv.rvadapter.EmployeeRecyclerViewAdapter
import com.example.diffutilrv.viewmodel.EmployeeListViewModel
import com.example.diffutilrv.viewmodel.EmployeeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EmployeeListViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmployeeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            EmployeeViewModelFactory()
        ).get(EmployeeListViewModel::class.java)
    }

    private fun initViews() {
        adapter = EmployeeRecyclerViewAdapter(
            DummyEmployeeDataUtils.employeeListSortedByRole
        )
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_name -> {
                adapter.updateEmployeeListItems(
                    DummyEmployeeDataUtils.employeeListSortedByName
                )
                return true
            }
            R.id.sort_by_role -> {
                adapter.updateEmployeeListItems(
                    DummyEmployeeDataUtils.employeeListSortedByRole
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}