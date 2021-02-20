package com.jordanro.stackoverflow.di

import android.app.Activity
import com.jordanro.stackoverflow.application.NavigationComponent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @Provides
    fun provideCallback(activity: Activity) =
        activity as NavigationComponent

}