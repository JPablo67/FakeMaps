package co.edu.eam.fakemaps.modelo

import java.util.Calendar
import java.util.Date

class Lugar(
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var direccion: String,
    var idCreador: Int,
    var estado: EstadoLugar,
    var idCategoria: Int,
    var posicion: Posicion,
    var idCiudad: Int
) {

    var imagenes: ArrayList<String> = ArrayList()
    var telefonos: ArrayList<String> = ArrayList()
    var fecha: Date = Date()
    var horarios: ArrayList<Horario> = ArrayList()
    var comentarios: List<Comentario> = listOf() // Inicialización de la lista de comentarios

    fun estAbierto(): String{
        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get (Calendar.HOUR_OF_DAY)
        //val minuto = fecha get (Calendar MINUTE)

        var mensaje = "Cerrado"

        for (horario in horarios){
            if( horario.diaSemana.contains( DiaSemana.values()[dia-1] ) && hora < horario.horaCierre && hora >horario.horaInicio){
                mensaje = "Abierto"
                break
            }
        }
        return mensaje
    }

    fun obtenerCalificacionPromedio (comentarios:ArrayList<Comentario>): Int{
        var promedio = 0

        if (comentarios.size>0){
            val suma = comentarios.stream().map { c -> c.calificacion }.reduce{suma, valor -> suma+valor}.get()

            promedio = suma/comentarios.size
        }

        return promedio

    }
    override fun toString(): String {
        return "Lugar(id=$id, nombre='$nombre', descripcion='$descripcion', direccion='$direccion', idCreador=$idCreador, estado=$estado, idCategoria=$idCategoria, posicion=$posicion, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, fecha=$fecha, horarios=$horarios)"
    }
}
