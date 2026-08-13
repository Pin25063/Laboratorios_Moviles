//Jose Pinto 25063 Sección 20
fun main() {
    var salir = true

    val nombre1 = "Laptop";  var cantidad1 = 10; var disponible1 = true
    val nombre2 = "Teclado"; var cantidad2 = 10; var disponible2 = true
    val nombre3 = "Mouse";   var cantidad3 = 10; var disponible3 = true
    val nombre4 = "Monitor"; var cantidad4 = 10; var disponible4 = true
    val nombre5 = "PC";      var cantidad5 = 10; var disponible5 = true

    do {
        menu()
        val opcion = readln().toIntOrNull()

        if (opcion != null) {
            when (opcion) {
                1 -> mostrarInventario(
                    nombre1, cantidad1, disponible1,
                    nombre2, cantidad2, disponible2,
                    nombre3, cantidad3, disponible3,
                    nombre4, cantidad4, disponible4,
                    nombre5, cantidad5, disponible5
                )

                2 -> buscarProducto(
                    nombre1, cantidad1, disponible1,
                    nombre2, cantidad2, disponible2,
                    nombre3, cantidad3, disponible3,
                    nombre4, cantidad4, disponible4,
                    nombre5, cantidad5, disponible5
                )

                3 -> {
                    println("\n--- ACTUALIZAR CANTIDAD ---")
                    println("1. $nombre1 (Actual: $cantidad1)")
                    println("2. $nombre2 (Actual: $cantidad2)")
                    println("3. $nombre3 (Actual: $cantidad3)")
                    println("4. $nombre4 (Actual: $cantidad4)")
                    println("5. $nombre5 (Actual: $cantidad5)")
                    print("Seleccione el número del producto a actualizar (1-5): ")

                    val numProducto = readln().toIntOrNull()

                    when (numProducto) {
                        1 -> cantidad1 = solicitarNuevaCantidad(nombre1)
                        2 -> cantidad2 = solicitarNuevaCantidad(nombre2)
                        3 -> cantidad3 = solicitarNuevaCantidad(nombre3)
                        4 -> cantidad4 = solicitarNuevaCantidad(nombre4)
                        5 -> cantidad5 = solicitarNuevaCantidad(nombre5)
                        else -> println("Número de producto inválido.")
                    }

                    disponible1 = cantidad1 > 0
                    disponible2 = cantidad2 > 0
                    disponible3 = cantidad3 > 0
                    disponible4 = cantidad4 > 0
                    disponible5 = cantidad5 > 0
                }

                4 -> mostrarEstadisticas(
                    cantidad1, disponible1,
                    cantidad2, disponible2,
                    cantidad3, disponible3,
                    cantidad4, disponible4,
                    cantidad5, disponible5
                )

                5 -> salir = false
                else -> println("Opción no válida. Por favor elija un número de 1 a 5.")
            }
        } else {
            println("Entrada inválida. Ingrese un número.")
        }
    } while (salir)

    println("\nCerrando programa...")
}

fun menu() {
    println("""
        
        -----------------------------------
        MENU DE INVENTARIO
        1. Mostrar inventario completo
        2. Buscar un producto
        3. Actualizar cantidad de producto
        4. Mostrar estadísticas
        5. Salir
        Ingrese su opción: 
    """.trimIndent())
}

fun mostrarInventario(
    n1: String, c1: Int, d1: Boolean,
    n2: String, c2: Int, d2: Boolean,
    n3: String, c3: Int, d3: Boolean,
    n4: String, c4: Int, d4: Boolean,
    n5: String, c5: Int, d5: Boolean
) {
    println("\n-----------------------------------")
    println("INVENTARIO COMPLETO:")
    println("1. $n1 | Cantidad: $c1 | Disponible: $d1")
    println("2. $n2 | Cantidad: $c2 | Disponible: $d2")
    println("3. $n3 | Cantidad: $c3 | Disponible: $d3")
    println("4. $n4 | Cantidad: $c4 | Disponible: $d4")
    println("5. $n5 | Cantidad: $c5 | Disponible: $d5")
}

fun buscarProducto(
    n1: String, c1: Int, d1: Boolean,
    n2: String, c2: Int, d2: Boolean,
    n3: String, c3: Int, d3: Boolean,
    n4: String, c4: Int, d4: Boolean,
    n5: String, c5: Int, d5: Boolean
) {
    println("\n-----------------------------------")
    print("Ingrese el nombre del producto a buscar: ")
    val busqueda = readln().trim().lowercase()

    when (busqueda) {
        n1.lowercase() -> println("Encontrado: $n1 | Cantidad: $c1 | Disponible: $d1")
        n2.lowercase() -> println("Encontrado: $n2 | Cantidad: $c2 | Disponible: $d2")
        n3.lowercase() -> println("Encontrado: $n3 | Cantidad: $c3 | Disponible: $d3")
        n4.lowercase() -> println("Encontrado: $n4 | Cantidad: $c4 | Disponible: $d4")
        n5.lowercase() -> println("Encontrado: $n5 | Cantidad: $c5 | Disponible: $d5")
        else -> println("No se encontró ningún producto con ese nombre.")
    }
}

fun solicitarNuevaCantidad(nombreProducto: String): Int {
    print("Ingrese la nueva cantidad para $nombreProducto (0-100): ")
    val entrada = readln().toIntOrNull()

    return if (entrada != null && entrada in 0..100) {
        println("¡Cantidad actualizada exitosamente!")
        entrada
    } else {
        println("Cantidad no válida. Se requiere un número entero entre 0 y 100.")
        0
    }
}

fun mostrarEstadisticas(
    c1: Int, d1: Boolean,
    c2: Int, d2: Boolean,
    c3: Int, d3: Boolean,
    c4: Int, d4: Boolean,
    c5: Int, d5: Boolean
) {
    println("\n-----------------------------------")
    println("ESTADÍSTICAS BÁSICAS DEL INVENTARIO:")

    var productosDisponibles = 0
    if (d1 && c1 > 0) productosDisponibles++
    if (d2 && c2 > 0) productosDisponibles++
    if (d3 && c3 > 0) productosDisponibles++
    if (d4 && c4 > 0) productosDisponibles++
    if (d5 && c5 > 0) productosDisponibles++

    // Sumamos todas las unidades físicas
    val totalUnidades = c1 + c2 + c3 + c4 + c5

    println("Tipos de productos disponibles: $productosDisponibles de 5")
    println("Total de unidades físicas en stock: $totalUnidades")
}