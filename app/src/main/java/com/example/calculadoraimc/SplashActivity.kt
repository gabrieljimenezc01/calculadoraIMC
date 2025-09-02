package com.example.calculadoraimc

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import android.content.Context

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
            val prefs = getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
            val nombre = prefs.getString("nombre", null)

            if (nombre.isNullOrEmpty()) {
                startActivity(Intent(this, RegistroActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }

    }
}