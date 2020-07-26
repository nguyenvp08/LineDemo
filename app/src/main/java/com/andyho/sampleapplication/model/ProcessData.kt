package com.andyho.sampleapplication.model

import android.graphics.Bitmap

data class ProcessData(val process: Int, val bitmap: Bitmap?, val done: Boolean = false)