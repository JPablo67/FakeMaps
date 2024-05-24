package co.edu.eam.fakemaps.bd

import co.edu.eam.fakemaps.modelo.Comentario

object Comentarios {

    private val lista:ArrayList<Comentario> = ArrayList()


    init {
        lista.add( Comentario("Super todo", 1, 5))
        lista.add( Comentario("Excelentre", 1,  5))
        lista.add( Comentario("Muy bien", 1,  5))
        lista.add( Comentario("Bueno", 1,  5))
        lista.add( Comentario("Nice", 1,  5))
        lista.add( Comentario("Cool", 1,  5))


    }



    fun crear(comentario: Comentario) {
        lista.add(comentario)
    }



}