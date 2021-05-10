package com.example.diffutilrv.hilt;

import android.app.Activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;

import com.example.diffutilrv.R;
import com.example.diffutilrv.databinding.ActivityMainBinding;
import com.example.diffutilrv.model.Employee;
import com.example.diffutilrv.view.EmployeeDiffItemCallbackImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class ActivityModule {

    @Provides
    public static ActivityMainBinding providerActivityMainBinding(Activity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_main);
    }

    @Binds
    public abstract DiffUtil.ItemCallback<Employee> bindsEmployeeDiffItemCallback(EmployeeDiffItemCallbackImpl employeeDiffItemCallback);

}
