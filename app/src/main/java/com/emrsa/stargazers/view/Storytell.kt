package com.emrsa.stargazers.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.emrsa.stargazers.R
import com.emrsa.stargazers.databinding.FragmentStorytellBinding
import kotlinx.coroutines.*

class Storytell : Fragment() {

    private var _binding: FragmentStorytellBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStorytellBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the TextView by its ID
        val storyText: TextView = binding.storyText

        // Load the animation from the anim resource
        val slideUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)

        // Start the animation on the TextView
        storyText.startAnimation(slideUpAnimation)

        lifecycleScope.launch {
            delay(1000L) // 1 saniye gecikme
            val button = binding.storytellNextButton
            button.visibility = View.VISIBLE
            button.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_storytell_to_imageList)
            }
        }

    }
}