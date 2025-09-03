package com.example.calculadoraimc

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class activity_historial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        val tableLayout = findViewById<TableLayout>(R.id.tableHistorial)

        // Abrimos la base de datos (asegúrate de que el nombre coincida con tu DBHelper)
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val db = admin.readableDatabase

        val cursor = db.rawQuery("SELECT id, nombre, peso, altura, imc FROM historial", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getFloat(1)
                val peso = cursor.getFloat(2)
                val altura = cursor.getFloat(3)
                val imc = cursor.getFloat(4)

                val row = TableRow(this)

                val tvId = TextView(this)
                tvId.text = id.toString()
                tvId.setPadding(8, 8, 8, 8)

                val tvNombre = TextView(this)
                tvNombre.text = nombre.toString()
                tvNombre.setPadding(8, 8, 8, 8)

                val tvPeso = TextView(this)
                tvPeso.text = peso.toString()
                tvPeso.setPadding(8, 8, 8, 8)

                val tvAltura = TextView(this)
                tvAltura.text = altura.toString()
                tvAltura.setPadding(8, 8, 8, 8)

                val tvImc = TextView(this)
                tvImc.text = imc.toString()
                tvImc.setPadding(8, 8, 8, 8)

                row.addView(tvId)
                row.addView(tvPeso)
                row.addView(tvAltura)
                row.addView(tvImc)

                tableLayout.addView(row)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

    }
}