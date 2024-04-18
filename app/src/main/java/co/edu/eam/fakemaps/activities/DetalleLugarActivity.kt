package co.edu.eam.fakemaps.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.databinding.ActivityDetalleLugarBinding

class DetalleLugarActivity : AppCompatActivity() {

    lateinit var binding:ActivityDetalleLugarBinding
    var idLugar:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        idLugar = intent.extras!!.getInt("codigo")
        val lugar = Lugares.buscarById(idLugar)

        Toast.makeText(this,"${lugar.toString()}", Toast.LENGTH_SHORT).show()

        llenarCampos()

    }

    fun llenarCampos(){
        val lugar = Lugares.buscarById(idLugar)
        binding.nombreLugar.text = lugar!!.nombre
        binding.descripcionLugar.text = lugar.descripcion
        binding.direccionLugar.text = lugar.direccion
        binding.creadorLugar.text = lugar.id.toString()
        binding.categoriaLugar.text = lugar.idCategoria.toString()

    }
}