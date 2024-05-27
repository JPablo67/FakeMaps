package co.edu.eam.fakemaps.fragmentos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.Lugares
import co.edu.eam.fakemaps.databinding.FragmentInicioBinding
import co.edu.eam.fakemaps.databinding.FragmentSearchBarBinding
import co.edu.eam.fakemaps.modelo.Lugar
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class InicioFragment : Fragment() ,OnMapReadyCallback{

    lateinit var binding: FragmentInicioBinding
    lateinit var gMap:GoogleMap
    private var tienePermiso = false
    private val defaultLocation = LatLng(4.550923, -75.6557201)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocationPermission()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById( R.id.mapa_principal) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getLocationPermission()


        return binding.root
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

        Firebase.firestore.collection("lugares").get().addOnSuccessListener {
            for (document in it) {
                var lugar = document.toObject(Lugar::class.java)
                lugar.key = document.id

                gMap.addMarker(MarkerOptions().position(LatLng(lugar.posicion.lat,lugar.posicion.lng))
                    .title(lugar.nombre))!!.tag = lugar.key
            }
        }

        Lugares.listarAprobados().forEach {
            val latlng = LatLng(it.posicion.lat,it.posicion.lng)
            gMap.addMarker(MarkerOptions().position(latlng).title(it.nombre).title(it.nombre).visible(true))

        }


        obtenerUbicacion()

    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            tienePermiso = true
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)

        }
    }

    private fun obtenerUbicacion() {
        try {
            if (tienePermiso) {
                val ubicacionActual =
                    LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
                ubicacionActual.addOnCompleteListener(requireActivity()) {
                    if (it.isSuccessful) {
                        val ubicacion = it.result
                        if (ubicacion != null) {
                            val latlng = LatLng(ubicacion.latitude, ubicacion.longitude)
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 8F)
                            )

                            gMap.addMarker(MarkerOptions().position(latlng).title("Marcador en el mapa"))
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
                val mapFragment = childFragmentManager.findFragmentById(R.id.mapa_principal) as SupportMapFragment
                mapFragment.getMapAsync(this)
            } else {
                tienePermiso = false
                // Permission denied, show a message to the user or handle accordingly
            }
        }
    }

}