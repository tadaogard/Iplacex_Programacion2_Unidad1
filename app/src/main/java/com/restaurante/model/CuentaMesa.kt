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
    /**
     * Lista mutable de ítems en la cuenta.
     * Relación de composición: la cuenta controla el ciclo de vida de los ítems.
     */
    private val _items: MutableList<ItemMesa> = mutableListOf()

    /**
     * Indica si el cliente acepta incluir propina (10%).
     * Por defecto es true.
     */
    var aceptaPropina: Boolean = true

    /**
     * Agrega un nuevo ítem a la cuenta.
     *
     * @param itemMenu Ítem del menú que se desea agregar
     * @param cantidad Cantidad solicitada del ítem
     */
    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val itemMesa = ItemMesa(itemMenu, cantidad)
        _items.add(itemMesa)
    }

    /**
     * Agrega un ítem ya creado a la cuenta.
     *
     * @param itemMesa Ítem de mesa que se desea agregar
     */
    fun agregarItem(itemMesa: ItemMesa) {
        _items.add(itemMesa)
    }

    /**
     * Calcula el total de la cuenta sin incluir la propina.
     *
     * @return Suma de todos los subtotales de los ítems
     */
    fun calcularTotalSinPropina(): Int {
        return _items.sumOf { it.calcularSubtotal() }
    }

    /**
     * Calcula el monto de la propina (10% del subtotal).
     *
     * @return Monto de la propina, o 0 si no se acepta propina
     */
    fun calcularPropina(): Int {
        return if (aceptaPropina) {
            (calcularTotalSinPropina() * 0.10).toInt()
        } else {
            0
        }
    }

    /**
     * Calcula el total de la cuenta incluyendo la propina.
     *
     * @return Subtotal + monto de la propina
     * @author Diego Herrera - diego.herrera.g@alumno.iplacex.cl
     * @since 1.0
     */
    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }

    /**
     * Obtiene una copia de la lista de ítems de la cuenta.
     *
     * @return Lista de ítems de mesa
     * @author Diego Herrera - diego.herrera.g@alumno.iplacex.cl
     * @since 1.0
     */
    fun obtenerItems(): List<ItemMesa> {
        return _items.toList()
    }
}