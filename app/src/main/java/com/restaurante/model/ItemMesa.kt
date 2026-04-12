package com.restaurante.model

/**
 * Representa un ítem solicitado en la mesa.
 *
 * Establece una asociación 1 a 1 con ItemMenu y permite
 * gestionar la cantidad solicitada por el cliente.
 *
 * @property itemMenu Referencia al ítem del menú asociado
 * @property cantidad Cantidad solicitada del ítem
 * @author Diego Herrera - diego.herrera.g@alumno.iplacex.cl
 * @since 1.0
 */
class ItemMesa(
    val itemMenu: ItemMenu,
    var cantidad: Int
) {
    /**
     * Calcula el subtotal para este ítem.
     *
     */
    fun calcularSubtotal(): Int {
        val precioInt = itemMenu.precio.replace(".", "").replace("$", "").toIntOrNull() ?: 0
        return precioInt * cantidad
    }
}