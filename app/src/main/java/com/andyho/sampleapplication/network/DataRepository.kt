package com.andyho.sampleapplication.network

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.andyho.sampleapplication.SampleApplication
import com.andyho.sampleapplication.database.AppRoomDatabase
import com.andyho.sampleapplication.model.DownloadData
import com.andyho.sampleapplication.model.DownloadData.Companion.STATUS_DOWNLOADED
import com.andyho.sampleapplication.model.DownloadData.Companion.STATUS_DOWNLOADING
import com.andyho.sampleapplication.model.ImageData
import com.andyho.sampleapplication.model.ProcessData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object DataRepository {
    private const val DATA_FILENAME = "data.json"

    val map = mutableMapOf<String, LatestProcessData?>()

    @SuppressLint("CheckResult")
    fun getImageData(context: Context): Single<ImageData?> {
        return Single.fromCallable({
            val fromDataBase = AppRoomDatabase.getInstance(context).dao?.getImageData()
            if (fromDataBase == null) {
                return@fromCallable getImageDataFromAsset(context)
            }
            return@fromCallable fromDataBase
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getImageDataFromAsset(context: Context): ImageData? {
        try {
            context.assets.open(DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val dataType = object : TypeToken<ImageData>() {}.type
                    val data: ImageData = Gson().fromJson(jsonReader, dataType)

                    val database = AppRoomDatabase.getInstance(context)
                    database.dao?.insert(data)
                    return data
                }
            }
        } catch (ex: Exception) {
            // Ignore
        }
        return null
    }

    /*
        Loading with HttpUrlConnection (no external library)
        And post Update
     */
    fun loadImage(imagePath: String, filePath: String): PublishSubject<ProcessData>? {
        if (!map.containsKey(imagePath)) {
            map.put(imagePath, LatestProcessData(null, PublishSubject.create()))
            Observable.create<ProcessData> {
                val url = URL(imagePath)
                val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                try {
                    val inputStream = BufferedInputStream(urlConnection.getInputStream())
                    // if filePath is null => there is no permission
                    // read the file and compress into bitmap...
                    // if there is filePath => read buffer and write into file
                    val buffer = ByteArray(8 * 1024)
                    var outputStream = BufferedOutputStream(FileOutputStream(filePath))

                    var count = 0
                    AppRoomDatabase.getInstance(SampleApplication.INSTANCE).dao
                        ?.insert(DownloadData(imagePath, filePath, STATUS_DOWNLOADING))
                    while (true) {
                        val read = inputStream.read(buffer, 0, buffer.size)
                        if (read != -1) {
                            outputStream.write(buffer, 0, read)
                            count += read
                            val processData = ProcessData(count, null)
                            map.get(imagePath)?.publishSubject?.onNext(processData)
                            map.get(imagePath)?.latestProcessData = processData
                        } else {
                            break
                        }
                    }

                    inputStream.close()
                    outputStream.flush()
                    outputStream.close()

                    // done
                    val processData = ProcessData(count, BitmapFactory.decodeFile(filePath), true)
                    map.get(imagePath)?.publishSubject?.onNext(processData)
                    map.get(imagePath)?.publishSubject?.onComplete()
                    map.get(imagePath)?.latestProcessData = processData
                    AppRoomDatabase.getInstance(SampleApplication.INSTANCE).dao
                        ?.insert(DownloadData(imagePath, filePath, STATUS_DOWNLOADED))

                } catch(e: Throwable) {
                    try {
                        if (!it.isDisposed) {
                            it.onError(e)
                        }
                    } catch (e: Throwable) {
                        // Ignore
                    }
                } finally {
                    urlConnection.disconnect()
                }
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }

        return map.get(imagePath)?.publishSubject
    }

    fun checkImageLoadedYet(imagePath: String) : Single<Bitmap?> {
        return Single.fromCallable {
            if (map.containsKey(imagePath)) {
                val bitmap = map.get(imagePath)?.latestProcessData?.bitmap
                if (bitmap != null) {
                    return@fromCallable bitmap
                }
            }

            // check from db
            val fromDatabase = AppRoomDatabase.getInstance(SampleApplication.INSTANCE)
                .dao?.getDownloadData(imagePath)
            if (fromDatabase != null && fromDatabase.status == STATUS_DOWNLOADED) {
                val bitmap = BitmapFactory.decodeFile(fromDatabase.filePath)
                map.put(imagePath, LatestProcessData(ProcessData(0, bitmap, true), PublishSubject.create()))

                return@fromCallable bitmap
            }

            return@fromCallable null
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun clearBitmapWhenFromPool(imagePath: String) {
        // clear bitmap from pool
        Log.d("NHN", "Clear Image : $imagePath")
        if (map.containsKey(imagePath) && map.get(imagePath)?.latestProcessData?.bitmap != null) {
            map.put(imagePath, null)
        }
    }

    data class LatestProcessData(var latestProcessData: ProcessData?, val publishSubject: PublishSubject<ProcessData>)

}