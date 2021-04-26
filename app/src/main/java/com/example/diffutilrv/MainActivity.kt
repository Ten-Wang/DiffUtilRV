package com.example.diffutilrv

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.diffutilrv.databinding.ActivityMainBinding
import com.example.diffutilrv.employee.presentation.EmployeeDiffUtilItemCallback
import com.example.diffutilrv.employee.presentation.EmployeeViewModel
import com.example.recyclerview.recyclerViewAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<EmployeeViewModel>()
    private val adapter by recyclerViewAdapter(BR.item, EmployeeDiffUtilItemCallback())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater)
            .also { setupViews(it) }
            .let { setContentView(it.root)
        }

        setupObservers()
        viewModel.sortByName()
    }

    private fun setupViews(binding: ActivityMainBinding) {
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.list.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_name -> viewModel.sortByName().let { true }
            R.id.sort_by_role -> viewModel.sortByRole().let { true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}