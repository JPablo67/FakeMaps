package co.edu.eam.fakemaps.bd

import co.edu.eam.fakemaps.modelo.Ciudad
import co.edu.eam.fakemaps.modelo.Lugar

object Ciudades {

    private val lista:ArrayList<Ciudad> = ArrayList()
    private var lastCityId: Int = 0

    init {
        lista.add(Ciudad(1,"Armenia"))
        lista.add(Ciudad(2,"Salento"))
        lista.add(Ciudad(3,"Pereira"))
        lista.add(Ciudad(4,"Calarca"))
        lista.add(Ciudad(5,"Manizales"))

        lastCityId = lista.maxByOrNull { it.id }?.id ?: 0
    }


    fun crear(ciudad:Ciudad){
        lastCityId++
        ciudad.id = lastCityId
        lista.add( ciudad )
    }

    fun listar():ArrayList<Ciudad>{
        return lista
    }

    fun obtenerCiudadById(id:Int):Ciudad?{
        return lista.firstOrNull{c -> c.id == id }

    }
}