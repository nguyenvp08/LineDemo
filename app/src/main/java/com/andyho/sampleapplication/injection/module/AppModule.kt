package com.andyho.sampleapplication.injection.module

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule  {
    private var application: Application? = null

    fun AppModule(application: Application?) {
        this.application = application
    }

    @Provides
    fun provideApplication(): Application? {
        return application
    }
}