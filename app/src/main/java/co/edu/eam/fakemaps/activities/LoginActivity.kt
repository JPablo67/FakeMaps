package co.edu.eam.fakemaps.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Usuarios
import co.edu.eam.fakemaps.databinding.ActivityLoginBinding
import co.edu.eam.fakemaps.bd.LocalStorage
import co.edu.eam.fakemaps.modelo.Usuario

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLog.setOnClickListener { login() }
        binding.btnReg.setOnClickListener { reg() }
    }

    fun reg() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }

    fun login() {
        val correo = binding.emailUsuario.text.toString()
        val contrasena = binding.passUsuario.text.toString()

        if (correo.isEmpty()) {
            binding.emailLayout.error = getString(R.string.es_obligatorio)
            return
        } else {
            binding.emailLayout.error = null
        }

        if (contrasena.isEmpty()) {
            binding.passLayout.error = getString(R.string.es_obligatorio)
            return
        } else {
            binding.passLayout.error = null
        }

        val usuario = Usuarios.login(correo, contrasena)

        if (usuario != null) {
            if (usuario.is_admin == true) {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(
                    this,
                    "Bienvenido ${usuario.correo}, es administrador",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val intent = Intent(this, SessionOnlyActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(
                    this,
                    "Bienvenido ${usuario.correo}, no es administrador",
                    Toast.LENGTH_LONG
                ).show()

                // Guardar el ID del usuario en LocalStorage
                LocalStorage.User = usuario
            }
        } else {
            Toast.makeText(
                this,
                "Credenciales incorrectas",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
