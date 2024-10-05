package com.emrsa.stargazers.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.emrsa.stargazers.R
import com.emrsa.stargazers.databinding.FragmentMainMenuBinding
import com.bumptech.glide.Glide



class MainMenu : Fragment() {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .asGif()
            .load(R.drawable.space_bg) // Replace with your actual GIF file in res/drawable
            .into(binding.backgroundGif) // Reference to the background_gif ImageView

        binding.root.setOnClickListener {
            Navigation.findNavController(this@MainMenu.requireView()).navigate(R.id.action_mainMenu_to_storytell)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}