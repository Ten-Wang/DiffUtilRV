package com.example.diffutilrv.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.diffutilrv.model.Employee;

import javax.inject.Inject;

public class EmployeeDiffItemCallbackImpl extends DiffUtil.ItemCallback<Employee> {

    @Inject
    public EmployeeDiffItemCallbackImpl() {
    }

    @Override
    public boolean areItemsTheSame(@NonNull Employee oldItem, @NonNull Employee newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Employee oldItem, @NonNull Employee newItem) {
        return oldItem.getName().equals(newItem.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(@NonNull Employee oldItem, @NonNull Employee newItem) {
        return super.getChangePayload(oldItem, newItem);
    }
}
