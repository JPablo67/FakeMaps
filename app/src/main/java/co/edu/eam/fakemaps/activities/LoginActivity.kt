package co.edu.eam.fakemaps.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Usuarios
import co.edu.eam.fakemaps.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLog.setOnClickListener{login()}
        binding.btnReg.setOnClickListener{reg()}

    }

    fun reg(){
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }

    fun login(){
        val correo = binding.emailUsuario.text
        val contrasena = binding.passUsuario.text

        if (correo.isEmpty()){
            binding.emailLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.emailLayout.error = null
        }

        if (contrasena.isEmpty()){
            binding.passLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.passLayout.error = null
        }
        if (correo.isNotEmpty() && contrasena.isNotEmpty()){
            try{
                val usuario = Usuarios.login(correo.toString(), contrasena.toString())
                Toast.makeText(this,"Sus datos son correctos $correo, $contrasena", Toast.LENGTH_LONG).show()
            }catch (e:Exception){
                Toast.makeText(this,"Sus datos son incorrectos $correo, $contrasena", Toast.LENGTH_LONG).show()

            }

        }else{
            Toast.makeText(this,"Los campos son obligatorios ", Toast.LENGTH_LONG).show()
        }
    }
}