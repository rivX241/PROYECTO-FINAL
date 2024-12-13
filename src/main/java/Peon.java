public class Peon extends Pieza{

    public Peon(int color, int columna, int fila){
        super(color, columna, fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/peonBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/peonNegro");
        }
    }
    public boolean puedeMoverse(int columnaObjetivo, int filaObjetivo) {
        if(dentroTablero(columnaObjetivo, filaObjetivo) && !esElMismoCuadrado(columnaObjetivo, filaObjetivo)) {
            //Definir el valor del movimiento en función de su color
            int movimientoValor;
            if(color == GUI.BLANCO){
                movimientoValor = -1;
            } else {
                movimientoValor = 1;
            }
            
            //Verificar el choque de piezas
            chocaPieza = getChocaPieza(columnaObjetivo, filaObjetivo);

            //1 cuadrado de movimiento
            if(columnaObjetivo == columnaPrevia && filaObjetivo == filaPrevia + movimientoValor && chocaPieza == null) {
                return true;
            }

            //2 cuadrados de movimiento
            if(columnaObjetivo == columnaPrevia && filaObjetivo == filaPrevia + movimientoValor * 2 && chocaPieza == null &&
                    !mover && !piezaEstaEnRecta(columnaObjetivo, filaObjetivo)) {
                    return true;
                }
            //Movimiento diagonal y matar (si una pieza esta en un cuadrado diagonal al peon)
            return Math.abs(columnaObjetivo - columnaPrevia) == 1 && filaObjetivo == filaPrevia + movimientoValor && chocaPieza != null &&
                    chocaPieza.color != color;
        }
        return false;
    }
}
