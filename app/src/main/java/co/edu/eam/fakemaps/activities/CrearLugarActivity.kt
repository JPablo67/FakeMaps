package co.edu.eam.fakemaps.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Categorias
import co.edu.eam.fakemaps.bd.Ciudades
import co.edu.eam.fakemaps.bd.LocalStorage
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.databinding.ActivityCrearLugarBinding
import co.edu.eam.fakemaps.modelo.Categoria
import co.edu.eam.fakemaps.modelo.Ciudad
import co.edu.eam.fakemaps.modelo.EstadoLugar
import co.edu.eam.fakemaps.modelo.Lugar
import co.edu.eam.fakemaps.modelo.Posicion

class CrearLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCrearLugarBinding
    var posCiudad:Int = -1
    var posCategoria:Int = -1
    lateinit var ciudades:ArrayList<Ciudad>
    lateinit var categorias:ArrayList<Categoria>
    private fun obtenerIdUsuarioActual(): Int {
        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)

        val id = sp.getInt("id_user",0)

        return id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCrearLugarBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ciudades = Ciudades.listar()
        categorias = Categorias.listar()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cargarCiudades()
        cargarCategorias()



        binding.btnCrearLugar.setOnClickListener{crearLugar()}
    }

    fun cargarCiudades(){
        var lista = ciudades.map { c -> c.nombre }
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ciudadLugar.adapter = adapter
        binding.ciudadLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0:AdapterView<*>?, p1:View?,p2:Int, p3:Long){
                posCiudad = p2
                Toast.makeText(baseContext, "el elemento seleccionado fue ${p0!!.getItemAtPosition(p2).toString()}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }
    fun cargarCategorias(){
        var lista = categorias.map { c -> c.nombre }
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaLugar.adapter = adapter
        binding.categoriaLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0:AdapterView<*>?, p1:View?,p2:Int, p3:Long){
                posCategoria = p2
                Toast.makeText(baseContext, "el elemento seleccionado fue ${p0!!.getItemAtPosition(p2).toString()}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    fun crearLugar(){

        val nombre = binding.nombreLugar.text.toString()
        val descripcion = binding.descripcionLugar.text.toString()
        val direccion = binding.direccionLugar.text.toString()
        val telefono = binding.telefonoLugar.text.toString()
        val ciudad = ciudades[posCiudad].id
        val categoria = categorias[posCategoria].id

        if(nombre.isEmpty()){
            binding.nombreLugarLayout.error = "Este campo es onbligatorio"
        }else{
            binding.nombreLugarLayout.error = null
        }
        if(descripcion.isEmpty()){
            binding.descripcionLugarLayout.error = "Este campo es onbligatorio"
        }else{
            binding.descripcionLugarLayout.error = null
        }
        if(telefono.isEmpty()){
            binding.telefonoLugarLayout.error = "Este campo es onbligatorio"
        }else{
            binding.telefonoLugarLayout.error = null
        }
        if(direccion.isEmpty()){
            binding.direccionLugarLayout.error = "Este campo es onbligatorio"
        }else{
            binding.direccionLugarLayout.error = null
        }

        if (nombre.isNotEmpty() && direccion.isNotEmpty() && descripcion.isNotEmpty() && telefono.isNotEmpty() && ciudad!=-1&&categoria!=-1){
            val nuevoLugar = Lugar(1,nombre,descripcion,direccion,obtenerIdUsuarioActual(), EstadoLugar.SIN_REVISAR, categoria,
                Posicion(8f,8f),ciudad)

            val telefonos:ArrayList<String> = ArrayList()
            telefonos.add(telefono)
            nuevoLugar.telefonos = telefonos
            Lugares.crear(nuevoLugar)
            Log.e("CrearLugarActivity",Lugares.listar().toString())
        }else{

        }


    }
}