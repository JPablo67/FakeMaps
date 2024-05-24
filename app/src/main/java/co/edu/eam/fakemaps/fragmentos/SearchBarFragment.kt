package co.edu.eam.fakemaps.fragmentos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.activities.ResultadoBusquedaActivity
import co.edu.eam.fakemaps.databinding.FragmentSearchBarBinding


class SearchBarFragment : Fragment() {
    lateinit var binding: FragmentSearchBarBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBarBinding.inflate(inflater,container,false)

        binding.txtBusqueda.setOnEditorActionListener{textView,i,keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEARCH){
                val busqueda = binding.txtBusqueda.text.toString()
                if(busqueda.isNotEmpty()){
                    val intent = Intent(activity, ResultadoBusquedaActivity::class.java)
                    intent.putExtra("texto", busqueda)
                    startActivity(intent)
                    Log.e("MainActivity",binding.txtBusqueda.text.toString())
                }
            }
            true
        }
        return binding.root
    }
}