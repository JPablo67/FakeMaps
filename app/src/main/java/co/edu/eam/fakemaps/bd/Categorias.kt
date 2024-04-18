package co.edu.eam.fakemaps.bd

import co.edu.eam.fakemaps.modelo.Categoria
import co.edu.eam.fakemaps.modelo.Ciudad

object Categorias {

    private val categorias:ArrayList<Categoria> = ArrayList()
    private var lastCategoryid: Int = 0

    init {
        categorias.add(Categoria(1, "Hotel","\uf594"))
        categorias.add(Categoria(2, "Cafeteria","\uf7b6"))
        categorias.add(Categoria(3, "Restaurante","\uf2e7"))
        categorias.add(Categoria(4, "Taller","\uf2e7"))
        categorias.add(Categoria(5, "Bar","\uf0fc"))

        lastCategoryid = categorias.maxByOrNull { it.id }?.id ?: 0
    }

    fun crear(categoria: Categoria){
        lastCategoryid++
        categoria.id = lastCategoryid
        categorias.add( categoria )
    }

    fun listar():ArrayList<Categoria>{
        return categorias
    }

    fun obtenerById(id:Int):Categoria? {
        return categorias.firstOrNull { c -> c.id == id }
    }


}