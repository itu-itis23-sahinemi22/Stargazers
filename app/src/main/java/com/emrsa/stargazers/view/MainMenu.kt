package com.emrsa.stargazers.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .asGif()
            .load(R.drawable.bg_stars) // Replace with your actual GIF file in res/drawable
            .into(binding.backgroundGif) // Reference to the background_gif ImageView

        val textFadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val textFadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
        val zoom_animation  = AnimationUtils.loadAnimation(requireContext(), R.anim.grow_animation)

        textFadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Optionally handle when the animation starts
            }

            override fun onAnimationEnd(animation: Animation?) {
                // When fade-in ends, start fade-out
                binding.nextDescriptionTextView1.startAnimation(textFadeOut)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Optionally handle when the animation repeats
            }
        })

        textFadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Optionally handle when the animation starts
            }

            override fun onAnimationEnd(animation: Animation?) {
                // When fade-out ends, start fade-in again
                binding.nextDescriptionTextView1.startAnimation(textFadeIn)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Optionally handle when the animation repeats
            }
        })

        zoom_animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Optionally handle when the animation starts
            }

            override fun onAnimationEnd(animation: Animation?) {
                // When the text animation ends, start the button animation
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Optionally handle when the animation repeats
            }
        })


        binding.nextDescriptionTextView1.startAnimation(textFadeIn)
        binding.logoImage.startAnimation(zoom_animation)
        binding.root.setOnClickListener {
            Navigation.findNavController(this@MainMenu.requireView()).navigate(R.id.action_mainMenu_to_storytell)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
        }
}