package co.edu.eam.fakemaps.modelo

class Usuario(
    var id: Int,
    var nombre:String,
    var correo:String,
    var password:String,
    val ciudadDeResidencia: String,
    var is_admin: Boolean
) {

    override fun toString(): String {
        return "Usuario(id=$id, nombre='$nombre', correo='$correo', password='$password', ciudadDeResidencia='$ciudadDeResidencia', is_admin='$is_admin')"
    }
}