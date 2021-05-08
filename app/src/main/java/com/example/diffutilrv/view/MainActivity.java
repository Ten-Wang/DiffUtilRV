package com.example.diffutilrv.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diffutilrv.R;
import com.example.diffutilrv.veiwmodel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EmployeeRecyclerViewAdapter mRecyclerViewAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mRecyclerViewAdapter = new EmployeeRecyclerViewAdapter(this.mViewModel.getEmployeeListSortedByRole());
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_name:
                mRecyclerViewAdapter.updateEmployeeListItems(this.mViewModel.getEmployeeListSortedByName());
                return true;
            case R.id.sort_by_role:
                mRecyclerViewAdapter.updateEmployeeListItems(this.mViewModel.getEmployeeListSortedByRole());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
