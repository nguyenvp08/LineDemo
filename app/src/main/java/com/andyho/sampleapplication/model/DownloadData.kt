package com.andyho.sampleapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DownloadData(@PrimaryKey val image: String, val filePath: String, val status: String) {

    companion object {
        const val STATUS_DOWNLOADING = "downloading"
        const val STATUS_DOWNLOADED = "downloaded"
    }
}