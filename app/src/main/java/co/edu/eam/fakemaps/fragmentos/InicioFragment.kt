package co.edu.eam.fakemaps.fragmentos

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.databinding.FragmentInicioBinding
import co.edu.eam.fakemaps.databinding.FragmentSearchBarBinding

class InicioFragment : Fragment() {

    lateinit var binding: FragmentInicioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }
}