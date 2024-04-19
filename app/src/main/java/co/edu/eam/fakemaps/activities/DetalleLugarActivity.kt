package co.edu.eam.fakemaps.activities

import ComentarioAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Comentarios
import co.edu.eam.fakemaps.bd.LocalStorage
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.databinding.ActivityDetalleLugarBinding
import co.edu.eam.fakemaps.modelo.Comentario
import co.edu.eam.fakemaps.modelo.Lugar

class DetalleLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetalleLugarBinding
    var idLugar: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        idLugar = intent.extras!!.getInt("codigo")
        val lugar = Lugares.buscarById(idLugar)

        Toast.makeText(this, "${lugar.toString()}", Toast.LENGTH_SHORT).show()

        llenarCampos()
    }

    private fun llenarCampos() {
        val lugar = Lugares.buscarById(idLugar)
        binding.nombreLugar.text = lugar!!.nombre
        binding.descripcionLugar.text = lugar.descripcion
        binding.direccionLugar.text = lugar.direccion
        binding.creadorLugar.text = lugar.id.toString()
        binding.categoriaLugar.text = lugar.idCategoria.toString()

        // Configurar los comentarios
        val comentarios = Comentarios.listar(idLugar)
        val comentarioAdapter = ComentarioAdapter(comentarios)
        binding.recyclerViewComentarios.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewComentarios.adapter = comentarioAdapter

        // Agregar comentario
        binding.btnEnviarComentario.setOnClickListener {
            val textoComentario = binding.editTextComentario.text.toString()

            if (textoComentario.isNotEmpty()) {
                val idUsuario = LocalStorage.User?.id

                if (idUsuario != null) {
                    Comentarios.crear(Comentario(0, textoComentario, idUsuario, idLugar, 5)) // 5 es una calificación de ejemplo
                    Toast.makeText(this, "Comentario enviado", Toast.LENGTH_SHORT).show()
                    binding.editTextComentario.text.clear()

                    // Actualizar la lista de comentarios
                    comentarioAdapter.actualizarComentarios(Comentarios.listar(idLugar))
                } else {
                    Toast.makeText(this, "No se pudo obtener el ID del usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
