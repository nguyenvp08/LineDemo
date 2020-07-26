package com.andyho.sampleapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andyho.sampleapplication.SampleApplication
import com.andyho.sampleapplication.model.ImageData
import com.andyho.sampleapplication.network.DataRepository
import io.reactivex.disposables.CompositeDisposable

class AViewModel : ViewModel() {
    val data: MutableLiveData<ImageData> = MutableLiveData<ImageData>()
    val compositeSubscription = CompositeDisposable()

    init {
        startLoadingData()
    }

    fun startLoadingData() {
        compositeSubscription.add(DataRepository.getImageData(SampleApplication.INSTANCE)
            .subscribe({
                it?.let {
                    data.value = it
                }
            }, {
            }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeSubscription.dispose()
    }
}