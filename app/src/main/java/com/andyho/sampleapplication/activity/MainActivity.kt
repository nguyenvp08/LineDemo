package com.andyho.sampleapplication.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.andyho.sampleapplication.R
import com.andyho.sampleapplication.databinding.ActivityMainBinding
import com.andyho.sampleapplication.fragment.AFragment

class MainActivity : AppCompatActivity(), OnImageInteractListener {

    private lateinit var binding: ActivityMainBinding

    private var fragment: AFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

         AFragment.getInstance().also {aFragment ->
             fragment = aFragment
             supportFragmentManager.beginTransaction()
                 .add(R.id.contentMain, aFragment)
                 .commit()
        }
    }

    override fun onImageClick() {
        fragment?.nextImage()
    }
}

interface OnImageInteractListener {
    fun onImageClick()
}