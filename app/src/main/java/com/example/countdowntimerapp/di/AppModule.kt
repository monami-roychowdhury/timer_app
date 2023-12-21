package com.example.countdowntimerapp.di

import androidx.work.WorkManager
import com.example.countdowntimerapp.viewmodel.TimerViewModel
import com.example.countdowntimerapp.worker.NotificationWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val appModule = module {
    worker {
        NotificationWorker(get(), get())
    }
    single {
        WorkManager.getInstance(androidContext())
    }
    viewModel {
        TimerViewModel(get())
    }
}