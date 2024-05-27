package co.edu.eam.fakemaps.modelo

class Usuario() {

    constructor(id: Int, nombre:String, correo:String, password:String, ciudadDeResidencia: String, is_admin: Boolean) : this() {
        this.id = id
        this.nombre = nombre
        this.correo = correo
        this.password = password
        this.ciudadDeResidencia = ciudadDeResidencia
        this.is_admin = is_admin
    }

    var id: Int = 0
    var key: String = ""
    var nombre:String = ""
    var correo:String = ""
    var password:String = ""
    var ciudadDeResidencia: String = ""
    var is_admin: Boolean = false

    override fun toString(): String {
        return "Usuario(id=$id, nombre='$nombre', correo='$correo', password='$password', ciudadDeResidencia='$ciudadDeResidencia', is_admin='$is_admin')"
    }
}