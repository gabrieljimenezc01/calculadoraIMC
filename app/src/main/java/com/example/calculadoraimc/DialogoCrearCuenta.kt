package com.example.calculadoraimc

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import kotlin.jvm.java

class DialogoCrearCuenta : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View=inflater.inflate(R.layout.fragment_dialogo_crear_cuenta, container, false)

        var correo=view.findViewById<EditText>(R.id.edittext_correo)
        var pass=view.findViewById<EditText>(R.id.edittext_pass)
        var boton_crear_cuenta=view.findViewById<Button>(R.id.boton_crear_cuenta)

        boton_crear_cuenta.setOnClickListener{
            if(pass.text.toString()!=""){
                if(correo.text.toString()!="" && Patterns.EMAIL_ADDRESS.matcher(correo.text.toString()).matches()){
                    crear_cuenta_firebase(correo.text.toString(),pass.text.toString())
                }
                else{
                    Toast.makeText(requireContext(),"Formato de correo incorrecto.",Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(requireContext(),"Escriba la contraseña",Toast.LENGTH_LONG).show()
            }
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return view
    }

    fun crear_cuenta_firebase(correo:String, pass:String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra("Correo",task.result.user?.email)
//                    intent.putExtra("Proveedor","Usuario/contraseña")
                    startActivity(intent)

                    Toast.makeText(requireContext(),"Cuenta creada.",Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(),"Contraseña corta/usuario existente.",Toast.LENGTH_LONG).show()
                }
            }
    }
}