package com.emrsa.stargazers.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.emrsa.stargazers.R
import com.emrsa.stargazers.musicservice.MusicService

class MainActivity : AppCompatActivity() {
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

            insets
        }
    }
    override fun onResume() {
        super.onResume()
        // Müzik servisini yeniden başlat
        val intent = Intent(this, MusicService::class.java)
        startService(intent)
    }
    override fun onStop() {
        super.onStop()

        // Uygulama arka plana alındığında müzik servisini durdur
        stopService(Intent(this, MusicService::class.java))
    }
    override fun onDestroy() {
        super.onDestroy()

        // Uygulama kapandığında müzik servisini durdur
        stopService(Intent(this, MusicService::class.java))
    }
}