package com.andyho.sampleapplication.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.andyho.sampleapplication.activity.OnImageInteractListener
import com.andyho.sampleapplication.databinding.ImageFragmentLayoutBinding
import com.andyho.sampleapplication.injection.InjectorUtils
import com.andyho.sampleapplication.network.DataRepository
import com.andyho.sampleapplication.viewmodel.ImageViewModel

class ImageFragment : Fragment() {

    private lateinit var binding: ImageFragmentLayoutBinding

    private val viewModel: ImageViewModel by viewModels {
        InjectorUtils.provideImageModelFactory()
    }

    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImageFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.image.setOnClickListener {
            if (activity is OnImageInteractListener) {
                (activity as OnImageInteractListener).onImageClick()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.startDownloadLiveData.observe(ImageFragment@this, Observer{
            if (it == true) {
                binding.progress.visibility = View.VISIBLE
                binding.progress.text = "000 kb downloading"
                startTimer()
            } else {
                timer?.cancel()
                displayDownloadedImage()
                binding.progress.visibility = View.GONE
            }
        })
        viewModel.setImage(arguments?.getString(EXTRA_IMAGE_PATH))
    }

    private fun displayDownloadedImage() {
        viewModel.getLatestDownloadData()?.apply {
            if (bitmap != null) {
                binding.image.setImageBitmap(bitmap)
            }
        }
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(Long.MAX_VALUE, TIME_INTERVAL_1S) {
            override fun onFinish() {
            }

            override fun onTick(p0: Long) {
                viewModel.getLatestDownloadData()?.let {
                    binding.progress.text = "${it.process / 1024} kb downloading"
                }
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
        binding.image.setImageBitmap(null)
        binding.image.setImageResource(0)

        // clear bitmap from cache
        viewModel.clearBitmapFromPool()
    }

    companion object {
        private const val EXTRA_IMAGE_PATH = "image_path"
        private const val TIME_INTERVAL_1S = 1000L

        fun getInstance(image: String) = ImageFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_IMAGE_PATH, image)
            }
        }
    }
}