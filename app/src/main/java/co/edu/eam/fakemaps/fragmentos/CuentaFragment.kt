package co.edu.eam.fakemaps.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.activities.CrearLugarActivity
import co.edu.eam.fakemaps.activities.MainActivity
import co.edu.eam.fakemaps.bd.Usuarios
import co.edu.eam.fakemaps.databinding.ActivityMyAccountBinding
import co.edu.eam.fakemaps.databinding.FragmentCuentaBinding
import co.edu.eam.fakemaps.databinding.FragmentFavoritosBinding

class CuentaFragment : Fragment() {
    lateinit var binding: FragmentCuentaBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreate(savedInstanceState)

        val sp = activity?.getSharedPreferences("sesion", Context.MODE_PRIVATE)

        val id = sp?.getInt("id_user",0)

        val user = id?.let { Usuarios.obtenerUsuarioPorId(it) }

        binding = FragmentCuentaBinding.inflate(inflater,container,false)


        if (user != null) {
            binding.userName.text = user.nombre
            binding.userLocation.text = user.ciudadDeResidencia
            binding.userEmail.text = user.correo


        }
        binding.btnCrearLugar.setOnClickListener { irACrear() }
        binding.btnCerrarSesion.setOnClickListener{cerrarSesion()}
        return binding.root
    }

    private fun cerrarSesion() {
        val sharedPreferences = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE).edit()
        sharedPreferences.clear()
        sharedPreferences.apply()
        Toast.makeText(requireContext(), "Sesion cerrada", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
    }

    fun irACrear() {
        val intent = Intent(requireActivity(), CrearLugarActivity::class.java)
        startActivity(intent)
    }
}