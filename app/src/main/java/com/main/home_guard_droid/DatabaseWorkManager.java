package com.main.home_guard_droid;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DatabaseWorkManager extends Worker {

    private Context context;
    DatabaseConnector databaseConnector = new DatabaseConnector();

    public DatabaseWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        databaseConnector.getList(context);
        return Result.success();
    }
}
