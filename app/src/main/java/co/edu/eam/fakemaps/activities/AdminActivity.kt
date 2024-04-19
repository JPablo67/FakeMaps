package co.edu.eam.fakemaps.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.databinding.ActivityAdminBinding
import co.edu.eam.fakemaps.adapter.LugarAdapter
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.fakemaps.modelo.EstadoLugar
import co.edu.eam.fakemaps.modelo.Lugar

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var lugares: MutableList<Lugar>
    private lateinit var adapter: LugarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Establecer el padding para la barra de estado
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lugares = Lugares.listar().toMutableList()
        adapter = LugarAdapter(lugares)

        binding.recyclerViewLocales.adapter = adapter
        binding.recyclerViewLocales.layoutManager = LinearLayoutManager(this)

        binding.btnAprobar.setOnClickListener {
            actualizarEstadoSeleccionado(EstadoLugar.ACEPTADO)
        }

        binding.btnDenegar.setOnClickListener {
            actualizarEstadoSeleccionado(EstadoLugar.RECHAZADO)
        }
    }

    private fun actualizarEstadoSeleccionado(estado: EstadoLugar) {
        val lugarSeleccionado = adapter.obtenerLugarSeleccionado()

        if (lugarSeleccionado != null) {
            lugarSeleccionado.estado = estado
            Lugares.editar(lugarSeleccionado)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Estado actualizado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Por favor, selecciona un lugar", Toast.LENGTH_SHORT).show()
        }
    }
}
