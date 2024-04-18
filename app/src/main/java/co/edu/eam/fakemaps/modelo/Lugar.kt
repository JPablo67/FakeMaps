package co.edu.eam.fakemaps.modelo

import java.util.Date

class Lugar(
    var id:Int,
    var nombre:String,
    var descripcion:String,
    var direccion:String,
    var idCreador:Int,
    var estado:EstadoLugar,
    var idCategoria:Int,
    var posicion: Posicion,
    var idCiudad:Int,


) {

    var imagenes:ArrayList<String> = ArrayList()
    var telefonos:ArrayList<String> = ArrayList()
    var fecha:Date = Date()
    var horarios:ArrayList<Horario> = ArrayList()

    override fun toString(): String {
        return "Lugar(id=$id, nombre='$nombre', descripcion='$descripcion', direccion='$direccion', idCreador=$idCreador, estado=$estado, idCategoria=$idCategoria, posicion=$posicion, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, fecha=$fecha, horarios=$horarios)"
    }


}