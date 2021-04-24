package com.example.diffutilrv.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diffutilrv.R
import com.example.diffutilrv.data.EmployeeSortBy
import com.example.diffutilrv.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: EmployeeRecyclerViewAdapter
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerViewAdapter = EmployeeRecyclerViewAdapter(this, viewModel)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerViewAdapter
        }
        observeLiveData()
        if (viewModel.employees.value.orEmpty().isEmpty()) {
            viewModel.loadData()
        }
    }

    private fun observeLiveData() {
        viewModel.employees.observe(this) { recyclerViewAdapter.submitList(it) }
        viewModel.clickRowAction.observe(this) {
            it?.runIfNotConsumed { employee ->
                Toast.makeText(this, getString(R.string.row_clicked_message, employee.name), Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.clickRowButtonAction.observe(this) {
            it?.runIfNotConsumed { employee ->
                Toast.makeText(this, getString(R.string.row_button_clicked_message, employee.name), Toast.LENGTH_SHORT)
                    .show()
            }
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
