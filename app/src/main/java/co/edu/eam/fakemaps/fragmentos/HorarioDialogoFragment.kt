package co.edu.eam.fakemaps.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Horarios
import co.edu.eam.fakemaps.databinding.FragmentHorarioDialogoBinding
import co.edu.eam.fakemaps.databinding.ItemLugarBinding
import co.edu.eam.fakemaps.modelo.DiaSemana
import co.edu.eam.fakemaps.modelo.Horario
import com.google.android.material.chip.Chip


class HorarioDialogoFragment : DialogFragment() {

    lateinit var binding:FragmentHorarioDialogoBinding
    lateinit var listener: onHorarioCreadoListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHorarioDialogoBinding.inflate(inflater, container, false)

        val horaInicioSpinner: Spinner = binding.horaInicio
        val horaCierreSpinner: Spinner = binding.horaCierre

        val hours = (0..23).map { it.toString().padStart(2, '0') + ":00" }
        val adapterInicio = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, hours)
        adapterInicio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        horaInicioSpinner.adapter = adapterInicio

        populateHoraCierreSpinner(hours, horaCierreSpinner)

        horaInicioSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedHour = position
                val startHour = hours[selectedHour].substringBefore(":").toInt()
                val hoursFromStart = (startHour..23).map { it.toString().padStart(2, '0') + ":00" }
                populateHoraCierreSpinner(hoursFromStart, horaCierreSpinner)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        cargarDias()
        binding.agregarHorario.setOnClickListener { agregarHorario() }
        return binding.root
    }

    fun agregarHorario() {
        val diasSemana = binding.listaDias.checkedChipIds


        if (diasSemana.isNotEmpty() && binding.horaInicio.selectedItem != null && binding.horaCierre.selectedItem != null) {

            val lista: ArrayList<DiaSemana> = diasSemana.map { index -> DiaSemana.values()[index] }.toCollection(ArrayList())

            val horaInicioString = binding.horaInicio.selectedItem.toString()
            val horaCierreString = binding.horaCierre.selectedItem.toString()

            val horaInicio = horaInicioString.substringBefore(":").toInt()
            val horaCierre = horaCierreString.substringBefore(":").toInt()

            val horario = Horarios.agregarHorario(Horario(lista, horaInicio, horaCierre))

            listener.elegirHorario(horario)
            dismiss()
        }
    }

    fun cargarDias(){

        DiaSemana.values().forEach {
            val chip = Chip(requireContext())
            chip.id = it.ordinal
            chip.text = it.name
            chip.isCheckable = true
            binding.listaDias.addView(chip)
        }

    }

    interface onHorarioCreadoListener{
        fun elegirHorario(horario:Horario)
    }

    private fun populateHoraCierreSpinner(hours: List<String>, spinner: Spinner) {
        val adapterCierre = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, hours)
        adapterCierre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterCierre
    }
}