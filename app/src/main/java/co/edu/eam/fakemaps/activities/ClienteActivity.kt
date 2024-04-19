package co.edu.eam.fakemaps.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.bd.LocalStorage
import co.edu.eam.fakemaps.databinding.ActivityClienteBinding
import co.edu.eam.fakemaps.modelo.Lugar
import co.edu.eam.fakemaps.adapter.LugarAdapter

class ClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClienteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var lugarAdapter: LugarAdapter
    private var lugares: MutableList<Lugar> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerViewLugares
        recyclerView.layoutManager = LinearLayoutManager(this)
        lugarAdapter = LugarAdapter(lugares)
        recyclerView.adapter = lugarAdapter

        cargarLugaresUsuario()
    }

    private fun cargarLugaresUsuario() {
        val userId = LocalStorage.User
        if (userId == null) {
            Log.e("ClienteActivity", "UserID is null")
            return
        }

        try {
            lugares.addAll(Lugares.listarPorUsuario(userId.id))
            lugarAdapter.notifyDataSetChanged()

            if (lugares.isEmpty()) {
                binding.txtNoLugares.visibility = View.VISIBLE
            } else {
                binding.txtNoLugares.visibility = View.GONE
            }
        } catch (e: Exception) {
            Log.e("ClienteActivity", "Error loading places", e)
        }
    }
}
