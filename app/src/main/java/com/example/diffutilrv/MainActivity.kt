package com.example.diffutilrv;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EmployeeRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerViewAdapter = new EmployeeRecyclerViewAdapter(
                DummyEmployeeDataUtils.getEmployeeListSortedByRole());
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_name:
                mRecyclerViewAdapter.updateEmployeeListItems(
                        DummyEmployeeDataUtils.getEmployeeListSortedByName());
                return true;
            case R.id.sort_by_role:
                mRecyclerViewAdapter.updateEmployeeListItems(
                        DummyEmployeeDataUtils.getEmployeeListSortedByRole());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
