package co.edu.eam.fakemaps.bd

import co.edu.eam.fakemaps.modelo.Usuario

object Usuarios {

    private val usuarios:ArrayList<Usuario> = ArrayList()
    private var lastUserId: Int = 0

    init {

        usuarios.add(Usuario(1, "Alejandro",  "Alejandro@gmail.com","123", "Calarcá",false))
        usuarios.add(Usuario(2, "Mariana",  "mariana@gmail.com","123","Armenia", false))
        usuarios.add(Usuario(3, "Sebas",   "sebas@gmail.com","123","Montnoir", true))
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

    // Tu función login
    fun login(correo: String, contrasena: String): Usuario? {
        val usuario = usuarios.find { it.correo == correo && it.password == contrasena }
        return usuario
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