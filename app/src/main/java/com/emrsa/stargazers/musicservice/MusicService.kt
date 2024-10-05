package com.emrsa.stargazers.musicservice

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.IBinder
import com.emrsa.stargazers.R

class MusicService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private var currentPosition = 0
    private var isMusicPlaying = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // SharedPreferences'den son kaydedilen pozisyonu al
        val sharedPreferences: SharedPreferences = getSharedPreferences("MusicPreferences", MODE_PRIVATE)
        currentPosition = sharedPreferences.getInt("lastPosition", 0)

        // Eğer müzik çalmıyorsa, yeni bir MediaPlayer oluştur ve çalmaya başla
        if (!isMusicPlaying) {
            mediaPlayer = MediaPlayer.create(this, R.raw.epic_music) // raw klasöründeki dosya adı
            mediaPlayer.isLooping = true
            mediaPlayer.seekTo(currentPosition)
            mediaPlayer.start()
            isMusicPlaying = true
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Mevcut pozisyonu kaydet
        if (mediaPlayer.isPlaying) {
            currentPosition = mediaPlayer.currentPosition
            val sharedPreferences: SharedPreferences = getSharedPreferences("MusicPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("lastPosition", currentPosition)
            editor.apply()

            mediaPlayer.stop()
            isMusicPlaying = false
        }
        mediaPlayer.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}