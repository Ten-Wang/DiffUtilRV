package com.example.diffutilrv

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diffutilrv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: EmployeeRecyclerViewAdapter
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerViewAdapter = EmployeeRecyclerViewAdapter(this)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerViewAdapter
        }
        viewModel.employees.observe(this) { recyclerViewAdapter.submitList(it) }
        if (viewModel.employees.value.orEmpty().isEmpty()) {
            viewModel.loadData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.sort_by_name -> {
            viewModel.changeSorting(EmployeeSortBy.NAME)
            true
        }
        R.id.sort_by_role -> {
            viewModel.changeSorting(EmployeeSortBy.ROLE)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
