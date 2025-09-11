package com.example.calculadoraimc

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class history_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        val listHistorial = findViewById<LinearLayout>(R.id.listHistorial)
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val db = admin.readableDatabase
        val prefs = getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
        val perf_user= getSharedPreferences(com.example.calculadoraimc.login.Global.preferencias_compartidas,Context.MODE_PRIVATE)
        val user_per = perf_user.getString("Correo","usuario")

        val username = findViewById<TextView>(R.id.tvUser)
        username.text = user_per
        val user = user_per

        val cursor = db.rawQuery(
            "SELECT fecha, peso, estatura, imc FROM historial WHERE nombre = '$user'",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val fecha = cursor.getString(0)
                val peso = cursor.getDouble(1)
                val estatura = cursor.getDouble(2)
                val imc = cursor.getString(3)

                // Crear tarjeta dinámica
                val card = LinearLayout(this)
                card.orientation = LinearLayout.VERTICAL
                card.setPadding(24, 24, 24, 24)
                card.setBackgroundResource(android.R.color.white)
                card.elevation = 6f

                // Margen inferior entre tarjetas
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 0, 0, 24)
                card.layoutParams = params

                // Crear y añadir los textos
                val tvFecha = TextView(this).apply {
                    text = "📅 "+getString(R.string.fecha)+": $fecha"
                    textSize = 16f
                    setPadding(0, 4, 0, 4)
                    setTypeface(typeface, android.graphics.Typeface.BOLD)
                }

                val tvPeso = TextView(this).apply {
                    text = "⚖️ "+getString(R.string.peso)+": $peso kg"
                    textSize = 15f
                    setPadding(0, 4, 0, 4)
                }

                val tvEstatura = TextView(this).apply {
                    text = "📏 "+getString(R.string.estatura)+": $estatura m"
                    textSize = 15f
                    setPadding(0, 4, 0, 4)
                }

                val tvImc = TextView(this).apply {
                    text = "💪 $imc"
                    textSize = 15f
                    setPadding(0, 4, 0, 4)
                    setTextColor(getColor(android.R.color.holo_green_dark))
                    gravity = Gravity.END
                }

                // Agregar vistas a la tarjeta
                card.addView(tvFecha)
                card.addView(tvPeso)
                card.addView(tvEstatura)
                card.addView(tvImc)

                // Agregar tarjeta al contenedor principal
                listHistorial.addView(card)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
    }
}
