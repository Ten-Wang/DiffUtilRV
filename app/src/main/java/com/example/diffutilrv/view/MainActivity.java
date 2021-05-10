package com.example.diffutilrv.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diffutilrv.R;
import com.example.diffutilrv.databinding.ActivityMainBinding;
import com.example.diffutilrv.veiwmodel.EmployeeSortType;
import com.example.diffutilrv.veiwmodel.MainViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    public ActivityMainBinding mBinding;
    private MainViewModel mViewModel;
    @Inject
    public EmployeeRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initView();
        initObserver();
    }

    private void initView() {
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
                mViewModel.updateSortType(EmployeeSortType.NAME);
                return true;
            case R.id.sort_by_role:
                mViewModel.updateSortType(EmployeeSortType.ROLE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
