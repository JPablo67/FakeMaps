package co.edu.eam.fakemaps.activities

import ComentarioAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Comentarios
import co.edu.eam.fakemaps.bd.LocalStorage
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.databinding.ActivityDetalleLugarBinding
import co.edu.eam.fakemaps.modelo.Comentario
import co.edu.eam.fakemaps.modelo.Lugar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetalleLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetalleLugarBinding
    var idLugar: Int = 0
    private var colorPorDefecto: Int = 0
    private var estrellas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        colorPorDefecto = binding.estrellas.e1.textColors.defaultColor

        for ( i in 0 until binding.estrellas.lista.childCount){
            (binding.estrellas.lista[i] as TextView).setOnClickListener { presionarEstrella(i) }
        }


        idLugar = intent.extras!!.getInt("codigo")
        val lugar = Lugares.buscarById(idLugar)

        Toast.makeText(this, "${lugar.toString()}", Toast.LENGTH_SHORT).show()

        llenarCampos()
    }

    private fun llenarCampos() {
        //val lugar = Lugares.buscarById(idLugar)
        Firebase.firestore.collection("Lugares").document(idLugar.toString()).get().addOnSuccessListener {
            var lugarF = it.toObject(Lugar::class.java)
            if (lugarF != null) {
                lugarF.key = it.id
            }
            Log.e("Detalle lugar", lugarF.toString())
        }.addOnFailureListener {
            Log.e("Detalle lugar", it.message.toString())
        }

//        binding.nombreLugar.text = lugar!!.nombre
//        binding.descripcionLugar.text = lugar.descripcion
//        binding.direccionLugar.text = lugar.direccion
//        binding.creadorLugar.text = lugar.id.toString()
//        binding.categoriaLugar.text = lugar.idCategoria.toString()
//
//        // Configurar los comentarios
//        val comentarios = lugar.comentarios
//        val comentarioAdapter = ComentarioAdapter(comentarios)
//        binding.recyclerViewComentarios.layoutManager = LinearLayoutManager(this)
//        binding.recyclerViewComentarios.adapter = comentarioAdapter
//
//        // Agregar comentario
//        binding.btnEnviarComentario.setOnClickListener {
//            val textoComentario = binding.editTextComentario.text.toString()
//
//            if (textoComentario.isNotEmpty()) {
//
//                val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
//                val id = sp.getInt("id_user",0)
//                val idUsuario = id
//
//                if (idUsuario != null) {
//                    lugar.comentarios.add(Comentario(textoComentario, idUsuario, estrellas)) // 5 es una calificación de ejemplo
//
//
//
//                    Toast.makeText(this, "Comentario enviado", Toast.LENGTH_SHORT).show()
//                    binding.editTextComentario.text.clear()
//
//                    // Actualizar la lista de comentarios
//                    comentarioAdapter.actualizarComentarios(comentarios)
//                } else {
//                    Toast.makeText(this, "No se pudo obtener el ID del usuario", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun presionarEstrella(pos:Int){
        estrellas = pos+1
        borrarSeleccion()
        for( i in 0..pos ){
            (binding.estrellas.lista[i] as TextView).setTextColor( ContextCompat.getColor(this, R.color.yellow) )
        }
    }


    private fun borrarSeleccion(){
        for ( i in 0 until binding.estrellas.lista.childCount){
            (binding.estrellas.lista[i] as TextView).setTextColor( colorPorDefecto )
        }
    }
}
