package co.edu.eam.fakemaps.bd

import co.edu.eam.fakemaps.modelo.Comentario

object Comentarios {

    private val lista:ArrayList<Comentario> = ArrayList()
    private var lastComentId: Int = 0

    init {
        lista.add( Comentario(1, "Super todo", 1, 1, 5))
        lista.add( Comentario(2, "Excelentre", 1, 1, 5))
        lista.add( Comentario(3, "Muy bien", 1, 1, 5))
        lista.add( Comentario(4, "Bueno", 1, 1, 5))
        lista.add( Comentario(5, "Nice", 1, 1, 5))
        lista.add( Comentario(6, "Cool", 1, 1, 5))

        lastComentId = lista.maxByOrNull { it.id }?.id ?: 0
    }

    fun listar(idLugar:Int):ArrayList<Comentario>{
        return lista.filter { c -> c.idLugar == idLugar }.toCollection(ArrayList())
    }

    fun crear (comentario: Comentario){
        lastComentId++
        comentario.id = lastComentId
        lista.add(comentario)
    }

    fun eliminar(id: Int) {
        val index = lista.indexOfFirst { it.id == id }
        if (index != -1) {
            lista.removeAt(index)
        }
    }

}