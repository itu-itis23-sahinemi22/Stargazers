package com.emrsa.stargazers.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.emrsa.stargazers.ListItem
import com.emrsa.stargazers.R
import com.emrsa.stargazers.databinding.FragmentImageDescriptionBinding

class ImageDescription : Fragment() {
    private var _binding: FragmentImageDescriptionBinding? = null
    private val binding get() = _binding!!

    private val imageList = mutableListOf(
        R.drawable.image1
        ,R.drawable.image2
        ,R.drawable.image3
        ,R.drawable.image4
        ,R.drawable.image5
        ,R.drawable.image6
        ,R.drawable.image7
        ,R.drawable.image8
    )
    private val textList = mutableListOf(
        "This image of NGC 5468, a galaxy located about 130 million light-years from Earth, combines data from the Hubble and James Webb space telescopes. This is the farthest galaxy in which Hubble has identified Cepheid variable stars.",
        "An active star-forming region. Red dual opposing jets coming from young stars fill the darker top half of the image, while a glowing pale-yellow, cave-like structure is bottom center, tilted toward two o’clock, with a bright star at its center.",
        "NASA’s James Webb Space Telescope has observed the well-known Ring Nebula in unprecedented detail. Formed by a star throwing off its outer layers as it runs out of fuel, the Ring Nebula is an archetypal planetary nebula.",
        "NASA’s James Webb Space Telescope has gazed at the Crab Nebula in the search for answers about the supernova remnant’s origins. Webb’s NIRCam (Near-Infrared Camera) and MIRI (Mid-Infrared Instrument) have revealed new details in infrared light.",
        "This image from the NIRCam (Near-Infrared Camera) instrument on NASA’s James Webb Space Telescope shows the central portion of the star cluster IC 348.",
        "NASA’s James Webb Space Telescope’s high resolution, near-infrared look at Herbig-Haro 211 reveals exquisite detail of the outflow of a young star, an infantile analogue of our Sun."
        ,"This image of Comet 238P/Read was captured by the NIRCam (Near-Infrared Camera) instrument on NASA’s James Webb Space Telescope on September 8, 2022. It displays the hazy halo, called the coma, and tail that are characteristic of comets, as opposed to asteroids."
        ,"Combined observations from NASA’s NIRCam (Near-Infrared Camera) and Hubble’s WFC3 (Wide Field Camera 3) show spiral galaxy NGC 5584, which resides 72 million light-years away from Earth."
    )

    fun getImageList() :  MutableList<Int>{
        return imageList
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDescriptionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .asGif()
            .load(R.drawable.space_bg) // Replace with your actual GIF file in res/drawable
            .into(binding.backgroundGif) // Reference to the background_gif ImageView
        val args: ImageDescriptionArgs by navArgs()
        val position = args.position

        binding.imageView.setImageResource(imageList[position-2])
        binding.descriptionText.text = textList[position-2]

        binding.imageView.setOnClickListener {
            val action = ImageDescriptionDirections.actionImageDescriptionToExtendedImageView(position)
            Navigation.findNavController(view).navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}