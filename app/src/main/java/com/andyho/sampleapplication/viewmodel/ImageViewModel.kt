package com.andyho.sampleapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andyho.sampleapplication.SampleApplication
import com.andyho.sampleapplication.model.ProcessData
import com.andyho.sampleapplication.network.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.io.File

class ImageViewModel : ViewModel() {

    val compositeSubscription = CompositeDisposable()

    private var image: String? = null

    val startDownloadLiveData = MutableLiveData<Boolean>()

    fun setImage(image: String?) {
        this.image = image
        startLoadingImage()
    }

    fun startLoadingImage() {
        startDownloadLiveData.value = true
        image?.let {image ->
            compositeSubscription.add(DataRepository.checkImageLoadedYet(image)
                .subscribe({bitmap ->
                    if (bitmap != null) {
                        startDownloadLiveData.postValue(false)
                    } else {
                        downloadImage(image)
                    }
                }, {
                    downloadImage(image)
                }))
        }
    }

    fun downloadImage(imagePath: String) {
        DataRepository.loadImage(imagePath, getFilePathForImage(imagePath))?.apply {
            subscribeOn(AndroidSchedulers.mainThread())
            compositeSubscription.add(
            subscribe({latestDownloadData ->
                if (latestDownloadData?.done == true) {
                    startDownloadLiveData.postValue(false)
                }
            }, {
                startDownloadLiveData.postValue(false)
            }))
        }
    }

    fun getFilePathForImage(image: String) : String {
        val app = SampleApplication.INSTANCE
        val folder = File(app.filesDir, "cache")
        folder.mkdirs()
        return File(folder, "${System.currentTimeMillis()}.jpg").absolutePath
    }

    fun getLatestDownloadData(): ProcessData? {
        return DataRepository.map.get(image)?.latestProcessData
    }

    fun clearBitmapFromPool() {
        image?.let {image ->
            DataRepository.clearBitmapWhenFromPool(image)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeSubscription.dispose()
    }
}