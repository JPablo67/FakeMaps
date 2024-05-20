package co.edu.eam.fakemaps.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.databinding.FragmentCuentaBinding
import co.edu.eam.fakemaps.databinding.FragmentFavoritosBinding

class CuentaFragment : Fragment() {
    lateinit var binding: FragmentCuentaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCuentaBinding.inflate(inflater, container, false)
        return binding.root
    }
}