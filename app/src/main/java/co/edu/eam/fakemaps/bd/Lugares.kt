package co.edu.eam.fakemaps.bd

import android.util.Log
import co.edu.eam.fakemaps.modelo.Comentario
import co.edu.eam.fakemaps.modelo.EstadoLugar
import co.edu.eam.fakemaps.modelo.Horario
import co.edu.eam.fakemaps.modelo.Lugar
import co.edu.eam.fakemaps.modelo.Posicion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Lugares {

    private val lista:ArrayList<Lugar> = ArrayList()
    private var lastLugarId: Int = 0

    init {

//        val horario1 = Horario(1, Horarios.obtenerTodos(),12,20)
//        val horario2 = Horario(2, Horarios.obtenerEntreSemana(),9,20)
//        val horario3 = Horario(3, Horarios.obtenerFinSemana(),14,23)
//
//        val comentario1 = Comentario("Super todo", 1, 5)
//        val comentario2 = Comentario("Excelentre", 1,  5)
//        val comentario3 = Comentario("Muy bien", 1,  5)
//        val comentario4 = Comentario("Bueno", 1,  5)
//        val comentario5 = Comentario("Nice", 1,  5)
//        val comentario6 = Comentario("Cool", 1,  5)
//
//        val lugar1 = Lugar(1, "Cafe 123", "Lindo", "Cra 25",1,EstadoLugar.ACEPTADO, 2, Posicion(4.548988,-75.6615188), 1)
//        lugar1.horarios.add(horario1)
//        lugar1.comentarios.add(comentario1)
//
//        val lugar2 = Lugar(2, "Restaurante 123", "Rico", "Cra 25",1,EstadoLugar.ACEPTADO, 3, Posicion(4.5499408,-75.6569629), 1)
//        lugar2.horarios.add(horario2)
//        lugar2.comentarios.add(comentario1)
//
//        val lugar3 = Lugar(3, "Bar 123", "Parchado", "Cra 25",1,EstadoLugar.ACEPTADO, 5, Posicion(4.5345584,-75.6783924), 1)
//        lugar3.horarios.add(horario1)
//        lugar3.comentarios.add(comentario3)
//
//        val lugar4 = Lugar(4, "Taller 123", "Lindo", "Cra 25",2,EstadoLugar.ACEPTADO, 4, Posicion(4.5387929,-75.6753918), 1)
//        lugar4.horarios.add(horario1)
//
//        val lugar5 = Lugar(5, "Restaurante Fulanito", "Rico", "Cra 25",2,EstadoLugar.ACEPTADO, 3, Posicion(4.5396581,-75.6727824), 1)
//        lugar5.horarios.add(horario2)
//        lugar5.comentarios.add(comentario4)
//
//        val lugar6 = Lugar(6, "Discoteca 456", "Parchado", "Cra 25",2, EstadoLugar.ACEPTADO, 5, Posicion(4.5502249,-75.671664), 1)
//        lugar6.horarios.add(horario3)
//
//        lista.add(lugar1)
//        lista.add(lugar3)
//        lista.add(lugar2)
//        lista.add(lugar4)
//        lista.add(lugar5)
//        lista.add(lugar6)
//
//        lastLugarId = lista.maxByOrNull { it.id }?.id ?: 0


    }



    fun crear(lugar:Lugar){
        lastLugarId++
        lugar.id = lastLugarId
        lista.add( lugar )
    }

    fun editar(lugar: Lugar) {
        val index = lista.indexOfFirst { it.id == lugar.id }
        if (index != -1) {
            lista[index] = lugar
        }
    }

    fun eliminar(id: Int) {
        val index = lista.indexOfFirst { it.id == id }
        if (index != -1) {
            lista.removeAt(index)
        }
    }

    fun listar(): ArrayList<Lugar>{
        return lista
    }

    fun listarAprobados(): ArrayList<Lugar>{
        return lista.filter { l -> l.estado.equals(EstadoLugar.ACEPTADO) }.toCollection(ArrayList())
    }

    fun listarRechazados(): ArrayList<Lugar>{
        return lista.filter { l -> l.estado.equals(EstadoLugar.RECHAZADO) }.toCollection(ArrayList())
    }

    fun listarNoRevisados(): ArrayList<Lugar>{
        return lista.filter { l -> l.estado.equals(EstadoLugar.SIN_REVISAR) }.toCollection(ArrayList())
    }

    fun buscarById(id:Int):Lugar?{
        return lista.firstOrNull {l -> l.id == id }
    }

    fun buscarByNombre(nombre:String):ArrayList<Lugar>{
        return lista.filter { l -> l.nombre.lowercase().contains(nombre.lowercase()) }.toCollection(ArrayList())
    }

    fun buscarByCiudad(codigoCiudad:Int):ArrayList<Lugar>{
        return lista.filter { l -> l.idCiudad == codigoCiudad }.toCollection(ArrayList())
    }

    fun buscarByCategoria(codigoCategoria:Int):ArrayList<Lugar>{
        return lista.filter { l -> l.idCiudad == codigoCategoria }.toCollection(ArrayList())
    }

    fun buscarPorUsuario(userId: Int): List<Lugar> {
        return lista.filter { it.idCreador == userId }
    }

    fun buscarPorNombre(nombre: String, callback: (ArrayList<Lugar>) -> Unit) {
        val lugares = ArrayList<Lugar>()
        Firebase.firestore.collection("lugares")
            .whereEqualTo("estado", EstadoLugar.ACEPTADO)
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val lugar = doc.toObject(Lugar::class.java)
                    if (lugar.nombre.lowercase().contains(nombre.lowercase())) {
                        lugar.key = doc.id
                        lugares.add(lugar)
                    }
                }
                callback(lugares) // Call the callback with the fetched data
            }
            .addOnFailureListener { exception ->
                Log.e("Lugares", "Error al obtener los lugares", exception)
                callback(lugares) // Return an empty list on failure
            }
    }

    fun buscarPorIdCreador(idCreador: Int, callback: (ArrayList<Lugar>) -> Unit) {
        val lugares = ArrayList<Lugar>()
        Firebase.firestore.collection("lugares")
            .whereEqualTo("estado", EstadoLugar.ACEPTADO)
            .whereEqualTo("idCreador", idCreador)
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id
                    lugares.add(lugar)
                }
                callback(lugares) // Call the callback with the fetched data
            }
            .addOnFailureListener { exception ->
                Log.e("Lugares", "Error al obtener los lugares", exception)
                callback(lugares) // Return an empty list on failure
            }
    }





}