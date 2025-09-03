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
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.Instant

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val edPeso = findViewById<EditText>(R.id.edPeso)
        val edEstatura = findViewById<EditText>(R.id.edEstatura)
        val btnIMC = findViewById<Button>(R.id.btnIMC)
        val tvimc = findViewById<TextView>(R.id.tvIMC)
        val btnGaurdar = findViewById<Button>(R.id.btnGuardar)
        val btnVerHistorial = findViewById<Button>(R.id.btnVerHistorial)

        val prefs = getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
        val nombre = prefs.getString("nombre", "Usuario")

        val pesotexto = edPeso.text.toString()
        val estatura = edEstatura.text.toString()
        // Mostrar saludo
        val saludo = findViewById<TextView>(R.id.tvIMC)
        saludo.text = "Hola, $nombre 👋"

        btnIMC.setOnClickListener {
            if (pesotexto.isNotEmpty() && estatura.isNotEmpty()) {
                val peso = pesotexto.toFloat()
                val estatura = estatura.toFloat()
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
                tvimc.text = "Hay un campo vacío:\nPeso= $pesotexto, Estatura= $estatura"
            }
        }

        btnGaurdar.setOnClickListener {
            val fecha = Instant.now()
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()

            registro.put("peso", pesotexto.toDouble())
            registro.put("estatura", estatura.toDouble())
            registro.put("imc", tvimc.text.toString())  // guarda el valor calculado, no el texto
            registro.put("nombre", nombre)
            registro.put("fecha", fecha.toString())

            bd.insert("Historial", null, registro)
            bd.close()

            edPeso.setText("")
            edEstatura.setText("")
            tvimc.text = "Hola, $nombre 👋"

            Toast.makeText(this, "Se guardaron los datos en el historial", Toast.LENGTH_SHORT).show()
        }

        btnVerHistorial.setOnClickListener {
            val intent = Intent(this, activity_historial::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
