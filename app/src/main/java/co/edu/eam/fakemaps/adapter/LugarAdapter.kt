package co.edu.eam.fakemaps.adapter

import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.fakemaps.R

import co.edu.eam.fakemaps.activities.DetalleLugarActivity
import co.edu.eam.fakemaps.databinding.ItemLugarBinding
import co.edu.eam.fakemaps.modelo.Comentario
import co.edu.eam.fakemaps.modelo.Lugar

class LugarAdapter(private val lista: MutableList<Lugar>) : RecyclerView.Adapter<LugarAdapter.ViewHolder>() {

    var lugarSeleccionadoIndex: Int = RecyclerView.NO_POSITION
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemLugarBinding.inflate( LayoutInflater.from(parent.context), parent, false )
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

    inner class ViewHolder(private var view: ItemLugarBinding) : RecyclerView.ViewHolder(view.root), View.OnClickListener, View.OnLongClickListener {

        private val nombre: TextView = itemView.findViewById(R.id.nombre_lugar)
        private val direccion: TextView = itemView.findViewById(R.id.direccion_lugar)
        private val estado: TextView = itemView.findViewById(R.id.estado_lugar)
        private val horario: TextView = itemView.findViewById(R.id.horario_lugar)
        private val imagen: ImageView = itemView.findViewById(R.id.img_lugar)
        private var codigoLugar = 0
        private lateinit var lugar: Lugar

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bind(lugar: Lugar) {
            this.lugar = lugar
            nombre.text = lugar.nombre
            horario.text = ""
            direccion.text = lugar.direccion
            val estaAbierto = lugar.estaAbierto()
            if(estaAbierto){
                view.estadoLugar.text = itemView.context.getString(R.string.abierto)
                view.estadoLugar.setTextColor( ContextCompat.getColor(itemView.context, R.color.light_green ) )
                view.horarioLugar.text = itemView.context.getString(R.string.cierra_a_las)+" "+lugar.obtenerHoraCierre()
                Log.e("Fr",R.string.cierra_a_las.toString())
            }else{
                Log.e("Lugar adapter", "this")
                view.estadoLugar.text = itemView.context.getString(R.string.cerrado)
                view.estadoLugar.setTextColor( ContextCompat.getColor(itemView.context, R.color.red_accent ) )
                view.horarioLugar.text = itemView.context.getString(R.string.abre_el)+" "+lugar.obtenerHoraApertura()
            }

            val comentarios:ArrayList<Comentario> = lugar.comentarios
            val calificacion = lugar.obtenerCalificacionPromedio( comentarios )

            for( i in 0..calificacion-1 ){
                Log.e("Lugar adapter", "$i")
                (view.listaEstrellas[i] as TextView).setTextColor( ContextCompat.getColor(view.listaEstrellas.context, R.color.yellow) )
            }

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
