package com.example.diffutilrv.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diffutilrv.R;
import com.example.diffutilrv.model.Employee;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class EmployeeRecyclerViewAdapter extends ListAdapter<Employee, EmployeeRecyclerViewAdapter.ViewHolder> {

    @Inject
    protected EmployeeRecyclerViewAdapter(DiffUtil.ItemCallback<Employee> diffCallback) {
        super(diffCallback);
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeRecyclerViewAdapter.ViewHolder holder, int position) {
        final Employee employee = getItem(position);
        holder.name.setText(employee.getName());
        holder.role.setText(employee.getRole());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView role;
        private final TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.employee_name);
            role = itemView.findViewById(R.id.employee_role);
        }
    }
}
