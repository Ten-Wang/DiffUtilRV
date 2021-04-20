package com.example.diffutilrv

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.repo.DummyEmployeeDataUtils
import com.example.diffutilrv.rvadapter.EmployeeRecyclerViewAdapter
import com.example.diffutilrv.viewmodel.EmployeeListViewModel
import com.example.diffutilrv.viewmodel.EmployeeViewModelFactory

class MainActivity : AppCompatActivity() {
    companion object {
        private val DEFAULT_LIST_ORDER = EmployeeListViewModel.ListOrder.SORT_BY_ROLE
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmployeeRecyclerViewAdapter

    private lateinit var viewModel: EmployeeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initViewModel()
        fetchList(DEFAULT_LIST_ORDER)
    }

    private fun initViews() {
        adapter = EmployeeRecyclerViewAdapter()
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            EmployeeViewModelFactory()
        ).get(EmployeeListViewModel::class.java)

        viewModel.list.observe(
            this,
            Observer<List<Employee>> { list ->
                adapter.updateEmployeeListItems(list)
            })
    }

    private fun fetchList(listOrder: EmployeeListViewModel.ListOrder) {
        viewModel.fetch(listOrder)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_name -> {
                fetchList(EmployeeListViewModel.ListOrder.SORT_BY_NAME)
                return true
            }
            R.id.sort_by_role -> {
                fetchList(EmployeeListViewModel.ListOrder.SORT_BY_ROLE)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}