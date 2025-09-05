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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
        val mensaje = getString(R.string.Saludo)
        saludo.text = "$mensaje $nombre \uD83D\uDC4B"

        btnIMC.setOnClickListener {
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            val pesotexto = edPeso.text.toString()
            val estatura1 = edEstatura.text.toString()
            val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            if (pesotexto.isNotEmpty() && estatura1.isNotEmpty()) {
                val peso = pesotexto.toDouble()
                val estatura = estatura1.toDouble()
                tvimc.text = ""

                val imc = peso / (estatura * estatura)
                val imcformat = String.format("%.2f", imc)
                tvimc.text = when {
                    imc <= 18.5 -> "IMC: $imcformat ("+getString(R.string.bajopeso)+")"
                    imc <= 24.9 -> "IMC: $imcformat (Normal)"
                    imc <= 29.9 -> "IMC: $imcformat ("+getString(R.string.sobrepeso)+")"
                    else -> "IMC: $imcformat ("+getString(R.string.obesidad)+")"
                }
                val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
                val bd = admin.writableDatabase
                val registro = ContentValues()
                registro.put("nombre", nombre)
                registro.put("fecha", fecha.toString())
                registro.put("peso", peso)
                registro.put("estatura", estatura)
                registro.put("imc", imcformat)
                bd.insert("historial", null, registro)
                edPeso.setText("")
                edEstatura.setText("")
                bd.close()
                Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show()
            } else {
                tvimc.text = getString(R.string.faltandatos)
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
