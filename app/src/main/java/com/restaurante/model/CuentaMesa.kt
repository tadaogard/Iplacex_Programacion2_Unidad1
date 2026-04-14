package com.restaurante.model

/**
 * Representa la cuenta de una mesa del restaurante.
 *
 * Gestiona una lista de ítems pedidos (ItemMesa) y calcula
 * los totales incluyendo o excluyendo la propina.
 *
 * @property mesa Número de mesa
 * @author Diego Herrera - diego.herrera.g@alumno.iplacex.cl
 * @since 1.0
 */
class CuentaMesa(
    val mesa: Int
) {
    private val _items: MutableList<ItemMesa> = mutableListOf()


    var aceptaPropina: Boolean = true


    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val itemMesa = ItemMesa(itemMenu, cantidad)
        _items.add(itemMesa)
    }

    fun agregarItem(itemMesa: ItemMesa) {
        _items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        return _items.sumOf { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int {
        return if (aceptaPropina) {
            (calcularTotalSinPropina() * 0.10).toInt()
        } else {
            0
        }
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }

    fun obtenerItems(): List<ItemMesa> {
        return _items.toList()
    }
}