public class Torre extends Pieza{

    public Torre(int color,int columna,int fila){
        super(color,columna,fila);

        tipoPieza = Tipo.TORRE;

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/torreBlanca");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/torreNegra");
        }
    }
    public boolean puedeMoverse(int columnaObjetivo, int filaObjetivo) {
        if(dentroTablero(columnaObjetivo, filaObjetivo) && !esElMismoCuadrado(columnaObjetivo, filaObjetivo)) {
            //Las torres pueden moverse siempre que su columna o fila sean las mismas.
            if(columnaObjetivo == columnaPrevia || filaObjetivo == filaPrevia) {
                return cuadradoValido(columnaObjetivo, filaObjetivo) && !piezaEstaEnRecta(columnaObjetivo, filaObjetivo);
            }
        }
        return false;
    }
}
