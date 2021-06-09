package com.future.mvvm_dagger

import com.future.mvvm_dagger.di.ActivityScope
import dagger.Component
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface LoginComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(activity: MainActivity)
}