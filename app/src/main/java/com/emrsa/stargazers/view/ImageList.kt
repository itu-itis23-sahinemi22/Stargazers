package com.emrsa.stargazers.view

import ImageListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
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
            ListItem.BoldDescription("WEBB SPACE TELESCOPE "),
            ListItem.Description("Click Images for more information."),
            ListItem.ImageDescription(R.drawable.image1, "NGC 5468"),
            ListItem.ImageDescription(R.drawable.image2, "Rho Ophiuchi"),
            ListItem.ImageDescription(R.drawable.image3, "Ring Nebula"),
            ListItem.ImageDescription(R.drawable.image4, "Crab Nebula"),
            ListItem.ImageDescription(R.drawable.image5, "IC 348"),
            ListItem.ImageDescription(R.drawable.image6, "HH 221"),
            ListItem.ImageDescription(R.drawable.image7, "Comet 238P/Read"),
            ListItem.ImageDescription(R.drawable.image8, "NGC 5584")
        )

        adapter = ImageListAdapter(items){ position ->
            val action = ImageListDirections.actionImageListToImageDescription(position)
            Navigation.findNavController(view).navigate(action)
            println(position)
        }

        val layoutManager = GridLayoutManager(context, 2)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    ImageListAdapter.VIEW_TYPE_DESCRIPTION -> 2 // Tam satır genişliği
                    ImageListAdapter.VIEW_TYPE_IMAGE_DESCRIPTION -> 1 // Normal öğe
                    ImageListAdapter.VIEW_TYPE_BOLD_DESCRIPTION -> 2
                    else -> 1
                }
            }
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .asGif()
            .load(R.drawable.space_bg) // Replace with your actual GIF file in res/drawable
            .into(binding.backgroundGif) // Reference to the background_gif ImageView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}