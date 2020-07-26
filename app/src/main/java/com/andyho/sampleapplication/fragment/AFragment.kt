package com.andyho.sampleapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.andyho.sampleapplication.adapter.ImageAdapter
import com.andyho.sampleapplication.databinding.AFragmentLayoutBinding
import com.andyho.sampleapplication.injection.InjectorUtils
import com.andyho.sampleapplication.model.ImageData
import com.andyho.sampleapplication.viewmodel.AViewModel

class AFragment : Fragment() {
    private val aViewModel : AViewModel by viewModels {
        InjectorUtils.provideAViewModelFactory()
    }

    private lateinit var binding: AFragmentLayoutBinding
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AFragmentLayoutBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        aViewModel.data.observe(AFragment@this, Observer {
            it?.let {
                displayImageData(it)
            }
        })
    }

    fun nextImage() {
        val size = adapter.itemCount
        val nextPostion = binding.viewPager.currentItem + 1
        binding.viewPager.currentItem = nextPostion % size
    }

    private fun displayImageData(imageData: ImageData) {
        activity?.let{act ->
            binding.text.text = imageData.title
            adapter = ImageAdapter(act)
            adapter.setImageList(imageData.image)
            binding.viewPager.adapter = adapter
        }
    }

    companion object {
        fun getInstance() = AFragment()
    }
}