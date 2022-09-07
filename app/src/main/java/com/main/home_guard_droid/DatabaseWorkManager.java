package com.main.home_guard_droid;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DatabaseWorkManager extends Worker {

    //DatabaseConnector databaseConnector = new DatabaseConnector();


    public DatabaseWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        //Boolean flameStatus = MainActivity.getInstance().getFlameStatus();
        //Boolean gasStatus = MainActivity.getInstance().getGasStatus();
        //databaseConnector.getList(context, flameStatus, gasStatus);
        return Result.success();
    }
}
