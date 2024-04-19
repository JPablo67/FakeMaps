package co.edu.eam.fakemaps.modelo

class Comentario(
    var id:Int,
    var texto:String,
    var idUsuario:Int,
    var idLugar:Int,
    var calificacion:Int,
    ) {// En Comentario.kt

    fun copy(
        id: Int = this.id,
        texto: String = this.texto,
        idUsuario: Int = this.idUsuario,
        idLugar: Int = this.idLugar,
        calificacion: Int = this.calificacion
    ) = Comentario(id, texto, idUsuario, idLugar, calificacion)


}