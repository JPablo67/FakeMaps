package co.edu.eam.fakemaps.fragmentos

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.adapter.LugarAdapter
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.databinding.FragmentFavoritosBinding
import co.edu.eam.fakemaps.databinding.FragmentInicioBinding
import co.edu.eam.fakemaps.modelo.EstadoLugar
import co.edu.eam.fakemaps.modelo.Lugar

class FavoritosFragment : Fragment() {

    lateinit var binding: FragmentFavoritosBinding
    var idbusqueda: Int = 0
    var listaLugares:ArrayList<Lugar> = ArrayList()
    lateinit var adapter: LugarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoritosBinding.inflate(inflater, container, false)

        adapter = LugarAdapter(listaLugares)
        binding.listaFavoritos.adapter = adapter
        binding.listaFavoritos.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)

        idbusqueda = sp.getInt("id_user",0)
        listaLugares = ArrayList()

        if (idbusqueda != 0) {
            listaLugares = Lugares.buscarPorUsuario(idbusqueda).filter {
                it.estado != EstadoLugar.RECHAZADO && it.estado != EstadoLugar.SIN_REVISAR
            } as ArrayList<Lugar>
            Log.e("ResultadoBusquedaActivity", listaLugares.toString())
        }

        val adapter = LugarAdapter(listaLugares)
        binding.listaFavoritos.adapter = adapter

        return binding.root
    }

}