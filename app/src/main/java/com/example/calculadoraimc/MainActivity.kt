package com.example.calculadoraimc

import android.content.Context
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
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val edPeso = findViewById<EditText>(R.id.edPeso)
        val edEstatura = findViewById<EditText>(R.id.edEstatura)
        val btnIMC = findViewById<Button>(R.id.btnIMC)
        val btnHistorial = findViewById<Button>(R.id.btnhistorial)
        val btnChange = findViewById<Button>(R.id.btnRol)
        val tvimc = findViewById<TextView>(R.id.tvIMC)

        val prefs = getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
        val nombre = prefs.getString("nombre", "Usuario")

        // Mostrar saludo
        val saludo = findViewById<TextView>(R.id.tvIMC)
        saludo.text = "Hola, $nombre 👋"

        btnIMC.setOnClickListener {
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            val pesotexto = edPeso.text.toString()
            val estatura1 = edEstatura.text.toString()

            if (pesotexto.isNotEmpty() && estatura1.isNotEmpty()) {
                val peso = pesotexto.toDouble()
                val estatura = estatura1.toDouble()
                tvimc.text = ""

                if (estatura > 0) {
                    val imc = peso / (estatura * estatura)
                    tvimc.text = when {
                        imc <= 18.5 -> "IMC: %.2f (Bajo peso)".format(imc)
                        imc <= 24.9 -> "IMC: %.2f (Normal)".format(imc)
                        imc <= 29.9 -> "IMC: %.2f (Sobrepeso)".format(imc)
                        else -> "IMC: %.2f (Obesidad)".format(imc)
                    }
                    val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
                    val bd = admin.writableDatabase
                    val registro = ContentValues()
                    registro.put("nombre", nombre)
                    registro.put("peso", peso)
                    registro.put("estatura", estatura)
                    registro.put("imc", String.format("%.2f", imc))
                    bd.insert("historial", null, registro)
                    edPeso.setText("")
                    edEstatura.setText("")
                    bd.close()
                    Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show()

                } else {
                    tvimc.text = "Digite la estatura"
                }
            } else {
                tvimc.text = "Hay un campo vacío:\nPeso= $pesotexto, Estatura= $estatura1"
            }
        }

        btnHistorial.setOnClickListener {
            startActivity(Intent(this, history_activity::class.java))
        }
        btnChange.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
