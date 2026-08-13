// Declarar propiedades necesarias
fun main() {

}

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
                } else if (element is Boolean){c
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