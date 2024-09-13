package com.stonic.stonic_scanner_movil_fe.model

import java.math.BigDecimal

data class Abarrote(
    val idAbarrote: Long,
    val contenido: BigDecimal,
    val contenidoUnidad: Long,
    val contenidoaMedida: Long?
)