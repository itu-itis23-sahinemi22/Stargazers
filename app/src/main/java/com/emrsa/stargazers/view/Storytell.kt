package com.emrsa.stargazers.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.emrsa.stargazers.R
import com.emrsa.stargazers.databinding.FragmentStorytellBinding

class Storytell : Fragment() {

    private var _binding: FragmentStorytellBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentStorytellBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.storyText.setOnClickListener{
            Navigation.findNavController(this@Storytell.requireView()).navigate(R.id.action_storytell_to_imageList)
        }
        // Load the background GIF
        Glide.with(this)
            .asGif()
            .load(R.drawable.bg_stars) // Replace with your actual GIF file
            .into(binding.backgroundGif) // Reference to the background_gif ImageView

        // Find the TextView by its ID
        val storyText: TextView = view.findViewById(R.id.storyText)

        // Load the slide-up animation from the anim resource
        val slideUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)

        // Apply an AnimationListener to detect when the story text animation ends
        slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Optionally handle when the animation starts
            }

            override fun onAnimationEnd(animation: Animation?) {
                // When the text animation ends, start the button animation
                val buttonAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.button_appear)
                binding.myButton.startAnimation(buttonAnimation) // Start button animation
                binding.myButton.visibility = View.VISIBLE // Make the button visible (if it was hidden initially)


            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Optionally handle when the animation repeats
            }
        })

        // Start the animation on the TextView
        storyText.startAnimation(slideUpAnimation)

        // Optionally hide the button initially
        binding.myButton.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}