package com.example.diffutilrv.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diffutilrv.R
import com.example.diffutilrv.databinding.ActivityEmployeeBinding
import com.example.diffutilrv.uistate.UiState
import com.example.diffutilrv.viewmodel.EmployeeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmployeeActivity : AppCompatActivity() {

    private val viewModel by viewModel<EmployeeViewModel>()
    private val adapter = EmployeeAdapter()

    private lateinit var binding: ActivityEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
        setUpViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_name -> {
                viewModel.fetchEmployeeListByName()
                true
            }
            R.id.sort_by_role -> {
                viewModel.fetchEmployeeListByRole()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpUi() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setUpViewModel() {
        viewModel.employeeList.observe(this, Observer { uiState ->
            when (uiState) {
                is UiState.ShowLoading -> binding.progressBar.isVisible = true
                is UiState.HideLoading -> binding.progressBar.isInvisible = true
                is UiState.Success -> adapter.submitList(uiState.data)
                is UiState.Empty -> Toast.makeText(this, "尚無資料", Toast.LENGTH_SHORT).show()
                is UiState.Error -> Toast.makeText(this, "資料錯誤", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.fetchEmployeeListByName()
    }
}