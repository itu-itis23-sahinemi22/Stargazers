package com.emrsa.stargazers.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
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
    private val textList = mutableListOf("a",
        "b","c","d","e","f","g","h")
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

        val args: ImageDescriptionArgs by navArgs()
        val position = args.position

        binding.imageView.setImageResource(imageList[position-1])
        binding.descriptionText.text = textList[position-1]
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}