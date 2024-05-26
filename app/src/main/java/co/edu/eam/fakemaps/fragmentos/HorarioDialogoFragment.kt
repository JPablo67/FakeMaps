package co.edu.eam.fakemaps.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.databinding.FragmentHorarioDialogoBinding
import co.edu.eam.fakemaps.databinding.ItemLugarBinding
import co.edu.eam.fakemaps.modelo.DiaSemana
import co.edu.eam.fakemaps.modelo.Horario


class HorarioDialogoFragment : DialogFragment() {

    lateinit var binding: FragmentHorarioDialogoBinding
    var diaSeleccionado = -1
    lateinit var listener: onHorarioCreadoListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHorarioDialogoBinding.inflate(inflater, container, false)

        binding.agregarHorario.setOnClickListener { agregarHorario() }
        cargarDias()
        return binding.root
    }

    fun agregarHorario() {
        val diaSemana = diaSeleccionado
        val horaInicio = binding.horaInicio.text.toString()
        val horaCierre = binding.horaCierre.text.toString()

        if (diaSemana!=-1 && horaInicio.isNotEmpty() && horaCierre.isNotEmpty()){
            val lista:ArrayList<DiaSemana> = ArrayList()
            lista.add(DiaSemana.values()[diaSemana])

            val horario = Horario(lista, horaInicio.toInt(), horaCierre.toInt())
            listener.elegirHorario(horario)
            dismiss()

        }
    }

    fun cargarDias(){
        var lista = DiaSemana.values()
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.diaSemana.adapter = adapter
        binding.diaSemana.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1:View?, p2:Int, p3:Long){
                diaSeleccionado = p2
                Toast.makeText(requireContext(), "el elemento seleccionado fue ${p0!!.getItemAtPosition(p2).toString()}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    interface onHorarioCreadoListener{
        fun elegirHorario(horario:Horario)
    }
}