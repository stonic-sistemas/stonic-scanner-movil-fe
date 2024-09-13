package com.stonic.stonic_scanner_movil_fe.model

import java.time.LocalDateTime

data class Producto(
    val idProducto: Long,
    val nombre: String,
    val audFechaRegistro: LocalDateTime?,
    val audFechaModifico: LocalDateTime?,
    val codigo: String?,
    val idProductora: Long?,
    val urlImagen: String?,
    val flagRevendible: Boolean,
    val abarrote: Abarrote?
)