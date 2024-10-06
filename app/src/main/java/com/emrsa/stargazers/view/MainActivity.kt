package com.emrsa.stargazers.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.emrsa.stargazers.R
import com.emrsa.stargazers.musicservice.MusicService

class MainActivity : AppCompatActivity() {
    private var isStop = false

    private fun onFragmentChanged(fragmentId: Int) {
        when (fragmentId) {
            R.id.imageList -> {
                stopService(Intent(this, MusicService::class.java))
            }
            R.id.imageDescription -> {
                stopService(Intent(this, MusicService::class.java))
            }
            R.id.extendedImageView -> {
                stopService(Intent(this, MusicService::class.java))
            }
            else -> {
                startService(Intent(this, MusicService::class.java))
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            val intent = Intent(this, MusicService::class.java)
            startService(intent)

            this@MainActivity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

            val navController = findNavController(R.id.fragmentContainerView)
            navController.addOnDestinationChangedListener{ controller, destination, arguments ->
                if(!isStop){
                    onFragmentChanged(destination.id)
                }
                else{
                    stopService(Intent(this, MusicService::class.java))
                }
            }

            insets
        }
    }
    override fun onResume() {
        super.onResume()
        isStop = false
        val navController = findNavController(R.id.fragmentContainerView)
        val currentFragmentId = navController.currentDestination?.id
        if(currentFragmentId == R.id.extendedImageView){
            stopService(Intent(this, MusicService::class.java))
        }
        else{
            startService(Intent(this, MusicService::class.java))
        }

    }
    override fun onStop() {
        super.onStop()
        isStop = true
    }
    override fun onDestroy() {
        super.onDestroy()
        isStop = true
        }
}