package com.restaurante

/**
 * Activity principal de la aplicación de toma de pedidos.
 *
 * Gestiona la interfaz de usuario para realizar pedidos
 * en el restaurante, incluyendo selección de platillos,
 * cantidades y cálculo de totales con opción de propina.
 *
 * @author Diego Herrera - diego.herrera.g@alumno.iplacex.cl
 * @since 1.0
 */
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.restaurante.databinding.ActivityMainBinding
import com.restaurante.model.CuentaMesa
import com.restaurante.model.ItemMenu
import com.restaurante.model.ItemMesa
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cuentaMesa: CuentaMesa
    private lateinit var pastelDeChoclo: ItemMenu
    private lateinit var cazuela: ItemMenu
    private lateinit var itemMesaPastel: ItemMesa
    private lateinit var itemMesaCazuela: ItemMesa
    
    /**
     * Formateador de moneda para pesos chilenos (CLP).
     * Formato: $12.000 (puntos como separadores de miles)
     */
    private val formatoCLP: NumberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CL"))

    /**
     * Método llamado al crear la Activity.
     * Inicializa los componentes del menú, la cuenta de la mesa,
     * configura los eventos y actualiza los montos iniciales.
     *
     * @param savedInstanceState Estado anterior de la Activity (si existe)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Paso 1: Inicializar los ítems del menú con nombre y precio
        inicializarMenu()
        
        // Paso 2: Crear la cuenta de la mesa y agregar los ítems
        inicializarCuenta()
        
        // Paso 3: Configurar listeners para detectar cambios del usuario
        configurarEventos()
        
        // Paso 4: Actualizar la interfaz con los valores iniciales
        actualizarMontos()
    }

    /**
     * Inicializa los platillos disponibles en el menú.
     *
     * Crea los objetos ItemMenu para cada platillo del restaurante:
     * - Pastel de Choclo: $12.000
     * - Cazuela: $10.000
     *
     * También crea los objetos ItemMesa asociados con cantidad inicial 0.
     */
    private fun inicializarMenu() {
        // Crear ítems del menú con nombre y precio
        pastelDeChoclo = ItemMenu("Pastel de Choclo", "$12.000")
        cazuela = ItemMenu("Cazuela", "$10.000")
        
        // Crear ítems de mesa asociados al menú con cantidad inicial 0
        itemMesaPastel = ItemMesa(pastelDeChoclo, 0)
        itemMesaCazuela = ItemMesa(cazuela, 0)
    }

    /**
     * Inicializa la cuenta de la mesa y agrega los ítems.
     *
     * Crea el objeto CuentaMesa para la mesa 1 y agrega
     * los ítems de mesa previamente inicializados.
     */
    private fun inicializarCuenta() {
        // Crear cuenta para mesa 1
        cuentaMesa = CuentaMesa(1)
        
        // Agregar los ítems de pedido a la cuenta
        cuentaMesa.agregarItem(itemMesaPastel)
        cuentaMesa.agregarItem(itemMesaCazuela)
    }

    /**
     * Configura los eventos de la interfaz de usuario.
     *
     * Establece listeners para:
     * - TextWatcher: Detecta cambios en las cantidades de cada platillo
     * - CheckedChangeListener: Detecta cambios en el switch de propina
     *
     * Cada evento actualiza automáticamente los montos en pantalla.
     */
    private fun configurarEventos() {
        // Configurar listener para cantidad de Pastel de Choclo
        binding.editTextCantidadPastel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                // Actualizar cantidad del ítem
                itemMesaPastel.cantidad = s.toString().toIntOrNull() ?: 0
                // Recalcular y mostrar los nuevos montos
                actualizarMontos()
            }
        })

        // Configurar listener para cantidad de Cazuela
        binding.editTextCantidadCazuela.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                // Actualizar cantidad del ítem
                itemMesaCazuela.cantidad = s.toString().toIntOrNull() ?: 0
                // Recalcular y mostrar los nuevos montos
                actualizarMontos()
            }
        })

        // Configurar listener para el switch de propina
        binding.switchPropina.setOnCheckedChangeListener { _, isChecked ->
            // Actualizar estado de aceptación de propina
            cuentaMesa.aceptaPropina = isChecked
            // Recalcular totales con o sin propina
            actualizarMontos()
        }
    }

    /**
     * Actualiza todos los montos mostrados en pantalla.
     *
     * Calcula y muestra:
     * - Subtotal de cada platillo
     * - Total sin propina
     * - Monto de la propina (10%)
     * - Total final con propina incluida (si aplica)
     *
     * Los valores se formatean como pesos chilenos usando NumberFormat.
     */
    private fun actualizarMontos() {
        // Mostrar subtotales de cada platillo
        binding.textViewSubtotalPastel.text = formatoCLP.format(itemMesaPastel.calcularSubtotal())
        binding.textViewSubtotalCazuela.text = formatoCLP.format(itemMesaCazuela.calcularSubtotal())
        
        // Mostrar totales de la cuenta
        binding.textViewTotalSinPropina.text = formatoCLP.format(cuentaMesa.calcularTotalSinPropina())
        binding.textViewMontoPropina.text = formatoCLP.format(cuentaMesa.calcularPropina())
        binding.textViewTotalFinal.text = formatoCLP.format(cuentaMesa.calcularTotalConPropina())
    }
}