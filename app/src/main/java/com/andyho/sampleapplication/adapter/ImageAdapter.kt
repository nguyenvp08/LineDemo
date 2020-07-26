package com.andyho.sampleapplication.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andyho.sampleapplication.fragment.ImageFragment

class ImageAdapter constructor(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val imageData = arrayListOf<String>();

    fun setImageList(list: List<String>?) {
        if (list != null) {
            imageData.apply {
                clear()
                addAll(list)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = imageData.count()

    override fun createFragment(position: Int): Fragment {
        return ImageFragment.getInstance(imageData[position])
    }
}