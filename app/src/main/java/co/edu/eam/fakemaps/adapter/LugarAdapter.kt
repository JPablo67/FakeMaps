package co.edu.eam.fakemaps.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.activities.DetalleLugarActivity
import co.edu.eam.fakemaps.modelo.Lugar

class LugarAdapter(private val lista: MutableList<Lugar>) : RecyclerView.Adapter<LugarAdapter.ViewHolder>() {

    var lugarSeleccionadoIndex: Int = RecyclerView.NO_POSITION
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lugar, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    fun obtenerLugarSeleccionado(): Lugar? {
        return if (lugarSeleccionadoIndex != RecyclerView.NO_POSITION) {
            lista[lugarSeleccionadoIndex]
        } else {
            null
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        private val nombre: TextView = itemView.findViewById(R.id.nombre_lugar)
        private val direccion: TextView = itemView.findViewById(R.id.direccion_lugar)
        private val estado: TextView = itemView.findViewById(R.id.estado_lugar)
        private val horario: TextView = itemView.findViewById(R.id.horario_lugar)
        private val imagen: ImageView = itemView.findViewById(R.id.imagen_lugar)
        private var codigoLugar = 0
        private lateinit var lugar: Lugar

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bind(lugar: Lugar) {
            this.lugar = lugar
            nombre.text = lugar.nombre
            horario.text = "Cierra a las 2:00"
            direccion.text = lugar.direccion
            estado.text = "Abierto"
            codigoLugar = lugar.id
        }

        override fun onClick(v: View?) {
            val intent = Intent(nombre.context, DetalleLugarActivity::class.java)
            intent.putExtra("codigo", codigoLugar)
            nombre.context.startActivity(intent)
        }

        override fun onLongClick(v: View?): Boolean {
            lugarSeleccionadoIndex = adapterPosition
            notifyDataSetChanged()
            return true
        }
    }
}
