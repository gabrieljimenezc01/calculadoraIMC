package com.example.calculadoraimc

import android.content.Context   // 👈 este es el que faltaba
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val edPeso = findViewById<EditText>(R.id.edPeso)
        val edEstatura = findViewById<EditText>(R.id.edEstatura)
        val btnIMC = findViewById<Button>(R.id.btnIMC)
        val tvimc = findViewById<TextView>(R.id.tvIMC)

        val prefs = getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
        val nombre = prefs.getString("nombre", "Usuario")

        // Mostrar saludo
        val saludo = findViewById<TextView>(R.id.tvIMC)
        saludo.text = "Hola, $nombre 👋"

        btnIMC.setOnClickListener {
            val pesotexto = edPeso.text.toString()
            val estatura1 = edEstatura.text.toString()

            if (pesotexto.isNotEmpty() && estatura1.isNotEmpty()) {
                val peso = pesotexto.toFloat()
                val estatura = estatura1.toFloat()
                tvimc.text = ""

                if (estatura > 0) {
                    val imc = peso / (estatura * estatura)
                    tvimc.text = when {
                        imc <= 18.5 -> "IMC: %.2f (Bajo peso)".format(imc)
                        imc <= 24.9 -> "IMC: %.2f (Normal)".format(imc)
                        imc <= 29.9 -> "IMC: %.2f (Sobrepeso)".format(imc)
                        else -> "IMC: %.2f (Obesidad)".format(imc)
                    }
                } else {
                    tvimc.text = "Digite la estatura"
                }
            } else {
                tvimc.text = "Hay un campo vacío:\nPeso= $pesotexto, Estatura= $estatura1"
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
