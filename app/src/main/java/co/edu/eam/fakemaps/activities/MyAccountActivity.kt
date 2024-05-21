package co.edu.eam.fakemaps.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Usuarios
import co.edu.eam.fakemaps.databinding.ActivityDetalleLugarBinding
import co.edu.eam.fakemaps.databinding.ActivityMainBinding
import co.edu.eam.fakemaps.databinding.ActivityMyAccountBinding
import co.edu.eam.fakemaps.modelo.Usuario

class MyAccountActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)

        val id = sp.getInt("id_user",0)

        val user = Usuarios.obtenerUsuarioPorId(id)

        binding = ActivityMyAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_my_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (user != null) {
            binding.userName.text = user.nombre
            binding.userLocation.text = user.ciudadDeResidencia
            binding.userEmail.text = user.correo
        }


    }
}