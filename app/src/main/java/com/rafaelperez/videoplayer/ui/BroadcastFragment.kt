package com.rafaelperez.videoplayer.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rafaelperez.videoplayer.R
import com.rafaelperez.videoplayer.databinding.FragmentBroadcastBinding


class BroadcastFragment : FullScreenFragment() {
    private lateinit var binding: FragmentBroadcastBinding

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_broadcast, container, false)
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
        val chatView = binding.chatBroadcast
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        chatView.layoutParams.height = metrics.heightPixels/2
        chatView.requestLayout()
        setSystemUIListener()

        binding.closeButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            Runnable {
                val cameraProvider = cameraProviderFuture.get()

                val preview : Preview = Preview.Builder().build()

                val cameraSelector : CameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                    .build()

                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
                preview.setSurfaceProvider(binding.previewView.createSurfaceProvider(camera.cameraInfo))
                binding.chatBroadcast.initTimer()
            }, ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireActivity().baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Snackbar.make(requireView(), "Permissions not granted by the user.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setSystemUIListener() {
        activity?.window?.decorView?.setOnSystemUiVisibilityChangeListener { visibility ->
            val layoutParams = view?.layoutParams as FrameLayout.LayoutParams?
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                // The system bars are visible
                layoutParams?.bottomMargin = resources.getDimension(R.dimen.full_screen_bottom_margin_space).toInt()
            } else {
                layoutParams?.bottomMargin = 0
            }
            view?.layoutParams = layoutParams
        }
    }
}