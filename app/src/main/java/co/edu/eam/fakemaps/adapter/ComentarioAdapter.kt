import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Usuarios
import co.edu.eam.fakemaps.modelo.Comentario

class ComentarioAdapter(private var comentarios: List<Comentario>) : RecyclerView.Adapter<ComentarioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = comentarios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comentarios[position])
    }

    fun obtenerNombreUsuario(id: Int): String {
        val user = Usuarios.listar().find { it.id == id }
        return user?.nombre ?: "NombreUsuarioPredeterminado"
    }

    fun actualizarComentarios(comentarios: List<Comentario>) {
        this.comentarios = comentarios
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nombreUsuario: TextView = itemView.findViewById(R.id.nombre_usuario)
        private val textoComentario: TextView = itemView.findViewById(R.id.texto_comentario)

        fun bind(comentario: Comentario) {
            val userName =obtenerNombreUsuario(comentario.idUsuario);
            nombreUsuario.text = "$userName: ${comentario.texto}"
        }
    }


}



