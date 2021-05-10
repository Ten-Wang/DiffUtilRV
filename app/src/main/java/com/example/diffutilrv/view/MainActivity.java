package com.example.diffutilrv.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diffutilrv.R;
import com.example.diffutilrv.databinding.ActivityMainBinding;
import com.example.diffutilrv.veiwmodel.EmployeeSortType;
import com.example.diffutilrv.veiwmodel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private MainViewModel mViewModel;

    private EmployeeRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initView();
        initObserver();
    }

    private void initView() {
        this.mRecyclerViewAdapter = new EmployeeRecyclerViewAdapter();
        this.mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mBinding.recyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void initObserver() {
        mViewModel.getEmployeeListLiveData().observe(this, employeeList -> mRecyclerViewAdapter.updateEmployeeListItems(employeeList));
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
                mViewModel.upDataSortType(EmployeeSortType.NAME);
                return true;
            case R.id.sort_by_role:
                mViewModel.upDataSortType(EmployeeSortType.ROLE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
