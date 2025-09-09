package com.example.calculadoraimc

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TableLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.TableRow
import android.widget.TextView


class history_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        val tableLayout = findViewById<TableLayout>(R.id.tableHistorial)
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val db = admin.readableDatabase

        val perf_user= getSharedPreferences(com.example.calculadoraimc.login.Global.preferencias_compartidas,Context.MODE_PRIVATE)
        val user_per = perf_user.getString("Correo","usuario")

        val user = user_per

        val username= findViewById<TextView>(R.id.tvUser)
        username.setText(user)

        val cursor = db.rawQuery("SELECT fecha, peso, estatura, imc FROM historial WHERE nombre = '$user'", null)

        if (cursor.moveToFirst()) {
            do {
                val fecha = cursor.getString(0)
                val peso = cursor.getDouble(1)
                val estatura = cursor.getDouble(2)
                val imc = cursor.getString(3)

                val row = TableRow(this)

                val tvFecha = TextView(this)
                tvFecha.text = fecha
                tvFecha.setPadding(8, 8, 8, 8)

                val tvPeso = TextView(this)
                tvPeso.text = peso.toString()
                tvPeso.setPadding(8, 8, 8, 8)

                val tvEstatura = TextView(this)
                tvEstatura.text = estatura.toString()
                tvEstatura.setPadding(8, 8, 8, 8)

                val tvImc = TextView(this)
                tvImc.text = imc
                tvImc.setPadding(8, 8, 8, 8)

                // Agregamos las columnas en el orden que quieras mostrar
                row.addView(tvFecha)
                row.addView(tvPeso)
                row.addView(tvEstatura)
                row.addView(tvImc)

                tableLayout.addView(row)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
    }
}