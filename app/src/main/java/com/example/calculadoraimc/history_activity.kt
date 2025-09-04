package com.example.calculadoraimc

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

        val cursor = db.rawQuery("SELECT codigo, nombre, peso, estatura, imc FROM historial", null)

        if (cursor.moveToFirst()) {
            do {
                val codigo = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val peso = cursor.getDouble(2)
                val estatura = cursor.getDouble(3)
                val imc = cursor.getString(4)

                val row = TableRow(this)

                val tvCodigo = TextView(this)
                tvCodigo.text = codigo.toString()
                tvCodigo.setPadding(8, 8, 8, 8)

                val tvNombre = TextView(this)
                tvNombre.text = nombre
                tvNombre.setPadding(8, 8, 8, 8)

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
                row.addView(tvCodigo)
                row.addView(tvNombre)
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