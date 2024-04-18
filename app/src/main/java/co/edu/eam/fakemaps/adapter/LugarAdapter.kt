package co.edu.eam.fakemaps.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.activities.DetalleLugarActivity
import co.edu.eam.fakemaps.modelo.Lugar

class LugarAdapter(var lista:ArrayList<Lugar>): RecyclerView.Adapter<LugarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lugar, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount()= lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( lista[position] )
    }

    inner class ViewHolder(var itemView:View):RecyclerView.ViewHolder(itemView), OnClickListener{

        val nombre:TextView = itemView.findViewById(R.id.nombre_lugar)
        val direccion:TextView = itemView.findViewById(R.id.direccion_lugar)
        val estado:TextView = itemView.findViewById(R.id.estado_lugar)
        val horario:TextView = itemView.findViewById(R.id.horario_lugar)
        val imagen:ImageView = itemView.findViewById(R.id.imagen_lugar)
        var codigoLugar = 0

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(lugar:Lugar){
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
    }


}