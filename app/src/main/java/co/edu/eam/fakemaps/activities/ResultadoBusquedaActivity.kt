package co.edu.eam.fakemaps.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.adapter.LugarAdapter
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.databinding.ActivityResultadoBusquedaBinding
import co.edu.eam.fakemaps.modelo.Lugar
import co.edu.eam.fakemaps.modelo.EstadoLugar

class ResultadoBusquedaActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultadoBusquedaBinding
    var textoBusqueda: String = ""
    lateinit var listaLugares: ArrayList<Lugar>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultadoBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textoBusqueda = intent.extras!!.getString("texto", "")
        listaLugares = ArrayList()

        if (textoBusqueda.isNotEmpty()) {
            listaLugares = Lugares.buscarByNombre(textoBusqueda).filter {
                it.estado != EstadoLugar.RECHAZADO && it.estado != EstadoLugar.SIN_REVISAR
            } as ArrayList<Lugar>
            Log.e("ResultadoBusquedaActivity", listaLugares.toString())
        }

        val adapter = LugarAdapter(listaLugares)
        binding.listarLugaresBusqueda.adapter = adapter
        binding.listarLugaresBusqueda.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}
