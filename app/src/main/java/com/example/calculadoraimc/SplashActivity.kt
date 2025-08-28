package com.example.calculadoraimc

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val videoView: VideoView = findViewById(R.id.videoView)
        val videoPath = "android.resource://$packageName/${R.raw.intro}"
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        // Inicia el video
        videoView.start()

        // Cuando termine el video, abre la actividad principal
        videoView.setOnCompletionListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}