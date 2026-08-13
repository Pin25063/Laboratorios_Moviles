import org.junit.Test
import kotlin.test.*

class LabTest {

    @Test  // Esta anotación le dice a Kotlin que este método es una prueba
    fun `Given a list, when it has multiple elements, then result list should be correct`() {

        // Creamos los datos de entrada para nuestra prueba
        val inputList = listOf(10, "Enero", null, true)

        // Ejecutamos la función que queremos probar
        val result = processList(inputList)


        // Verificamos que el resultado sea el esperado

        // 1. Primero verificamos que el resultado NO sea null
        // assertNotNull() es una función que falla la prueba si el valor es null
        assertNotNull(result)

        // 2. Verificamos que la lista tenga el tamaño correcto (3 elementos, porque null se ignora)
        // assertTrue() verifica que la condición sea verdadera
        assertTrue(result.size == 3)

        // 3. Verificamos el PRIMER elemento (índice 0)
        // Comprobamos todas las propiedades del objeto ItemData
        assertTrue(
            result.get(0).originalPos == 0          // Posición original en la lista
                    && result.get(0).originalValue == 10    // Valor original
                    && result.get(0).type == ElementType.ENTERO  // Tipo correcto
                    && result.get(0).info.lowercase() == "m10"   // Info generada correctamente
        )

        // 4. Verificamos el SEGUNDO elemento (índice 1)
        // Nota: "Enero" tiene 5 letras, por eso info es "l5"
        assertTrue(
            result.get(1).originalPos == 1
                    && result.get(1).originalValue == "Enero"
                    && result.get(1).type == ElementType.CADENA
                    && result.get(1).info.lowercase() == "l5"
        )

        // 5. Verificamos el TERCER elemento (índice 2 en resultado, pero era índice 3 en original)
        // Nota: el null se saltó, por eso originalPos es 3 pero está en índice 2 del resultado
        assertTrue(
            result.get(2).originalPos == 3
                    && result.get(2).originalValue == true
                    && result.get(2).type == ElementType.BOOLEANO
                    && result.get(2).info.lowercase() == "verdadero"
        )
    }

    @Test
    fun `Given multiple ItemData objects, then the toString method returns the expected string`() {

        // Creamos una lista con más elementos para probar diferentes tipos
        val inputList = listOf(10, "Enero", null, true, 5.5)

        val result = processList(inputList)

        // Verificamos que el resultado no sea null
        assertNotNull(result)

        // Verificamos que el método toString() del primer elemento genere el string correcto
        // El formato esperado es: "el elemento 'valor' estaba en la posición X, es de tipo Y e info es Z"
        assertTrue(
            result.get(0).toString().lowercase() ==
                    "'10' estaba en la posición 0, es de tipo entero e info es m10"
        )

        // TIP: También podrías verificar otros elementos así:
        // assertTrue(
        //     result.get(1).toString().lowercase() ==
        //     "'enero' estaba en la posición 1, es de tipo cadena e info es l5"
        // )
    }

}

// Declarar propiedades necesarias
data class ItemData(
    val originalPos : Int,
    val originalValue : Any,
    val type : ElementType,
    val info : String
){
    override fun toString(): String {

        val tipoEnMinusculas = type.name.lowercase()
        val infoEnMinusculas = info.lowercase()

        return "'$originalValue' estaba en la posición $originalPos, es de tipo $tipoEnMinusculas e info es $infoEnMinusculas"
    }
}

enum class ElementType {
    CADENA,
    ENTERO,
    BOOLEANO,
    DESCONOCIDO
}

fun processList(inputList: List<Any?>?): MutableList<ItemData>? {
    val outputList = mutableListOf<ItemData>()
    if (inputList != null){
        for ((index, element) in inputList.withIndex()) {
            if (element != null){
                var info : String = ""
                var tipo : ElementType
                if (element is String){
                    tipo = ElementType.CADENA
                    info = "L${element.length}"
                } else if (element is Int){
                    tipo = ElementType.ENTERO
                    info = if (element % 10 == 0) "M10" else if (element %5 == 0) "M5" else if (element%2 == 0) "M2" else "-"
                } else if (element is Boolean){
                    tipo = ElementType.BOOLEANO
                    info = if (element == true) "Verdadero" else "Falso"
                } else {
                    tipo = ElementType.DESCONOCIDO
                    info = "Desconocido"
                }

                val item = ItemData(index, element, tipo, info)
                outputList.add(item)
            }
        }
    } else {
        return null
    }
    return outputList
}