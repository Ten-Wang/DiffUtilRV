package com.example.diffutilrv

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.diffutilrv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val adapter = EmployeeRecyclerViewAdapter()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_sort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_name -> {
                //TODO: sort by name
                true
            }
            R.id.sort_by_role -> {
                //TODO: sort by role
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
