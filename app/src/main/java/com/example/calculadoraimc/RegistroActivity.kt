package com.example.calculadoraimc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_activity)

        val edNombre = findViewById<EditText>(R.id.edNombre)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombre = edNombre.text.toString().trim()

            if (nombre.isNotEmpty()) {
                // Guardamos en SharedPreferences
                val prefs = getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("nombre", nombre)
                editor.apply()

                // Ir al MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                edNombre.error = "Por favor escribe tu nombre"
            }
        }
    }
}
