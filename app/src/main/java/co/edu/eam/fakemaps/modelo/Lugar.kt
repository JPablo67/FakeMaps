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
    var comentarios: ArrayList<Comentario> = ArrayList() // Inicialización de la lista de comentarios

    fun estaAbierto():Boolean{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get(Calendar.HOUR_OF_DAY)
        //val minuto = fecha.get(Calendar.MINUTE)

        var mensaje = false

        for(horario in horarios){
            if( horario.diaSemana.contains( DiaSemana.values()[dia-1] ) && hora < horario.horaCierre && hora > horario.horaInicio  ){
                mensaje = true
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

    fun obtenerHoraCierre():String{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)

        var mensaje = ""

        for(horario in horarios){
            if( horario.diaSemana.contains( DiaSemana.values()[dia-1] ) ){
                mensaje = "${horario.horaCierre}:00"
                break
            }
        }

        return mensaje
    }

    fun obtenerHoraApertura():String{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get(Calendar.HOUR_OF_DAY)

        var mensaje = ""
        var pos:Int

        for(horario in horarios){
            pos = horario.diaSemana.indexOf( DiaSemana.values()[dia-1] )
            mensaje = if( pos!=-1 ){
                if( horario.horaInicio > hora ){
                    "${horario.diaSemana[pos].toString().lowercase()} a las ${horario.horaInicio}:00"
                }else{
                    if(horario.diaSemana[pos]==horario.diaSemana[4]){
                        "${horario.diaSemana[0].toString().lowercase()} a las ${horario.horaInicio}:00"
                    }else{
                    "${horario.diaSemana[pos+1].toString().lowercase()} a las ${horario.horaInicio}:00"
                    }
                }
            }else{
                "${horario.diaSemana[0].toString().lowercase()} a las ${horario.horaInicio}:00"
            }
            break
        }

        return mensaje
    }


    override fun toString(): String {
        return "Lugar(id=$id, nombre='$nombre', descripcion='$descripcion', direccion='$direccion', idCreador=$idCreador, estado=$estado, idCategoria=$idCategoria, posicion=$posicion, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, fecha=$fecha, horarios=$horarios)"
    }
}
