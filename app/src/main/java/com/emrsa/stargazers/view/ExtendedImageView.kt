package com.emrsa.stargazers.view

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.Image
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.emrsa.stargazers.R
import com.emrsa.stargazers.databinding.FragmentExtendedImageViewBinding


class ExtendedImageView : Fragment() {

    private var _binding: FragmentExtendedImageViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var soundPool: SoundPool
    private lateinit var spaceImageBitmap: Bitmap

    // Stores sound IDs for the guitar and bass
    private var guitarSounds = mutableMapOf<String, Int>()
    private var bassSounds = mutableMapOf<String, Int>()
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtendedImageViewBinding.inflate(inflater, container, false)
        val view = binding.root

        // Load the space image from drawable resource
        loadSpaceImage()

        // Load sounds for guitar and bass
        loadSounds()

        // Handle touch events (finger acting as the mouse cursor)
        binding.spaceImageView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_DOWN) {
                handleTouch(event.x.toInt(), event.y.toInt())
            }
            true
        }

        return view
    }

    private fun loadSpaceImage() {

        val args : ExtendedImageViewArgs by navArgs()
        val position = args.position
        val imageList = ImageDescription().getImageList()

        // Ensure the ImageView dimensions are available after the layout is done
        binding.spaceImageView.post {
            // Get the dimensions of the ImageView
            val imageViewWidth = binding.spaceImageView.width
            val imageViewHeight = binding.spaceImageView.height

            // Set BitmapFactory options to downsample the image
            val options = BitmapFactory.Options().apply {
                // Only decode the image dimensions without loading it into memory
                inJustDecodeBounds = true
            }

            // Load the image dimensions (just decode bounds to get image size)
            BitmapFactory.decodeResource(resources, imageList[position-2], options)

            // Calculate the appropriate sample size based on the ImageView size
            options.inSampleSize = calculateInSampleSize(options, imageViewWidth, imageViewHeight)

            // Now decode the image with the sample size set
            options.inJustDecodeBounds = false
            spaceImageBitmap = BitmapFactory.decodeResource(resources, imageList[position-2], options)

            // Set the scaled bitmap into the ImageView
            binding.spaceImageView.setImageBitmap(spaceImageBitmap)
        }
    }

    /**
     * Helper function to calculate the appropriate sample size for downsampling
     */
    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Get the original width and height of the image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        // If the image is larger than the requested dimensions, calculate the inSampleSize
        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }


    private fun loadSounds() {
        // Initialize SoundPool to handle audio
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(10) // Maximum 10 sounds at once
            .setAudioAttributes(audioAttributes)
            .build()

        // Load guitar and bass sounds into SoundPool
        guitarSounds["a4"] = soundPool.load(context, R.raw.a4, 1)
        bassSounds["a4"] = soundPool.load(context, R.raw.a4sharp, 1)
        // Load other sounds similarly
    }

    private fun handleTouch(x: Int, y: Int) {
        // Ensure that the touch coordinates are within the bounds of the image
        if (x < 0 || y < 0 || x >= spaceImageBitmap.width || y >= spaceImageBitmap.height) {
            return
        }

        // Get the pixel color at the touched coordinates
        val pixelColor = spaceImageBitmap.getPixel(x, y)
        val red = Color.red(pixelColor)
        val green = Color.green(pixelColor)
        val blue = Color.blue(pixelColor)

        // Calculate brightness and map color to sound notes
        val brightness = (red + green + blue) / 3
        val chord = mapColorToFeeling(red, green, blue, brightness)

        // Play guitar and bass sounds based on the touch input
        playSounds(chord)
    }

    private fun playSounds(chord: List<String>) {
        chord.forEach { note ->
            guitarSounds[note]?.let {
                soundPool.play(it, 1f, 1f, 1, 0, 1f)
            }
            bassSounds[note]?.let {
                soundPool.play(it, 1f, 1f, 1, 0, 1f)
            }
        }
    }

    private fun mapColorToFeeling(red: Int, green: Int, blue: Int, brightness: Int): List<String> {
        return when {
            brightness < 50 -> listOf("a4", "g4", "c4", "e4")
            red > 200 && green < 100 && blue < 100 -> listOf("b2", "d3", "f3", "a3")
            green > 200 -> listOf("g3", "b3", "d4", "f4")
            blue > 200 -> listOf("f3", "a3", "c4", "e4")
            brightness > 200 -> listOf("c4", "e4", "g4", "b4")
            else -> listOf("d3", "f3", "a3", "c4")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        soundPool.release()
    }
}