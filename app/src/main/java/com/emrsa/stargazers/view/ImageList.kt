package com.emrsa.stargazers.view

import ImageListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.emrsa.stargazers.ListItem
import com.emrsa.stargazers.R
import com.emrsa.stargazers.databinding.FragmentImageListBinding

class ImageList: Fragment() {

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ImageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        val view = binding.root

        val items = mutableListOf(
            ListItem.Description("Bağımsız AçıklamaBağımsız AçıklamaBağımsız AçıklamaBağımsız AçıklamaBağımsız " +
                    "AçıklamaBağımsız AçıklamaBağımsız AçıklamaBağımsız Açıklama"),
            ListItem.ImageDescription(R.drawable.image1, "Açıklama 1"),
            ListItem.ImageDescription(R.drawable.image2, "Açıklama 2"),
            ListItem.ImageDescription(R.drawable.image3, "Açıklama 3"),
            ListItem.ImageDescription(R.drawable.image4, "Açıklama 4"),
            ListItem.ImageDescription(R.drawable.image5, "Açıklama 5"),
            ListItem.ImageDescription(R.drawable.image6, "Açıklama 6"),
            ListItem.ImageDescription(R.drawable.image7, "Açıklama 7"),
            ListItem.ImageDescription(R.drawable.image8, "Açıklama 8")
        )

        adapter = ImageListAdapter(items){ position ->
            val action = ImageListDirections.actionImageListToImageDescription(position)
            Navigation.findNavController(view).navigate(action)
        }

        val layoutManager = GridLayoutManager(context, 2)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    ImageListAdapter.VIEW_TYPE_DESCRIPTION -> 2 // Tam satır genişliği
                    ImageListAdapter.VIEW_TYPE_IMAGE_DESCRIPTION -> 1 // Normal öğe
                    else -> 1
                }
            }
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}