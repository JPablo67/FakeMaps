package co.edu.eam.fakemaps.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Usuarios
import co.edu.eam.fakemaps.databinding.ActivityRegistroBinding
import co.edu.eam.fakemaps.modelo.Usuario

class RegistroActivity : AppCompatActivity() {

    lateinit var binding:ActivityRegistroBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnReg.setOnClickListener{registrarUsuario()}



    }

    fun registrarUsuario(){
        val nombre = binding.nombreUsuario.text.toString()
        val username =binding.usernameUsuario.text.toString()
        val email = binding.emailUsuario.text.toString()
        val pass = binding.passUsuario.text.toString()
        val ciudad = binding.ciudadUsuario.text.toString()

        if (nombre.isEmpty()){
            binding.nombreLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.nombreLayout.error = null
        }

        if (username.isEmpty()){
            binding.usernameLayout.error = getString(R.string.es_obligatorio)
        }else{
            if(username.length >10){
                binding.usernameLayout.error = getString(R.string.es_obligatorio)
            }else{
                binding.usernameLayout.error = null
            }

        }

        if (email.isEmpty()){
            binding.emailLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.emailLayout.error = null
        }

        if (pass.isEmpty()){
            binding.passLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.passLayout.error = null
        }

        if (ciudad.isEmpty()){
            binding.ciudadLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.ciudadLayout.error = null
        }

        if (nombre.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && ciudad.isNotEmpty() ){
            val Usuario = Usuario(4,nombre, username, email, pass, ciudad)
            Usuarios.agregar(Usuario)
            Toast.makeText(this, "usuario registrado correctamente", Toast.LENGTH_SHORT).show()

            Log.e("Registro Activity", Usuarios.listar().toString())
        }

    }


}