package com.example.calculadoraimc

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

        btnIMC.setOnClickListener {
            val pesotexto = edPeso.text.toString()
            val estatura1 = edEstatura.text.toString()

            if (pesotexto.isNotEmpty()&& estatura1.isNotEmpty()){
                val peso = pesotexto.toFloat()
                val estatura = estatura1.toFloat()
                tvimc.text = " "

                if (estatura>0){
                    val imc = peso /(estatura*estatura)
                    if (imc <= 18.5){
                        tvimc.setText("Bajo peso")
                    }
                    if (imc >18.5 && imc<=24.9){
                        tvimc.setText("Normal")
                    }
                    if (imc >24.9 && imc<=29.9){
                        tvimc.setText("Sobrepeso")
                    }
                    if (imc>29.9){
                        tvimc.setText("Obesidad")
                    }
                }else{
                    tvimc.setText("Digite la estatura")
                }
            }else{
                tvimc.setText("Hay un Campo vacio:\ncampos vacios.\nPeso= $pesotexto, y altura = $estatura1")
            }



        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}