package co.edu.eam.fakemaps.bd

import co.edu.eam.fakemaps.modelo.Usuario

object Usuarios {

    private val usuarios:ArrayList<Usuario> = ArrayList()
    private var lastUserId: Int = 0

    init {

        usuarios.add(Usuario(1, "Carlos", "carlos",  "carlos@gmail.com","123","Armenia"))
        usuarios.add(Usuario(2, "Mariana", "mariana",  "mariana@gmail.com","123","Armenia"))
        usuarios.add(Usuario(3, "Sebas", "sebas",  "sebas@gmail.com","123","Montnoir"))

        lastUserId = usuarios.maxByOrNull { it.id }?.id ?: 0
    }

    fun listar():ArrayList<Usuario>{
        return usuarios
    }

    fun agregar(usuario: Usuario){
        lastUserId++
        usuario.id = lastUserId
        usuarios.add(usuario)
    }

    fun login(correo:String, pass:String):Usuario{
        val res = usuarios.first { u -> u.password == pass && u.correo == correo }
        return res
    }

    fun editar(usuario: Usuario) {
        val index = usuarios.indexOfFirst { it.id == usuario.id }
        if (index != -1) {
            usuarios[index] = usuario
        }
    }

    fun eliminar(id: Int) {
        val index = usuarios.indexOfFirst { it.id == id }
        if (index != -1) {
            usuarios.removeAt(index)
        }
    }


}