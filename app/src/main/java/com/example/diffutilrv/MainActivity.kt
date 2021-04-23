package com.example.diffutilrv

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.model.EmployeeListOrder
import com.example.diffutilrv.repo.DummyEmployeeDataRepository
import com.example.diffutilrv.rvadapter.EmployeeRecyclerViewAdapter
import com.example.diffutilrv.viewmodel.EmployeeListViewModel
import com.example.diffutilrv.viewmodel.EmployeeViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ContentLoadingProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmployeeRecyclerViewAdapter

    private lateinit var viewModel: EmployeeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        initRecyclerView()
        initViewModel()
        fetchList()
    }

    private fun findViews() {
        progressBar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.recycler_view)
    }

    private fun initRecyclerView() {
        adapter = EmployeeRecyclerViewAdapter(
            workerThreadExecutor = Dispatchers.IO.asExecutor(),
            clickListener = ::onEmployeeClicked
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            EmployeeViewModelFactory(DummyEmployeeDataRepository())
        ).get(EmployeeListViewModel::class.java)

        viewModel.result.observe(this, { result ->
            result.onSuccess(adapter::submitList)
            result.onFailure(::onFetchError)
        })

        viewModel.isFetching.observe(this, ::onFetchingStateChanged)
    }

    private fun fetchList(listOrder: EmployeeListOrder? = null) {
        viewModel.fetch(listOrder)
    }

    private fun onFetchError(throwable: Throwable) {
        Toast.makeText(this, "Error: ${throwable.message}", Toast.LENGTH_SHORT).show()
    }

    private fun onFetchingStateChanged(isFetching: Boolean) {
        if (recyclerView.childCount > 0) {
            progressBar.hide()
            return
        }

        if (isFetching) {
            progressBar.show()
        } else {
            progressBar.hide()
        }
    }

    private fun onEmployeeClicked(employee: Employee, position: Int) {
        Toast.makeText(this, "Click: $employee at position[$position]", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_name -> {
                fetchList(EmployeeListOrder.SORT_BY_NAME)
                return true
            }
            R.id.sort_by_role -> {
                fetchList(EmployeeListOrder.SORT_BY_ROLE)
                return true
            }
            R.id.sort_by_cost -> {
                fetchList(EmployeeListOrder.SORT_BY_COST)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}