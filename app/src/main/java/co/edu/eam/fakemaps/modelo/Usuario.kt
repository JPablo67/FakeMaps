package co.edu.eam.fakemaps.modelo

class Usuario(
    var id:Int,
    var nombre:String,
    var nickname:String,
    var correo:String,
    var password:String,
    val ciudadDeResidencia: String
) {

    override fun toString(): String {
        return "Usuario(id=$id, nombre='$nombre', nickname='$nickname', correo='$correo', password='$password', ciudadDeResidencia='$ciudadDeResidencia')"
    }
}