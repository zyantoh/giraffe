package com.giraffe.canteen.model

data class Location(
    val latitude: Double,
    val longitude: Double
) {
    fun distance(otherLocation: Location): Double {
        val deg2rad = { deg: Double -> deg * (Math.PI/180) }

        val earthRadius = 6371
        val dLat = deg2rad(otherLocation.latitude - this.latitude)
        val dLon = deg2rad(otherLocation.longitude - this.longitude)

        val a =
            Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(deg2rad(this.latitude)) * Math.cos(deg2rad(otherLocation.latitude)) *
                    Math.sin(dLon/2) * Math.sin(dLon/2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
        return earthRadius * c
    }
}
