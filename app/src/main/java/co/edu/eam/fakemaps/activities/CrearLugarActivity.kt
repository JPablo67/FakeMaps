package co.edu.eam.fakemaps.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Categorias
import co.edu.eam.fakemaps.bd.Ciudades
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.databinding.ActivityCrearLugarBinding
import co.edu.eam.fakemaps.fragmentos.HorarioDialogoFragment
import co.edu.eam.fakemaps.modelo.Categoria
import co.edu.eam.fakemaps.modelo.Ciudad
import co.edu.eam.fakemaps.modelo.EstadoLugar
import co.edu.eam.fakemaps.modelo.Horario
import co.edu.eam.fakemaps.modelo.Lugar
import co.edu.eam.fakemaps.modelo.Posicion
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CrearLugarActivity : AppCompatActivity() , HorarioDialogoFragment.onHorarioCreadoListener, OnMapReadyCallback {

    lateinit var binding: ActivityCrearLugarBinding
    var posCiudad:Int = -1
    var posCategoria:Int = -1
    lateinit var ciudades:ArrayList<Ciudad>
    lateinit var categorias:ArrayList<Categoria>
    lateinit var horarios:ArrayList<Horario>
    lateinit var gMap:GoogleMap
    private var tienePermiso = false
    private val defaultLocation = LatLng(4.550923, -75.6557201)
    private var posicion:Posicion? = null


    private fun obtenerIdUsuarioActual(): Int {
        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)

        val id = sp.getInt("id_user",0)

        return id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        binding = ActivityCrearLugarBinding.inflate(layoutInflater)

        setContentView(binding.root)

        horarios = ArrayList()

        ciudades = Ciudades.listar()
        categorias = Categorias.listar()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cargarCiudades()
        cargarCategorias()


        binding.asignarHorario.setOnClickListener { mostrarDialogo() }
        binding.btnCrearLugar.setOnClickListener{crearLugar()}

        val mapFragment = supportFragmentManager.findFragmentById( R.id.mapa_crear_lugar) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getLocationPermission()
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

        if (nombre.isNotEmpty() && direccion.isNotEmpty() && horarios.isNotEmpty() && descripcion.isNotEmpty() && telefono.isNotEmpty() && ciudad!=-1&&categoria!=-1){

            if(posicion!=null){
                val nuevoLugar = Lugar(1,nombre,descripcion,direccion,obtenerIdUsuarioActual(), EstadoLugar.SIN_REVISAR, categoria,
                    posicion!!,ciudad)

                val telefonos:ArrayList<String> = ArrayList()
                telefonos.add(telefono)
                nuevoLugar.telefonos = telefonos
                nuevoLugar.horarios = horarios
                Lugares.crear(nuevoLugar)

                Firebase.firestore
                    .collection("lugares").add( nuevoLugar!! )
                    .addOnSuccessListener {
                        Snackbar.make(binding.root, getString(R.string.abre_el), Snackbar.LENGTH_LONG).show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 4000)
                    }
                    .addOnFailureListener{
                        Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
                    }

                Log.e("CrearLugarActivity",Lugares.listar().toString())
            }else{
                Log.e("CrearLugarActivity","hola")
            }


        }else{

        }


    }

    fun mostrarDialogo(){
        val dialog = HorarioDialogoFragment()
        dialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogoTitulo)
        dialog.listener=this
        dialog.show(supportFragmentManager,"Agregar")
    }

    override fun elegirHorario(horario: Horario) {
        horarios.add(horario)
    }

    override fun onMapReady(p0: GoogleMap) {
        gMap = p0

        try {
            if (tienePermiso) {
                gMap.isMyLocationEnabled = true
                gMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                gMap.isMyLocationEnabled = false
                gMap.uiSettings.isMyLocationButtonEnabled = false
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        obtenerUbicacion()

        gMap.uiSettings.isZoomControlsEnabled = true
        gMap.setOnMapClickListener {
            if (posicion == null) {
                posicion = Posicion()
            }
            posicion?.let { pos ->
                pos.lat = it.latitude
                pos.lng = it.longitude
            }
            gMap.clear()
            gMap.addMarker(MarkerOptions().position(it).title("Aqui esta el lugar"))
        }
    }


    private fun obtenerUbicacion() {
        try {
            if (tienePermiso) {
                val ubicacionActual =
                    LocationServices.getFusedLocationProviderClient(this).lastLocation
                ubicacionActual.addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        val ubicacion = it.result
                        if (ubicacion != null) {
                            val latlng = LatLng(ubicacion.latitude, ubicacion.longitude)
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15F)
                            )
                        }
                    } else {
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,
                            14F))
                        gMap.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            tienePermiso = true
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                tienePermiso = true
                // Refresh the map
                val mapFragment = supportFragmentManager.findFragmentById(R.id.mapa_principal) as SupportMapFragment
                mapFragment.getMapAsync(this)
            } else {
                tienePermiso = false
                // Permission denied, show a message to the user or handle accordingly
            }
        }
    }
}