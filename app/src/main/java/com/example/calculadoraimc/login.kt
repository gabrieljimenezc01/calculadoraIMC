package com.example.calculadoraimc

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class login : AppCompatActivity() {
    object Global{
        var preferencias_compartidas="sharedpreferences"
    }
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        verificar_sesion_abierta()

        var correo = findViewById<TextView>(R.id.etEmail)
        var pass = findViewById<TextView>(R.id.etPassword)
        val btnlogin = findViewById<Button>(R.id.btnLogin)
        val registro = findViewById<TextView>(R.id.tvRegister)


        btnlogin.setOnClickListener {
            if(pass.text.toString()!=""){
                if(correo.text.toString()!="" && Patterns.EMAIL_ADDRESS.matcher(correo.text.toString()).matches()){
                    login_firebase(correo.text.toString(),pass.text.toString())
                }
                else{
                    Toast.makeText(applicationContext,getString(R.string.formatocorreo),Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(applicationContext,getString(R.string.escribacontraseña),Toast.LENGTH_LONG).show()
            }

        }

        registro.setOnClickListener {
            DialogoCrearCuenta().show(supportFragmentManager, null)

        }

    }
    fun verificar_sesion_abierta(){
        var sesion_abierta:SharedPreferences=this.getSharedPreferences(com.example.calculadoraimc.login.Global.preferencias_compartidas, MODE_PRIVATE)

        var correo=sesion_abierta.getString("Correo",null)
        var proveedor=sesion_abierta.getString("Proveedor",null)

        if(correo!=null && proveedor!=null){
            var intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("Correo",correo)
            startActivity(intent)
        }
    }

    fun login_firebase(correo:String, pass:String){
        val sesionAbierta = findViewById<com.google.android.material.checkbox.MaterialCheckBox>(R.id.cbRemember)
        auth.signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var guardar_user:SharedPreferences.Editor=this.getSharedPreferences(com.example.calculadoraimc.login.Global.preferencias_compartidas, MODE_PRIVATE).edit()
                    guardar_user.putString("Correo",correo)
                    guardar_user.apply()
                    guardar_user.commit()
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)

                    if (sesionAbierta.isChecked == true){
                        guardar_sesion(task.result.user?.email.toString(),"Usuario/contraseña")
                    }
                } else {
                    Toast.makeText(applicationContext,getString(R.string.usuarioincorrecto),Toast.LENGTH_LONG).show()
                }
            }
    }
    fun guardar_sesion(correo:String, proveedor:String){
        var guardar_sesion:SharedPreferences.Editor=this.getSharedPreferences(com.example.calculadoraimc.login.Global.preferencias_compartidas, MODE_PRIVATE).edit()
        guardar_sesion.putString("Correo",correo)
        guardar_sesion.putString("Proveedor",proveedor)
        guardar_sesion.apply()
        guardar_sesion.commit()
    }
}