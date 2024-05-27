package co.edu.eam.fakemaps.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.adapter.LugarAdapter
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.databinding.ActivityMisLugaresBinding
import co.edu.eam.fakemaps.modelo.Lugar

class MisLugaresActivity : AppCompatActivity() {

    lateinit var binding: ActivityMisLugaresBinding
    var idbusqueda: Int = 0
    lateinit var lugarAdapter: LugarAdapter
    lateinit var listaLugares: ArrayList<Lugar>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMisLugaresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        idbusqueda = sp.getInt("id_user", 0)
        listaLugares = ArrayList()

        // Initialize the adapter after listaLugares has been initialized
        lugarAdapter = LugarAdapter(listaLugares)
        binding.listarMisLugaresBusqueda.adapter = lugarAdapter
        binding.listarMisLugaresBusqueda.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (idbusqueda != 0) {
            Lugares.buscarPorIdCreador(idbusqueda) { lugares ->
                listaLugares.clear()
                listaLugares.addAll(lugares)
                lugarAdapter.notifyDataSetChanged()

                Log.e("MisLugaresActivity", listaLugares.toString())
            }
            Log.e("MisLugaresActivity", listaLugares.toString())
        }
    }
}
