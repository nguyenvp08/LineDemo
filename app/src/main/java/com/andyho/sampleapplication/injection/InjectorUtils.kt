package com.andyho.sampleapplication.injection

import com.andyho.sampleapplication.viewmodel.ImageViewModel
import com.andyho.sampleapplication.viewmodel.factory.AFragmentFactory
import com.andyho.sampleapplication.viewmodel.factory.ImageFragmentFactory

object InjectorUtils {
    fun provideAViewModelFactory(): AFragmentFactory {
        return AFragmentFactory()
    }

    fun provideImageModelFactory(): ImageFragmentFactory {
        return ImageFragmentFactory()
    }
}