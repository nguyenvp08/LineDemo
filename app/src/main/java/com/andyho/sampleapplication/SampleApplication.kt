package com.andyho.sampleapplication

import androidx.multidex.MultiDexApplication
import com.andyho.sampleapplication.injection.component.AppComponent
import com.andyho.sampleapplication.injection.component.DaggerAppComponent

class SampleApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this;
    }

    companion object {
        lateinit var INSTANCE: SampleApplication
    }
}