package co.edu.eam.fakemaps.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Usuarios
import co.edu.eam.fakemaps.databinding.ActivityRegistroBinding
import co.edu.eam.fakemaps.modelo.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnReg.setOnClickListener { registrarUsuario() }
    }

    fun registrarUsuario() {
        val id = binding.id.text.toString()

        // Convertir el ID de String a Int
        val idInt = try {
            id.toInt()
        } catch (e: NumberFormatException) {
            // Manejo de error en caso de que el valor no sea un número válido
            binding.usernameLayout.error = "El ID debe ser un número válido"
            return
        }

        val nombre = binding.nombreUsuario.text.toString()
        val email = binding.emailUsuario.text.toString().lowercase()
        val pass = binding.passUsuario.text.toString()
        val ciudad = binding.ciudadUsuario.text.toString()

        // Validación de campos vacíos
        if (nombre.isEmpty()) {
            binding.nombreLayout.error = getString(R.string.es_obligatorio)
            return
        }

        if (id.isEmpty()) {
            binding.usernameLayout.error = getString(R.string.es_obligatorio)
            return
        }

        if (email.isEmpty()) {
            binding.emailLayout.error = getString(R.string.es_obligatorio)
            return
        }

        if (pass.isEmpty()) {
            binding.passLayout.error = getString(R.string.es_obligatorio)
            return
        }

        if (ciudad.isEmpty()) {
            binding.ciudadLayout.error = getString(R.string.es_obligatorio)
            return
        }

        // Crear una instancia de Usuario usando el ID convertido a Int
        val usuario = Usuario(idInt, nombre, email, pass, ciudad, false)
        Firebase.firestore.collection("usuarios").add(usuario).addOnSuccessListener {
            Snackbar.make(binding.root, getString(R.string.abre_el), Snackbar.LENGTH_LONG).show()

            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 4000)
        }.addOnFailureListener{
            Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
        }

        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
        Log.e("Registro Activity", Usuarios.listar().toString())
    }
}
