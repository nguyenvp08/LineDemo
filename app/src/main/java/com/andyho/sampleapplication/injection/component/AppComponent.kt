package com.andyho.sampleapplication.injection.component

import android.app.Application
import com.andyho.sampleapplication.injection.module.AppModule
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun application(): Application?
}