package com.future.mvvm_dagger

import android.app.Application
import com.future.mvvm_dagger.di.AppComponent
import com.future.mvvm_dagger.di.DaggerAppComponent

open class MyApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}