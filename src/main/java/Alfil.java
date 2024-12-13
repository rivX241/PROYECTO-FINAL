//Clase hija Afil que hereda métodos o atributos de la superclase Pieza
public class Alfil extends Pieza{

    public Alfil(int color,int columna,int fila){
        super(color,columna,fila);

        tipoPieza = Tipo.ALFIL;

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/alfilBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/alfilNegro");
        }
    }
    public boolean puedeMoverse(int columnaObjetivo, int filaObjetivo) {
        if(dentroTablero(columnaObjetivo, filaObjetivo) && esElMismoCuadrado(columnaObjetivo, filaObjetivo) == false) {
		// Verifica si el movimiento es diagonal (la diferencia entre columna y fila debe ser igual)
            if(Math.abs(columnaObjetivo - columnaPrevia) == Math.abs(filaObjetivo - filaPrevia)) {
		 //Verifica que el cuadrado esté vacío y que no haya piezas bloqueando la diagonal
                if(cuadradoValido(columnaObjetivo, filaObjetivo) && piezaEstaEnDiagonal(columnaObjetivo, filaObjetivo) == false) {
                    return true;
                }
            }
        }
	//Si no cumple la condición, invalida movimientos
        return false;
    }
}
