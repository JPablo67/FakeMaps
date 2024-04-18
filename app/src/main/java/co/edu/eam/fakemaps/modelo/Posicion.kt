package co.edu.eam.fakemaps.modelo

class Posicion (){

    var lat:Double = 0.0
    var lng:Double = 0.0

    constructor(lat: Float, lng: Float):this(){
        this.lat = lat.toDouble()
        this.lng = lng.toDouble()
    }

    override fun toString(): String {
        return "Posicion(lat=$lat, lng=$lng)"
    }
}