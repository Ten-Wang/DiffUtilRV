package com.example.diffutilrv.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.diffutilrv.R
import com.example.diffutilrv.databinding.ActivityMainBinding
import com.example.diffutilrv.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val adapter = EmployeeRecyclerViewAdapter()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_sort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_name -> {
                viewModel.filterDataByName()
                true
            }
            R.id.sort_by_role -> {
                viewModel.filterDataByRole()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupUI() {
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.uiState.observe(this) {
            handleUiState(it)
        }
    }

    private fun handleUiState(uiState: MainUiState) {
        when (uiState) {
            is MainUiState.StateLoading -> Unit
            is MainUiState.StateEmpty -> Unit
            is MainUiState.StateError -> Unit
            is MainUiState.StateLoaded -> {
                adapter.setData(uiState.employees)
            }
        }
    }
}
